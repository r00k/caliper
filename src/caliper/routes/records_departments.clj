(ns caliper.routes.records-departments
  (:use compojure.core)
  (:require [hiccup.core :refer :all])
  (:require [hiccup.form :as f])
  (:require [caliper.models.db :as db])
  (:require [caliper.views.layout :as layout])
  (:require [compojure.core :refer [defroutes]]))

(defn- records-department-form []
  (f/form-to {:role "form"} [:post "/records-departments"]
             [:legend "New records dept"]

             [:div.form-group
              [:label { :for "Department title" } "Department title"]
              (f/text-field {:class "form-control"} "department_title")]

             (f/submit-button {:class "btn btn-default"} "Create records dept")))

(defn records-departments-index []
  (layout/render
    "app.html"
    {:content (let [records-departments (db/all-records-departments)]
                (html
                  [:h2 "All records-departments"]
                  [:table.table
                   [:tr
                    [:th "First name"]
                    [:th "Last name"]
                    [:th "DOB"]
                    [:th "Date of accident"]]
                   (for [{:keys [department_title hospital_name]} records-departments]
                     [:tr
                      [:td department_title]
                      [:td hospital_name]])]))}))

(defn new-records-department []
  (layout/render
    "app.html"
    {:content (html (records-department-form))}))

(defn create-records-department [records-dept-attributes]
  (db/create-records-department records-dept-attributes)
  (records-departments-index))

(defroutes records-departments-routes
  (GET "/records-departments/new" [] (new-records-department))
  (POST "/records-departments" [_ :as request] (create-records-department (:params request))))
