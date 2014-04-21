(ns caliper.test.records-departments-routes
  (:use clojure.test
        ring.mock.request
        caliper.handler))

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
      #_(is (re-find #"1983-04-16" (:body response)))
      #_(is (re-find #"2014-04-01" (:body response))))))
