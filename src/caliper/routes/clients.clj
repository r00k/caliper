(ns caliper.routes.clients
  (:use compojure.core)
  (:require [hiccup.core :refer :all])
  (:require [hiccup.form :as f])
  (:require [caliper.models.db :as db])
  (:require [caliper.views.layout :as layout])
  (:require [caliper.views.clients :as views]))

(defn new-client []
  (layout/render
    "app.html"
    {:content (views/client-form)}))

(defn client-index []
  (layout/render
    "app.html"
    {:content (views/index)}))

(defn create-client [params]
  (db/create-client params)
  (client-index))

(defroutes client-routes
  (GET "/clients/new" [] (new-client))
  (POST "/clients" [_ :as request] (create-client (:form-params request)))
  (GET "/clients" [] (client-index)))
