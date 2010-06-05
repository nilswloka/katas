(ns gameoflife.core)

(defn- cell-at [{:keys [width cells]} x y]
  (cells (+ (* width y) x)))

(defn- all-cell-coordinates [{:keys [width height]}]
  (for [y (range height) x (range width)] [x y]))

(defn- neighbour-inside-grid? [{:keys [width height]} current-coords]
  (fn [coords] (and (not-any? neg? coords)
		    (< (first coords) width)
		    (< (second coords) height)
		    (not (= coords current-coords)))))

(defn- neighbours-coordinates [grid [x y :as current-coordinates]]
  (let [deltas (for [dx (range -1 2) dy (range -1 2)] [dx dy])]
    (filter (neighbour-inside-grid? grid current-coordinates) 
	    (map #(vector (+ x (first %)) (+ y (second %))) deltas))))

(defn- neighbour-cell-counts [grid]
  (for [cell-coordinate (all-cell-coordinates grid)]
    (reduce + (map #(cell-at grid (first %) (second %))
		   (neighbours-coordinates grid cell-coordinate)))))

(defn- live-or-die [[cell neighbour-count]]
		   (let [is-alive (not (zero? cell))
			survives (and (> neighbour-count 1) (< neighbour-count 4) is-alive)
			grows (and (not is-alive) (= neighbour-count 3))]
			(if (or grows survives) 1 0)))

(defn evolve [{height :height width :width cells :cells :as grid}]
  (let [cells-with-neighbour-counts (map vector cells (neighbour-cell-counts grid))]
    (assoc grid :cells (vec (map live-or-die cells-with-neighbour-counts)))))

