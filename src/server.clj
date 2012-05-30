(ns server
  (:require [ring.util.response :refer (redirect)]
            [compojure.core :refer (routes GET)]
            [compojure.route :as route]))

(def handler
  (routes
   (GET "/" [] (redirect "/index.html"))
   (route/resources "/")
   (route/not-found "Page not found")))