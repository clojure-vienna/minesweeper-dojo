(ns kata.minesweeper
  (:require [clojure.string :as str])
  (:gen-class))

(def neighbour-coords [[-1 -1]
                      [-1 0]
                      [-1 1]
                      [0 -1]
                      [0 1]
                      [1 -1]
                      [1 0]
                      [1 1]])

(defn read-input [src]
  (slurp src))

(defn split-sections [input]
  (str/split input #"\n" 2))

(defn parse-body [body]
  (let [rows (str/split body #"\n")]
    (into [] (map (fn [row]
                    (into [] (map #(if (= % \x) 1 0) row)))
                  rows))))

(defn neighbourbombs [matrix y x]
  (reduce (fn [result [offset-y offset-x]]
            (+ result (get-in matrix [(+ y offset-y) (+ x offset-x)] 0)))
          0
          neighbour-coords))

(defn solve [input]
  (map-indexed (fn [y row]
                 (map-indexed (fn [x val]
                                (if (= val 0) (neighbourbombs input y x) \B))
                              row))
               input))

(defn serialize [input]
  (str/join \newline
            (map (fn [val] (str/join \space
                                     (map #(if (= % 0) \space %) val)))
                 input)))

(defn -main [src-path dest-path]
  ((comp #(spit dest-path %)
         serialize
         solve
         parse-body
         second
         split-sections
         read-input)
   src-path))
