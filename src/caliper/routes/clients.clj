(ns caliper.routes.clients
  (:use compojure.core)
  (:require [hiccup.core :refer :all])
  (:require [hiccup.form :as f])
  (:require [caliper.models.db :as db]))

(defn- client-form []
  (f/form-to [:post "/clients"]
             [:label { :for "first_name" } "First Name:"]
             (f/text-field "first_name")
             (f/submit-button "Create client")))

(defn new-client []
  (html
    (client-form)))

(defn create-client [params]
  (db/create-client params))

(defroutes client-routes
  (GET "/clients/new" [] (new-client))
  (POST "/clients" [_ :as request] (create-client (:params request))))
