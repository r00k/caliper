(ns caliper.test.features.client-requests
  (:use clj-webdriver.taxi
        clojure.test
        caliper.test.support.db-cleaner
        caliper.browser)
  (:require [caliper.models.db :as db]))

(use-fixtures :each clean-database)

(declare create-client create-client-with-records-department)

(deftest test-client-creation
  (browser-up)
  (testing "a record is created in the database"
    (is (= 1
           (do
             (create-client)
             (count (db/all-clients))))))
  (testing "the selected records department is shown"
    (do
      (create-client-with-records-department "DFCI")
      (is (find-element {:tag :td
                         :text "DFCI"}))))
  (browser-down))

(defn create-client []
  (to "http://localhost:3000")
  (click "#clients-index")
  (click "#new-client")
  (input-text "#first_name" "Ben")
  (submit ".btn"))

(defn create-client-with-records-department [title]
  (db/create-records-department {:department_title title})
  (to "http://localhost:3000")
  (click "#clients-index")
  (click "#new-client")
  (input-text "#first_name" "Ben")
  (select "#records_department_id")
  (submit ".btn"))
