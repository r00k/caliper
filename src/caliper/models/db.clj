(ns caliper.models.db
  (:use korma.core
        [korma.db :only (defdb)]
        [clojure.walk :only (keywordize-keys)])
  (:require [caliper.models.schema :as schema])
  (:require [clj-time.format :as time])
  (:require [clj-time.coerce :as coerce])
  (:require [clj-time.core :as t]))

(declare clients records_departments clients_records_departments)

(defdb db schema/db-spec)

;; ClientsRecordsDepartments
(defentity clients_records_departments)

(defn- char-to-int
  "Needed because wrap-params sends in single-digit
  integers as chars(!?)"
  [c]
  {:pre [(char? c)]}
  (Integer/parseInt (str c)))

(defn create-clients-records-department
  [client_id records_department_id]
  {:pre [(integer? records_department_id)
         (integer? client_id)]}
  (insert clients_records_departments 
          (values {:clients_id client_id
                   :records_departments_id records_department_id})))

(defn create-clients-records-departments
  [client_id records_department_ids]
  {:pre [(not (empty? records_department_ids))]}
  (println (map type records_department_ids))
  (doall
    (map
      (partial create-clients-records-department client_id)
      (map char-to-int records_department_ids))))

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

(defn remove-records-departments-ids [m]
  (dissoc m :records_department_ids))

(defn parse-client-attributes [m]
  (-> m
      (keywordize-keys)
      (parse-dates)
      (remove-records-departments-ids)))

(defn create-client [client-attributes]
  (let [parsed-attributes (parse-client-attributes client-attributes)
        client (insert clients (values parsed-attributes))]
    (create-clients-records-departments
      (:id client)
      (get client-attributes "records_department_ids"))
    client))

(defn all-clients []
  (select clients))

(defn find-client [id]
  (first (select clients
                 (where {:id id})
                 (limit 1))))

(defn destroy-all-clients []
  (delete clients_records_departments)
  (delete clients))

;; Records depts
(defentity records_departments
  (many-to-many clients :clients_records_departments))

(defn all-records-departments []
  (select records_departments))

(defn create-records-department [records-dept-attributes]
  (insert records_departments (values records-dept-attributes)))

(defn destroy-all-records-departments []
  (delete clients_records_departments)
  (delete records_departments))
