# Conway's Game of Life

## Getting Started

#### 1. Install Leiningen

Leningen is a dependency and build tool for Clojure and ClojureScript.

Follow the installation instructions at http://github.com/technomancy/leiningen

#### 2. Compile the ClojureScript

Run `lein cljsbuild once`

#### 3. Start the HTML server

Run `lein ring server`

#### 4. Test that it works

Visit `localhost:3000/auto.html`. You should see the Game of Life running in the browser.

At this point, you can modify the program. Just recompile using `lein cljsbuild auto` and refresh the browser to see your changes.

If you like, you can use `lein cljsbuild auto` to automatically recompile whenever the source file changes.

#### 5. Hook up a REPL and start hacking.

First, run `lein trampoline cljsbuild repl-listen` at the command prompt (or as an inferior lisp in Emacs).

Then, load `localhost:3000/repl.html` in your browser. This will connect to the REPL you just started. If you loaded repl.html before you started the repl server, you'll need to refresh the page to ensure that the Javascript code successfully connects to the REPL server.

Try typing some forms at the REPL! Start with simple forms, like `(+ 1 1)` and move up from there.

To start the game of life execution, invoke the `start` function in the `game-of-life` namespace. For example:

```clojure
    (game-of-life/start 50)
```
