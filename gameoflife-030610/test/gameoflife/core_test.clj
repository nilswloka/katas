(ns gameoflife.core-test
  (:use [gameoflife.core] :reload-all)
  (:use [clojure.test]))

(deftest alive-cells-with-no-neighbours-die
  (are [x y] (= x y)
       {:width 3
	:height 3
	:cells [0 0 0
		0 0 0
		0 0 0]}
       (evolve {:width 3
		:height 3
		:cells [0 0 0 
			0 1 0 
			0 0 0]})
       {:width 4
	:height 4
	:cells [0 0 0 0
		0 0 0 0
		0 0 0 0
		0 0 0 0]}
       (evolve {:width 4
		:height 4
		:cells [0 0 0 1
			0 0 0 0
			0 0 0 0
			0 0 0 0]})))

(deftest alive-cells-with-one-neighbour-die
  (are [x y] (= x y)
       {:width 3
	:height 3
	:cells [0 0 0
		0 0 0
		0 0 0]}
       (evolve {:width 3
		:height 3
		:cells [1 0 0 
			1 0 0 
			0 0 0]})
       {:width 4
	:height 4
	:cells [0 0 0 0
		0 0 0 0
		0 0 0 0
		0 0 0 0]}
       (evolve {:width 4
		:height 4
		:cells [0 0 0 1
			0 0 0 1
			1 0 0 0
			1 0 0 0]})))

(deftest live-and-die
  (are [x y] (= x y)
       {:width 3
	:height 3
	:cells [1 0 1
		0 0 0
		1 0 1]}
       (evolve {:width 3
		:height 3
		:cells [1 1 1 
			1 1 1 
			1 1 1]})
       {:width 6
	:height 6
	:cells [0 0 1 1 0 0
		0 1 1 1 1 0
		1 1 0 0 1 1
		1 1 0 0 1 1
		0 1 1 1 1 0
		0 0 1 1 0 0]}
       (evolve {:width 6
		:height 6
		:cells [0 0 0 0 0 0
			0 1 1 1 1 0
			0 1 0 0 1 0
			0 1 0 0 1 0
			0 1 1 1 1 0
			0 0 0 0 0 0]})))
