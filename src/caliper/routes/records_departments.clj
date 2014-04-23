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
              (f/text-field {:class "form-control"} "department_title")

              [:label { :for "Hospital name" } "Hospital name"]
              (f/text-field {:class "form-control"} "hospital_name")

              [:label { :for "Address line 1" } "Address line 1"]
              (f/text-field {:class "form-control"} "address_line_1")

              [:label { :for "Address line 2" } "Address line 2"]
              (f/text-field {:class "form-control"} "address_line_2")]

             (f/submit-button {:class "btn btn-default"} "Create records dept")))

(defn records-departments-index []
  (layout/render
    "app.html"
    (let [records-departments (db/all-records-departments)]
      {:content (html
                  [:h2 "All records-departments"]
                  [:table.table
                   [:tr
                    [:th "Depaprtment title"]
                    [:th "Hospital name"]
                    [:th "Address line 1"]
                    [:th "Address line 2"]]
                   (for [{:keys [department_title 
                                 hospital_name 
                                 address_line_1 
                                 address_line_2]} 
                         records-departments]
                     [:tr
                      [:td department_title]
                      [:td hospital_name]
                      [:td address_line_1]
                      [:td address_line_2]])])})))

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
