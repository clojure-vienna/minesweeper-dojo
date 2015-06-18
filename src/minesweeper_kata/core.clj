(ns minesweeper-kata.core)

(use 'clojure.java.io)
(require '[clojure.string :as str])

(defn convert-field
  "return 1 for x else 0"
  [field]
  (if (= "x" field) 1 0))

(defn handle-line [line] (map convert-field (rest (str/split line #""))))

(defn read-file
  "docstring"
  [file]
  (with-open [rdr (reader file)]
    (let [lines (rest (line-seq rdr))]
      (reduce
        (fn [coll x] (conj coll (vec (handle-line x))))
        []
        lines))))

(defn cart [colls]
  (if (empty? colls)
    '(())
    (for [x (first colls)
          more (cart (rest colls))]
      (cons x more))))

(defn read-neighbors
  ""
  [matrix x y]
  (map
    (fn [coords] (get-in matrix coords))
    (cart [(range (- x 1) (+ x 2)) (range (- y 1) (+ y 2))])))

(defn count-neighbors
  "get value for coords"
  [matrix x y]
  (if (= 1 (get-in matrix [x y]))
    "b"
    (reduce (fn [x next] (if next (+ x next) x)) 0
            (read-neighbors matrix x y)))
  )

(defn magic
  "docstring"
  [file]
  (let [matrix (read-file file)]
    (map
      (fn [x]
        (map
          (fn [y]
            (count-neighbors matrix x y))
          (range 0 3)))
      (range 0 3))))


(println (magic "field-in"))
