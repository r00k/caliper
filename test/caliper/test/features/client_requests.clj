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
  (to "http://localhost:3000"))

  (click "a[href*='login']")

  (input-text "#login_field" "dog")
  (input-text "#password" "patch")

  (submit "#password")
  (quit))
