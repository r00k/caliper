(ns caliper.test.db
  (:use clojure.test
        caliper.models.db
        korma.core))

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

(deftest test-destroy-all-clients
  (is (= []
         (do
           (create-client {:first_name "Bob"})
           (destroy-all-clients)
           (all-clients)))))

(deftest test-create-client
  (testing "creates a join record for a single records department"
    (let [records-department (create-records-department {:department_title "DFCI"})
          client (create-client {:records_department_ids (vector (:id records-department))})]
      (is (not-empty (select clients_records_departments 
                             (where {:clients_id (:id client)
                                     :records_departments_id (:id records-department)}))))))
  (testing "creates join records when multiple depts are specified"
    (let [records-department-one (create-records-department {:department_title "One"})
          records-department-two (create-records-department {:department_title "Two"})
          client (create-client {:records_department_ids (map :id [records-department-one records-department-two])})]
      (is (and
            (not-empty (select clients_records_departments
                             (where {:clients_id (:id client)
                                     :records_departments_id (:id records-department-one)})))
            (not-empty (select clients_records_departments
                               (where {:clients_id (:id client)
                                       :records_departments_id (:id records-department-two)}))))))))
