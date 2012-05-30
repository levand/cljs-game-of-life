(ns server
  (:require [compojure.core :refer (routes)]
            [compojure.route :as route]))

(def handler
  (routes
   (route/resources "/")
   (route/not-found "Page not found")))