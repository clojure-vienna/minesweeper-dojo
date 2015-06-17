(ns minesweeper-kata.core)

(use 'clojure.java.io)
(require '[clojure.string :as str])


(defn handle-line [line] (rest (str/split line #"")))
(defn testx []
  (with-open [rdr (reader "field-in")]
    (let [lines (rest (line-seq rdr))]
      (reduce
        (fn [coll x] (conj coll (handle-line x)))
        []
        lines))
    )
  )

(println (testx))
