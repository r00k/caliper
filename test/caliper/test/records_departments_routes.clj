(ns caliper.test.records-departments-routes
  (:use clojure.test
        ring.mock.request
        caliper.handler
        caliper.test.support.db-cleaner))

(use-fixtures :each clean-database)

(deftest REST-for-departments
  (testing "GET /records-departments/new"
    (let [response (app (request :get "/records-departments/new"))]
      (is (= (:status response) 200))))
  (testing "POST /records-departments"
    (let [response (app (request :post 
                                 "/records-departments" 
                                 {:department_title "DFCI Records Dept"
                                  :hospital_name "Dana-Farber"
                                  :address_line_1 "123 Main St"
                                  :address_line_2 "Boston MA 02114"}))]
      (is (= (:status response) 200))
      (is (re-find #"DFCI Records Dept" (:body response)))
      (is (re-find #"Dana-Farber" (:body response)))
      (is (re-find #"123 Main St" (:body response)))
      (is (re-find #"Boston MA 02114" (:body response))))))
