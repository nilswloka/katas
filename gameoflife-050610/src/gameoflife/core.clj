(ns gameoflife.core
  (:use (clojure.contrib seq-utils)))

(defn evolve-cell [current-state number-of-alive-neighbours]
  (if (or (and (= 0 current-state) (= 3 number-of-alive-neighbours))
          (and (= 1 current-state) (> number-of-alive-neighbours 1) (< number-of-alive-neighbours 4)))
    1 0))

(defn neighbour-coordinates-for-in [[x y] width height]
  (let [offsets (for [dx (range -1 2) dy (range -1 2) :when (not (and (= 0 dx) (= 0 dy)))] [dx dy])
        offset-coordinates (map #(vector (+ x (first %)) (+ y (second %))) offsets)]
    (filter #(and (not-any? neg? %) (< (first %) width) (< (second %) height)) offset-coordinates)))

(defn value-at [{:keys [width height cells]}]
  (fn [[x y]]
    (get cells (+ (* y width) x))))

(defn count-alive-neighbours [{:keys [width height cells] :as grid} coordinates]
  (let [neighbour-coordinates (neighbour-coordinates-for-in coordinates width height)
        values (map (value-at grid) neighbour-coordinates)]
    (reduce + values)))

(defn walk-grid [width height]
  (for [y (range height) x (range width)] [x y]))

(defn evolve [{:keys [width height cells] :as grid}]
  (let [alive-neighbours (map #(count-alive-neighbours grid %) (walk-grid width height))
        next-cells (vec (map evolve-cell cells alive-neighbours))]
    (assoc grid :cells next-cells)))
