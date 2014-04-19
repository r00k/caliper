(ns caliper.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [caliper.models.schema :as schema])
  (:require [clj-time.format :as time])
  (:require [clj-time.coerce :as coerce]))

(defdb db schema/db-spec)

(defentity clients)

(defn str->date [date-string]
  "Takes 2014-04-19 and puts in in db-ready format"
  (when date-string
    (coerce/to-sql-date (time/parse (time/formatters :date) date-string))))

(defn parse-dates [m]
  (assoc m :date_of_birth (str->date (:date_of_birth m))))

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
