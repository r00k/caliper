(ns caliper.routes.records-departments
  (:use compojure.core)
  (:require [hiccup.core :refer :all])
  (:require [hiccup.form :as f])
  (:require [caliper.models.db :as db])
  (:require [caliper.views.layout :as layout])
  (:require [compojure.core :refer [defroutes]]))

(defn new-records-department []
  "hey")

(defroutes records-departments-routes
  (GET "/record-departments/new" [] (new-records-department)))
