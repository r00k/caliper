(ns caliper.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [caliper.models.schema :as schema])
  (:require [clj-time.format :as time])
  (:require [clj-time.coerce :as coerce])
  (:require [clj-time.core :as t]))

(declare clients records_departments)

(defdb db schema/db-spec)

;; Clients 
(defentity clients
  (many-to-many records_departments :clients_records_departments))

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


;; Records depts
(defentity records_departments
  (many-to-many clients :clients_records_departments))

(defn all-records-departments []
  (select records_departments))

(defn create-records-department [records-dept-attributes]
  (insert records_departments (values records-dept-attributes)))

(defn destroy-all-records-departments []
  (delete records_departments))
