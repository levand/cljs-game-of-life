(defproject game-of-life "0.1.0-SNAPSHOT"
  :description "Game of Life demo in ClojureScript"
  :plugins [[lein-cljsbuild "0.2.1"]
            [lein-ring "0.7.1"]]
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.0"]]
  :ring {:handler server/handler}
  :cljsbuild {:builds
              [{:source-path "src/cljs"
                :compiler {:output-to "resources/public/life.js"
                           :optimizations :whitespace
                           :pretty-print true}}]})