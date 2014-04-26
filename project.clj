(defproject caliper
  "0.1.0-SNAPSHOT"
  :repl-options {:init-ns caliper.repl}
  :dependencies [[ring-server "0.3.1"]
                 [ragtime "0.3.4"]
                 [environ "0.4.0"]
                 [markdown-clj "0.9.41"]
                 [com.taoensso/timbre "3.1.6"]
                 [korma "0.3.1"]
                 [org.clojure/clojure "1.6.0"]
                 [com.taoensso/tower "2.0.2"]
                 [log4j "1.2.17" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [selmer "0.6.5"]
                 [lib-noir "0.8.1"]
                 [compojure "1.1.6"]
                 [postgresql/postgresql "9.1-901.jdbc4"]
                 [hiccup "1.0.5"]
                 [clj-time "0.7.0"]]
  :ring {:handler caliper.handler/app,
         :init caliper.handler/init,
         :destroy caliper.handler/destroy}
  :cucumber-feature-paths ["test/features/"]
  :ragtime {:migrations ragtime.sql.files/migrations,
            :database (if (System/getenv "DATABASE_URL")
                        (let [heroku-url (System/getenv "DATABASE_URL")
                              db-uri (java.net.URI. heroku-url)
                              [username password] (clojure.string/split (.getUserInfo db-uri) #":")]
                          (str "jdbc:postgresql://" (.getHost db-uri) ":" (.getPort db-uri) (.getPath db-uri) "?user=" username "&password=" password))
                        "jdbc:postgresql://localhost/caliper")}
  :profiles {:uberjar {:aot :all}
             :production {:ring
                          {:open-browser? false, :stacktraces? false, :auto-reload? false}},
             :dev {:dependencies [[org.clojure/core.cache "0.6.3"]
                                  [ring/ring-devel "1.2.2"]
                                  [clj-webdriver/clj-webdriver "0.6.1"]
                                  [ring-mock "0.1.5"]],
                   :env {:dev true}}}
  :url "http://example.com/FIXME"
  :plugins [[lein-ring "0.8.10"]
            [lein-environ "0.4.0"]
            [lein-cucumber "1.0.2"]
            [ragtime/ragtime.lein "0.3.4"]]
  :description "FIXME: write description"
  :min-lein-version "2.0.0"
  :main ^:skip-aot caliper.handler)
