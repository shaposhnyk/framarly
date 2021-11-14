const apiURL = "/todos/?text=";
const app = Vue.createApp({
  data() {
    return {
      ver: 0,
      pos: 0,
      sources: [
        { text: "Le fautes d'orthographe et le erreurs grammaticales peuvent affecter votre crédibilité.", whitespace: " " },
        { text: "Il en va de même pour les virgules mal utilisées et les autres types de ponctuation.", whitespace: "" },
      ],
      todos: []
    };
  },
  created: function() {
    this.fetchSuggestions(0, this.sourceText);
  },
  computed: {
    sourceHtml() {
      return this.sources
        .map((x) => ("ERR" == x.type)? ("<span class='redUnderline'>"+x.text+x.whitespace+"</span>") : (x.text + x.whitespace))
        .join("");
    },
    sourceText() {
      return this.sources.map((x) => x.text + x.whitespace).join("");
    },
    suggestions() {
      return this.todos;
    }
  },
  watch: {
    sourceHtml() {
      var self = this;
      this.$nextTick(() => {
        self.restorePos();
      });
    }
  },
  methods: {
    onType: _.debounce(function (e) {
      const text = this.$refs.editor.innerText;
      this.fetchSuggestions(++this.ver, text);
    }, 900),
    onTypeI(e) {
      console.log("here");
    },
    applyTodo(todo) {
      if (!todo.disabled) {
        todo.disabled = true;
        for (var i = 0; i < todo.replacements.length; i++) {
          var r = todo.replacements[i];
          this.sources.splice(r.startIdx, r.len, ...r.replacement);
        }
        // do I need to adapt existing todos?
        this.finishUpdatesLater(todo);
      }
    },
    finishUpdatesLater: _.debounce(function (todo) {
      this.todos.splice(this.todos.indexOf(todo), 1);
      this.fetchSuggestions(++this.ver, this.$refs.editor.innerText);
    }, 900),
    savePos() {
        var sel = document.getSelection();
        return [ sel.anchorNode, sel.focusOffset ];
    },
    restorePos() {
      console.log("restoring pos to: " + this.pos);
      //this.$refs.editor.focus();

       var range = document.createRange();
       range.selectNodeContents(this.$refs.editor);
       range.setStart(this.pos[0], this.pos[1]);
       range.setEnd(this.pos[0], this.pos[1]+1);

       var sel = window.getSelection();
       sel.removeAllRanges();
       sel.addRange(range);
       //sel.collapse(this.pos[0], this.pos[1]);
      //this.$refs.editor.selectionStart = this.pos[1];
    },
    updateSentence(ver, newSentence, newTodos) {
        if (ver == this.ver) {
            console.log("updating to: " + this.ver);
            this.pos = this.savePos();
            this.todos = newTodos;
            this.sources = newSentence;
            console.log("updated to: " + this.sourceText);
        } else {
            console.log("skipping update to " + ver + " because current is: " + this.ver);
        }
    },
    fetchSuggestions(ver, text) {
      console.log("suggestions for: " + text);
      var xhr = new XMLHttpRequest();
      var self = this;
      xhr.open("GET", apiURL + text);
      xhr.onload = function() {
        var res = JSON.parse(xhr.responseText);
        var newTodos = res.suggestions.map((x, idx) => ({
            id: idx,
            title: x.title,
            replacements: x.replacements,
        }));
        self.updateSentence(ver, res.sentence, newTodos);
      };
      xhr.send();
    },
    isDifferent(newTodos) {
      return !(this.todos.length == 0 && newTodos.length == 0);
    }
  }
});

app.mount("#app");