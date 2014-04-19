(ns caliper.test.db
  (:use clojure.test
        caliper.models.db))

(deftest test-str->date
  (testing "returns a tagged literal date"
    (is (= #inst "2014-04-16" (str->date "2014-04-16")))))

(deftest test-parse-dates
  (testing "returns a map with the dates parsed into sql-friendly form"
    (is (= {:date_of_birth #inst "1983-04-16"}
           (parse-dates {:date_of_birth "1983-04-16"})))))

(deftest roundtrip
  (testing "can insert and retrieve clients"
    (is (= {:first_name "Ben", :date_of_birth #inst "1983-04-16"}
           (let [client-id (:id (create-client {:first_name "Ben", :date_of_birth "1983-04-16"}))]
             (select-keys (find-client client-id) [:first_name, :date_of_birth]))))))
