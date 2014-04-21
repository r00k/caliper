(ns caliper.test.records-departments-routes
  (:use clojure.test
        ring.mock.request
        caliper.handler))

(deftest test-new-records-department
  (testing "GET /record-departments/new"
    (let [response (app (request :get "/records-department/new"))]
      (is (= (:status response) 200)))))
