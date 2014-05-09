(ns caliper.models.schema
  (:require [heroku-database-url-to-jdbc.core :as parse]))

(def local-database-map
  {:subprotocol "postgresql"
   :subname "//localhost/caliper"})

(def db-spec
  (if-let [database-url (System/getenv "DATABASE_URL")]
    (parse/korma-connection-map database-url)
    local-database-map))
