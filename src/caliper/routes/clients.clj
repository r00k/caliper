(ns caliper.routes.clients
  (:use compojure.core)
  (:require [hiccup.core :refer :all])
  (:require [hiccup.form :as f])
  (:require [caliper.models.db :as db])
  (:require [caliper.views.layout :as layout]))

(defn- client-form []
  (f/form-to {:role "form"} [:post "/clients"]
             [:legend "New client"]
             [:div.form-group
              [:label { :for "first_name" } "First name"]
              (f/text-field {:class "form-control"} "first_name")]

             [:div.form-group
              [:label { :for "last_name" } "Last name"]
              (f/text-field {:class "form-control"} "last_name")]

             [:div.form-group
              [:label { :for "date_of_birth" } "Date of birth"]
              (f/text-field {:class "form-control"} "date_of_birth")]

             [:div.form-group
              [:label { :for "date_of_accident" } "Date of accident"]
              (f/text-field {:class "form-control"} "date_of_accident")]

             (f/submit-button {:class "btn btn-default"} "Create client")))

(defn new-client []
  (layout/render
    "app.html"
    {:content (html (client-form))}))

(defn client-index []
  (layout/render
    "app.html"
    {:content (let [clients (db/all-clients)]
                (html
                  [:h2 "Clients"]
                  [:p
                   [:a {:href "/clients/new"} "Add new"]]
                  [:br]
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
