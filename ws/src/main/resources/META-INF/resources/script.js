const apiURL = "/todos/?text=";
const app = Vue.createApp({
  data() {
    return {
      source: [
        { text: "Le fautes d'orthographe et les erreurs grammaticales peuvent affecter votre crédibilité.", whitespace: " " },
        { text: "Il en va de même pour les virgules mal utilisées et les autres types de ponctuation.", whitespace: "" },
      ],
      todos: []
    };
  },
  created: function() {
    this.fetchSuggestions(this.sourceText);
  },
  computed: {
    sourceHtml() {
      return this.source
        .map((x) => ("ERR" == x.type)? ("<span class='redUnderline'>"+x.text + x.whitespace+"</span>") : (x.text + x.whitespace))
        .join("");
    },
    sourceText() {
      return this.source.map((x) => x.text + x.whitespace).join("");
    },
    suggestions() {
      return this.todos;
    }
  },
  methods: {
    onEdit: _.debounce(function (e) {
      this.fetchSuggestions(e.target.innerText);
    }, 1000),
    applyTodo(todo) {
      if (!todo.disabled) {
        todo.disabled = true;
        for (var i = 0; i < todo.replacements.length; i++) {
          var r = todo.replacements[i];
          this.source.splice(r.startIdx, r.len, ...r.replacement);
        }
        // do I need to adapt existing todos?
        this.finishUpdatesLater(todo);
      }
    },
    finishUpdatesLater: _.debounce(function (todo) {
      this.todos.splice(this.todos.indexOf(todo), 1);
      this.fetchSuggestions(this.sourceText);
    }, 500),
    fetchSuggestions(text) {
      var xhr = new XMLHttpRequest();
      var self = this;
      xhr.open("GET", apiURL + text);
      xhr.onload = function() {
        var res = JSON.parse(xhr.responseText);
        self.source = res.sentence;
        self.todos = res.suggestions.map((x, idx) => ({
            id: idx,
            title: x.title,
            replacements: x.replacements,
        }));
      };
      xhr.send();
    }
  }
});

app.mount("#app");