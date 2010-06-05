(ns gameoflife.core)

(defn lives? [alive the-number-of-neighbours]
  (or (and alive (> the-number-of-neighbours 1) (< the-number-of-neighbours 4))
      (and (not alive) (= the-number-of-neighbours 3))))

(defn inside-grid? [grid]
  (let [outside-height (count grid)
	outside-width (count (first grid))]
    (fn [coordinates]
      (and (not-any? neg? coordinates)
	   (< (first coordinates) outside-width)
	   (< (second coordinates) outside-height)))))

(defn cell-at [grid]
  (fn [coordinates]
    (get-in grid (reverse coordinates))))

(defn number-of-neighbours [grid coordinates]
  (let [offsets (for [dx (range -1 2) dy (range -1 2)] [dx dy])
	neighbours-coordinates (filter (inside-grid? grid)
				       (for [offset offsets] [(+ (first coordinates) (first offset))
							      (+ (second coordinates) (second offset))]))]
    (- (reduce + (map (cell-at grid) neighbours-coordinates)) ((cell-at grid) coordinates))))

(defn evolve [grid]
  (let [height (count grid)
	width (count (first grid))
	all-coordinates (for [y (range height) x (range width)] [x y])
	cell-at (cell-at grid)]
    (clojure.walk/walk vec vec
		       (partition width (for [coordinate all-coordinates]
					  (if (lives? (= (cell-at coordinate) 1)
						      (number-of-neighbours grid coordinate)) 1 0))))))