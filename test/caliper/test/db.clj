(ns caliper.test.db
  (:use clojure.test
        caliper.models.db))

(deftest test-str->date
  (testing "returns a java.sql.Date for that day at noon"
    (is (= #inst "2014-04-16T12:00:00.000-00:00" (str->date "2014-04-16")))))

(deftest test-parse-dates
  (testing "returns a map with the dates parsed into sql-friendly form"
    (is (= {:date_of_birth #inst "1983-04-16T12:00:00-00:00"
            :date_of_accident #inst "2014-04-01T12:00:00-00:00"}
           (parse-dates {:date_of_birth "1983-04-16"
                         :date_of_accident "2014-04-01"})))))

(deftest test-destroy-all-records-depts
  (is (= []
         (do
           (create-records-department {:department_title "DFCI"})
           (destroy-all-records-departments)
           (all-records-departments)))))
