(ns gameoflife.core-test
  (:use [gameoflife.core] :reload-all)
  (:use [clojure.test]))

(deftest rules-for-cells
  (are [current-state number-of-alive-neighbours future-state]
       (= (evolve-cell current-state number-of-alive-neighbours) future-state)
       0 1 0
       0 2 0
       0 3 1
       0 4 0
       1 0 0
       1 1 0
       1 2 1
       1 3 1
       1 4 0))

(deftest calculate-neighbour-coordinates
  (are [coordinates width height expected-coordinates]
       (some expected-coordinates (neighbour-coordinates-for-in coordinates width height))
       [0 0] 2 2 #{[0 1] [1 1] [1 0]}
       [1 1] 2 2 #{[0 0] [0 1] [1 0]}
       [1 1] 3 3 #{[0 0] [0 1] [0 2]
                   [1 0] [1 2]
                   [2 0] [2 1] [2 2]}))

(deftest get-values-by-coordinates
  (let [grid {:width 2 :height 2 :cells [1 2
                                         3 4]}
        value-at (value-at grid)]
    (are [coordinates expected-value]
         (= (value-at coordinates) expected-value)
         [0 0] 1
         [1 0] 2
         [0 1] 3
         [1 1] 4)))

(deftest counting-alive-neighbours
  (let [grid {:width 3 :height 3 :cells [0 1 0
                                         1 1 0
                                         0 0 1]}]
    (are [coordinates expected-alive-neighbours]
         (= (count-alive-neighbours grid coordinates) expected-alive-neighbours)
         [0 0] 3
         [0 1] 2
         [0 2] 2
         [1 0] 2
         [1 1] 3
         [1 2] 3
         [2 0] 2
         [2 1] 3
         [2 2] 1)))

(deftest get-all-coordinates
  (are [width height expected-coordinates]
       (= (walk-grid width height) expected-coordinates)
       3 3 [[0 0] [1 0] [2 0] [0 1] [1 1] [2 1] [0 2] [1 2] [2 2]]))

(deftest evolve-whole-grid
  (are [current-grid next-grid]
       (= (evolve current-grid) next-grid)
       {:width 3 :height 3 :cells [0 1 0
                                   1 0 0
                                   0 1 0]}
       {:width 3 :height 3 :cells [0 0 0
                                   1 1 0
                                   0 0 0]}))

