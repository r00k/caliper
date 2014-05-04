(ns caliper.test.features.client-requests
  (:use clj-webdriver.taxi
        clojure.test)
  (:require [caliper.models.db :as db]))

(declare create-client)

(deftest test-client-creation
  (is (= 1
         (do
           (create-client)
           (count (db/all-clients))))))

(defn create-client []
  (set-driver! {:browser :firefox})
  (to "http://localhost:3000")
  (click "#clients-index")
  (click "#new-client")
  (input-text "#first_name" "Ben")
  (submit ".btn")
  (quit))
