(ns caliper.test.features.records-request-test
  (:use clj-webdriver.taxi
        clojure.test
        caliper.test.support.db-cleaner
        caliper.browser)
  (:require [caliper.models.db :as db]))

(use-fixtures :each clean-database)

(declare create-client-with-records-department)

(deftest test-records-request-generation
  (try
    (browser-up)
    (create-client-with-records-department "DFCI")
    (click {:tag :a :text "DFCI"})
    (is (find-element {:tag :p :text "Joshua P. Zisson"}))
    (finally
      (browser-down))))

(defn create-client-with-records-department [title]
  (db/create-records-department {:department_title title})
  (to "http://localhost:3000")
  (click "#clients-index")
  (click "#new-client")
  (input-text "#first_name" "Ben")
  (select "#records_department_ids")
  (submit ".btn"))
