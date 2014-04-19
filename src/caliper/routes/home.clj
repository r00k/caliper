(ns caliper.routes.home
  (:use compojure.core)
  (:require [caliper.views.layout :as layout]
            [caliper.util :as util]))

(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))
