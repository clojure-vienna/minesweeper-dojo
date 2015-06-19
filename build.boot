(set-env!
 :source-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.7.0-RC1"]])

(swap! boot.repl/*default-dependencies*
       concat '[[lein-light-nrepl "0.0.13"]
                [org.clojure/clojurescript "0.0-3191"]])
(swap! boot.repl/*default-middleware*
       conj 'lighttable.nrepl.handler/lighttable-ops)

(task-options!
 pom {:project 'kata-minesweeper
      :version "0.1.0"}
 aot {:namespace '#{kata.minesweeper}}
 jar {:main 'kata.minesweeper})

(deftask build
  []
  (comp (aot) (pom) (uber) (jar)))


(deftask dev
  []
  (repl :init-ns 'kata.minesweeper))
