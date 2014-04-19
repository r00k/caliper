(ns caliper.test.handler
  (:use clojure.test
        ring.mock.request
        caliper.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest test-client
  (testing "GET /clients/new"
    (let [response (app (request :get "/clients/new"))]
      (is (= (:status response) 200))))
  (testing "POST /clients"
    (let [response (app (request :post "/clients" {:first_name "Ben"}))]
      (is (= (:status response) 200))
      (is (re-find #"Ben" (:body response))))))


      
