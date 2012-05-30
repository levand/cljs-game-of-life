(ns game-of-life
  (:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(def cell-size 10)

#_(def canvas (.getElementById js/document "life-canvas"))

(defn setup-canvas!
  "Sets the canvas size and initializes the background"
  [canvas size]
  (let [px (* size cell-size)
        px-str (str px "px")
        ctx (.getContext canvas "2d")]
    (.setAttribute canvas "width" px-str)
    (.setAttribute canvas "height" px-str)
    (set! (.-fillStyle ctx) "rgb(0,0,0)")
    (.fillRect ctx 0 0 px px)))

(defn render-cell!
  "Renders a cell on a canvas"
  [canvas x y value]
  (let [ctx (.getContext canvas "2d")
        color (if value "rgb(0,200,0)" "rgb(0,0,0)")]
    (set! (.-fillStyle ctx) color)
    (.beginPath ctx)
    (.arc ctx (* x cell-size) (* y cell-size) (/ cell-size 2) 0 (* 2 Math/PI) false)
    (.closePath ctx)
    (.fill ctx)))

;; We'll represent our grid as a set: the keys are [x,y] tuples. If a
;; key is present it means that cell is alive.

(defn render-grid!
  "Given a grid, render it to the page."
  [canvas grid size]
  (let [canvas (.getElementById js/document "life-canvas")]
    (doseq [x (range size)
            y (range size)]
      (render-cell! canvas x y (grid [x y])))))

(defn build-grid
  "Given a size, a grid, and a liveness function, return a new grid."
  [size alive? prev]
  (time
   (into #{} (for [x (range size)
                   y (range size)]
               (if (alive? x y prev) [x y])))))

(defn rand-bool
  [_ _ _]
  (zero? (rand-int 2)))

(defn neighbors
  "Returns a sequence of tuples that are neighbors of the given cell"
  [x y]
  (for [mx [-1 0 1]
        my [-1 0 1]]
    (when-not (and (zero? mx) (zero? my))
      [(+ x mx) (+ y my)])))

;; Use this for now

;; massive speedup by encoding points as ints instead of vectors.
5
(defn cell-lives?-slow
  [x y grid]
  (let [live-neighbors (count (filter grid (neighbors x y)))]
    (or
     (= 3 live-neighbors)
     (and (grid [x y])
          (= 2 live-neighbors)))))

(defn cell-lives?-falsy
  [x y grid]
  false)

(defn cell-lives?-truthy
  [x y grid]
  true)

(defn count-live-neighbors
  [x y grid]
  (loop [ix -1
         iy -1
         n 0]
    (let [t (grid [(+ x ix) (+ y iy)])]
      (if (= ix iy 1)
        n
        (if (= ix 1)
          (recur -1 (inc iy) (if t (inc n) n))
          (recur (inc ix) iy (if t (inc n) n)))))))

(defn cell-lives?
  [x y grid]
  (let [live-neighbors (count-live-neighbors x y grid)]
    (or
     (= 3 live-neighbors)
     (and (grid [x y])
          (= 2 live-neighbors)))))

(defn game
  "Returns an infinite sequence of game states initialized with a
  random state"
  [size]
  (iterate (partial build-grid size cell-lives?-slow)
           (build-grid size rand-bool nil)))

;; Is checked before each game loop, set to true to stop the current execution.
(def *stop-flag* false)

(defn render-game!
  "Recursively renders successive game states."
  [canvas size game]
  (when game
    (render-grid! canvas (first game) size)
    (if-not *stop-flag*
      (js/setTimeout #(render-game! canvas size (rest game)) 1))))

(defn start
  "Set up and kick the whole thing off."
  [size]
  (let [canvas (.getElementById js/document "life-canvas")]
    (setup-canvas! canvas size)
    (render-game! canvas size (game size))))