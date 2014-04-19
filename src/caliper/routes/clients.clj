(ns caliper.routes.clients
  (:use compojure.core)
  (:require [hiccup.core :refer :all])
  (:require [hiccup.form :as f])
  (:require [caliper.models.db :as db])
  (:require [caliper.views.layout :as layout]))

(defn- client-form []
  (f/form-to [:post "/clients"]
             [:label { :for "first_name" } "First Name:"]
             (f/text-field "first_name")
             (f/submit-button "Create client")))

(defn new-client []
  (layout/render
    "app.html"
    {:content (html (client-form))}))

(defn client-index []
  (layout/render
    "app.html"
    {:content (let [clients (db/all-clients)]
                (html
                  [:h2 "All clients"]
                  [:table.table
                   [:tr
                    [:th "First name"]
                    [:th "Last name"]
                    [:th "DOB"]
                    [:th "Date of accident"]]
                   (for [{:keys [first_name last_name date_of_birth date_of_accident]} clients]
                     [:tr
                      [:td first_name]
                      [:td last_name]
                      [:td date_of_birth]
                      [:td date_of_accident]])]))}))

(defn create-client [params]
  (db/create-client params)
  (client-index))

(defroutes client-routes
  (GET "/clients/new" [] (new-client))
  (POST "/clients" [_ :as request] (create-client (:params request)))
  (GET "/clients" [] (client-index)))
