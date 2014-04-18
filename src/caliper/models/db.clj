(ns caliper.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [caliper.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity clients)

(defn create-client [client-attributes]
  (insert clients (values client-attributes)))

;; (defn update-user [id first-name last-name email]
;;   (update users
;;   (set-fields {:first_name first-name
;;                :last_name last-name
;;                :email email})
;;   (where {:id id})))
;; 
;; (defn get-user [id]
;;   (first (select users
;;                  (where {:id id})
;;                  (limit 1))))
