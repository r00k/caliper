(ns caliper.test.support.db-cleaner
  (:use caliper.models.db))

(defn clean-database [f]
  (destroy-all-records-departments)
  (destroy-all-clients)
  (f))
