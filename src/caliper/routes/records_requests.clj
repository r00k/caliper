(ns caliper.routes.records-requests
  (:use compojure.core)
  (:require [hiccup.core :refer :all])
  (:require [caliper.models.db :as db])
  (:require [caliper.views.layout :as layout])
  (:require [compojure.core :refer [defroutes]])
  (:require [caliper.views.records-requests :as views]))

(defn- parse-params [params]
  (reduce (fn [acc [k v]] (conj acc {k (Integer/parseInt v)})) 
          {} 
          params))

(defn show-records-request [params]
  (let [parsed (parse-params params)
        client (db/find-client (:client_id parsed))
        records-department (db/find-records-department 
                             (:records_department_id parsed))]
    (layout/render
      "records_request.html"
      {:content 
       (views/show client records-department)})))

(defroutes records-requests-routes
  (GET "/records-request" [_ :as request] (show-records-request (:params request))))
