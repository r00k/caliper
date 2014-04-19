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

(defn client-index []
  (let [clients (db/all-clients)]
    (html
      [:h1 "All clients"]
      [:ul
       (for [{:keys [first_name date_of_birth date_of_accident]} clients]
         [:li
          [:p first_name]
          [:p date_of_birth]
          [:p date_of_accident]])])))

(defn create-client [params]
  (db/create-client params)
  (client-index))

(defroutes client-routes
  (GET "/clients/new" [] (new-client))
  (POST "/clients" [_ :as request] (create-client (:params request)))
  (GET "/clients" [] (client-index)))
