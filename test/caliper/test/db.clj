(ns caliper.test.db
  (:use clojure.test
        caliper.models.db))

(deftest test-str->date
  (testing "returns a java.sql.Date for that day at noon"
    (is (= #inst "2014-04-16T12:00:00.000-00:00" (str->date "2014-04-16")))))

(deftest test-parse-dates
  (testing "returns a map with the dates parsed into sql-friendly form"
    (is (= {:date_of_birth #inst "1983-04-16T12:00:00-00:00"}
           (parse-dates {:date_of_birth "1983-04-16"})))))

(deftest roundtrip
  (testing "dates are maintained through a roundtrip"
    ; TODO: why does this come out oddly?
    (is (= #inst "1983-04-16T05:00:00"
           (let [client-id (:id (create-client {:first_name "Ben", :date_of_birth "1983-04-16"}))]
             (:date_of_birth (find-client client-id)))))))
