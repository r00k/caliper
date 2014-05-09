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
    {:content (html (views/client-form))}))

(defn client-index []
  (layout/render
    "app.html"
    {:content (let [clients (db/all-clients)]
                (html
                  [:h2 "Clients"]
                  [:p
                   [:a {:href "/clients/new" :id "new-client"} "Add new"]]
                  [:br]
                  [:table.table
                   [:tr
                    [:th "First name"]
                    [:th "Last name"]
                    [:th "DOB"]
                    [:th "Date of accident"]
                    [:th "Records depts"]]
                   (for [{:keys [first_name last_name date_of_birth date_of_accident records_departments]} clients]
                     [:tr
                      [:td first_name]
                      [:td last_name]
                      [:td date_of_birth]
                      [:td date_of_accident]
                      [:td (map :department_title records_departments)]])]))}))

(defn create-client [params]
  (db/create-client params)
  (client-index))

(defroutes client-routes
  (GET "/clients/new" [] (new-client))
  (POST "/clients" [_ :as request] (create-client (:form-params request)))
  (GET "/clients" [] (client-index)))
