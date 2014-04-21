(ns caliper.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [caliper.models.schema :as schema])
  (:require [clj-time.format :as time])
  (:require [clj-time.coerce :as coerce])
  (:require [clj-time.core :as t]))

(defdb db schema/db-spec)

(defentity clients)

(defn str->date [date-string]
  "returns a java.sql.Date for that day at noon"
  (when date-string
    (coerce/to-sql-date
      (str date-string "T12:00:00"))))

(defn parse-dates [m]
  (-> m
      (assoc :date_of_birth (str->date (:date_of_birth m)))
      (assoc :date_of_accident (str->date (:date_of_accident m)))))

(defn create-client [client-attributes]
  (let [parsed-attributes (parse-dates client-attributes)]
    (insert clients (values parsed-attributes))))

(defn all-clients []
  (select clients))

(defn find-client [id]
  (first (select clients
                 (where {:id id})
                 (limit 1))))

;; (defn update-user [id first-name last-name email]
;;   (update users
;;   (set-fields {:first_name first-name
;;                :last_name last-name
;;                :email email})
;;   (where {:id id})))
