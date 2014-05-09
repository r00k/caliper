(ns caliper.views.clients
  (:use hiccup.core
        hiccup.form
        caliper.views.layout)
  (:require [caliper.models.db :as db]))

(defn- records-departments-options []
  (for [{:keys [id department_title]} (db/all-records-departments)]
    [:div.checkbox
     [:label
      (check-box "records_department_ids" false id)
      department_title]]))

(defn client-form []
  (form-to {:role "form"} [:post "/clients"]
             [:legend "New client"]
             [:div.form-group
              [:label { :for "first_name" } "First name"]
              (text-field {:class "form-control"} "first_name")]

             [:div.form-group
              [:label { :for "last_name" } "Last name"]
              (text-field {:class "form-control"} "last_name")]

             [:div.form-group
              [:label { :for "date_of_birth" } "Date of birth"]
              (text-field {:class "form-control" :placeholder "YYYY-MM-DD"} "date_of_birth")]

             [:div.form-group
              [:label { :for "date_of_accident" } "Date of accident"]
              (text-field {:class "form-control" :placeholder "YYYY-MM-DD"} "date_of_accident")]

             [:label {:for "treatments"} "Records Departments"]
             (records-departments-options)

             (submit-button {:class "btn btn-default"} "Create client")))
