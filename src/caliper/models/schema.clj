(ns caliper.models.schema)

(defn heroku-database-url->jdbc-connection-string
  "Converts Heroku's DATABASE_URL to a JDBC connection string"
  [heroku-database-url]
  (let [db-uri (java.net.URI. heroku-database-url)
        user-and-password (clojure.string/split (.getUserInfo db-uri) #":")]
    {:classname "org.postgresql.Driver"
     :subprotocol "postgresql"
     :user (get user-and-password 0)
     :password (get user-and-password 1) ; may be nil
     :subname (if (= -1 (.getPort db-uri))
                (format "//%s%s" (.getHost db-uri) (.getPath db-uri))
                (format "//%s:%s%s" (.getHost db-uri) (.getPort db-uri) (.getPath db-uri)))}))

(def local-database
  {:subprotocol "postgresql"
   :subname "//localhost/caliper"})

(def db-spec
  (if-let [database-url (System/getenv "DATABASE_URL")]
    (heroku-database-url->jdbc-connection-string)
    local-database))
