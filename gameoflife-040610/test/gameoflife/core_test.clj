(ns gameoflife.core-test
  (:use [gameoflife.core] :reload-all)
  (:use [clojure.test]))

(deftest rules-for-cells
  (are [alive the-number-of-neighbours expected] (= expected (lives? alive the-number-of-neighbours))
       true 0 false
       true 1 false
       true 2 true
       true 3 true
       true 4 false
       false 2 false
       false 3 true
       false 4 false))

(deftest right-number-of-neighbours
  (are [grid coordinates expected] (= expected (number-of-neighbours grid coordinates))
       [[0 0 0]
	[0 0 0]
	[0 0 0]] [0 0] 0
       [[0 1 0]
	[1 1 0]
        [0 0 0]] [0 0] 3
       [[0 0 0]
	[0 1 1]
	[0 1 1]] [2 2] 3
       [[0 1 0]
	[1 0 1]
	[0 1 0]] [1 1] 4))

(deftest dont-step-outside-the-grid
  (are [coordinates expected] (= expected ((inside-grid? [[0 0]
							  [0 0]]) coordinates))
       [0 0] true
       [-1 0] false
       [0 -1] false
       [2 1] false
       [1 2] false
       [1 1] true))

(deftest find-out-status-of-cell
  (are [coordinates expected] (= expected ((cell-at [[0 1]
						     [2 3]]) coordinates))
       [0 0] 0
       [0 1] 2
       [1 0] 1
       [1 1] 3))

(deftest evolving-the-grid
  (are [before after] (= after (evolve before))
       [[0 1 0]
	[1 0 1]
	[0 1 0]]
       [[0 1 0]
	[1 0 1]
	[0 1 0]]
       [[1 1 1]
	[1 1 1]
	[1 1 1]]
       [[1 0 1]
	[0 0 0]
	[1 0 1]]
       [[0 0 0]
	[1 0 1]
	[0 1 0]]
       [[0 0 0]
	[0 1 0]
	[0 1 0]]))
