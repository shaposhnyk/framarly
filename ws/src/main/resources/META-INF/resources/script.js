const app = Vue.createApp({
  data() {
    return {
      source: [
        { text: "This", t:'e', whitespace: " " },
        { text: "is", whitespace: " " },
        { text: "a", whitespace: " " },
        { text: "text", t:'w', whitespace: "" },
        { text: ".", whitespace: "" }
      ],
      target: "<b>hello</b> text t",
      todos: [
        {
          id: 1,
          s: 0,
          l: 1,
          t: 'e',
          title: "replace this by that",
          w: [{ text: "That", whitespace: " " }]
        },
        {
          id: 2,
          s: 3,
          l: 1,
          t: 'w',
          title: "remove text",
          w: []
        },
        {
          id: 3,
          s: 1,
          l: 0,
          t: 'w',
          title: "add some",
          w: [
            { text: "Some", whitespace: " " },
            { text: "more", whitespace: " " }
          ]
        }
      ]
    };
  },
  computed: {
    sourceHtml() {
      return this.source
        .map((x) => (x.t !== undefined)? ("<span class='"+(x.t == "e"? 'redUnderline' : 'yellowUnderline')+"'>"+x.text + x.whitespace+"</span>") : (x.text + x.whitespace))
        .join("");
    },
    filteredTodos() {
      return this.todos;
    }
  },
  methods: {
    update: _.debounce(function (e) {
      var src = e.target.innerText;
      this.target = src;
      this.source = src + "<b id='code'>some22</b>";
    }, 300),
    applyTodo(todo) {
      if (!todo.disabled) {
        todo.disabled = true;
        this.source.splice(todo.s, todo.l, ...todo.w);
        this.adjustTodos(todo);
        this.removeTodoLater(todo);
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
    removeTodoLater: _.debounce(function (todo) {
      this.removeTodo(todo);
    }, 5000),
    removeTodo(todo) {
      this.todos.splice(this.todos.indexOf(todo), 1);
    }
  }
});

app.mount("#app");