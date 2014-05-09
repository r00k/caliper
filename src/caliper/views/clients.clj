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
  (html
    (form-to
      {:role "form"} [:post "/clients"]
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

      (submit-button {:class "btn btn-default"} "Create client"))))

(defn index []
  (let [clients (db/all-clients)]
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
       (for [{:keys [first_name last_name
                     date_of_birth date_of_accident
                     records_departments]}
             clients]
         [:tr
          [:td first_name]
          [:td last_name]
          [:td date_of_birth]
          [:td date_of_accident]
          [:td (map :department_title records_departments)]])])))
