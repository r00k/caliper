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

(defn new-records-department []
  (layout/render
    "app.html"
    {:content (html (records-department-form))}))

(defroutes records-departments-routes
  (GET "/records-departments/new" [] (new-records-department)))
