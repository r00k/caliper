(ns caliper.test.schema-test
  (:use clojure.test
        caliper.models.schema))

(deftest test-heroku-database-parsing
  (testing "when all elements are present"
    (is (= {:classname "org.postgresql.Driver"
            :subprotocol "postgresql"
            :user "user"
            :password "pass"
            :subname "//host:1234/path"}
          (heroku-database-url->jdbc-connection-map "postgres://user:pass@host:1234/path")))))
