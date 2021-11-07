const apiURL = "/todos/?text=";
const app = Vue.createApp({
  data() {
    return {
      source: [
        { text: "Quelque", t:'e', whitespace: " " },
        { text: "chose", whitespace: " " },
        { text: "roule", whitespace: " " },
        { text: "vite", t:'w', whitespace: "" },
        { text: ".", whitespace: "" }
      ],
      todos: []
    };
  },
  computed: {
    sourceHtml() {
      return this.source
        .map((x) => (x.t !== undefined)? ("<span class='"+(x.t == "e"? 'redUnderline' : 'yellowUnderline')+"'>"+x.text + x.whitespace+"</span>") : (x.text + x.whitespace))
        .join("");
    },
    sourceText() {
      return this.source.map((x) => x.text + x.whitespace).join("");
    },
    filteredTodos() {
      return this.todos;
    }
  },
  methods: {
    update: _.debounce(function (e) {
      this.fetchData(e.target.innerText);
    }, 300),
    applyTodo(todo) {
      if (!todo.disabled) {
        todo.disabled = true;
        this.source.splice(todo.s, todo.l, ...todo.w);
        this.adjustTodos(todo);
        this.finishUpdatesLater(todo);
      }
    },
    adjustTodos(todo) {
      var delta = todo.w.length - todo.l;
      for (var i = 0; i < this.todos.length; i++) {
        if (this.todos[i].s > todo.s) {
          this.todos[i].s += delta;
        }
      }
    },
    finishUpdatesLater: _.debounce(function (todo) {
      this.removeTodo(todo);
      this.fetchData(this.sourceText)
    }, 500),
    removeTodo(todo) {
      this.todos.splice(this.todos.indexOf(todo), 1);
    },
    fetchData(text) {
      var xhr = new XMLHttpRequest();
      var self = this;
      xhr.open("GET", apiURL + text);
      xhr.onload = function() {
        var res = JSON.parse(xhr.responseText);
        self.source = res.sentence;
        self.todos = res.suggestions.map((x, idx) => ({
            id: idx,
            title: x.title,
            s: x.replacements[0].startIdx,
            l: x.replacements[0].len,
            w: x.replacements[0].replacement,
            t: 'e'
        }));
      };
      xhr.send();
    }
  }
});

app.mount("#app");