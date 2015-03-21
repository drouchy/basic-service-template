(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [metosin/compojure-api "0.18.0"]
                 [metosin/ring-http-response "0.6.1"]
                 [org.clojure/clojure "1.6.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [environ "1.0.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.2"]
                 [http-kit "2.1.19"]
                 [cheshire "5.4.0"]
                 [com.netflix.hystrix/hystrix-clj "1.4.1"]]

  :min-lein-version "2.0.0"
  :ring {:handler {{name}}.handler/app}
  :main {{name}}.main
  :uberjar-name "{{name}}.jar"

  :profiles { :dev-common {:dependencies [[ring-mock "0.1.5"]
                                          [peridot "0.3.1"]]
                           :plugins [[lein-ring "0.9.2"]
                                     [lein-environ "1.0.0"]
                                     [lein-ancient "0.6.5"]
                                     [com.jakemccrary/lein-test-refresh "0.6.0"]]}
              :uberjar-common { :aot :all}

              :dev     [:shared :dev-common :dev-local]
              :test    [:dev :test-local]
              :ci      [:test :ci-local]
              :uberjar [:shared :uberjar-common]})
