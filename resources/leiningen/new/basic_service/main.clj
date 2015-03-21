(ns {{name}}.main
  (:require [{{name}}.handler :refer [app]]
            [clojure.tools.logging :as logger])
  (:use ring.adapter.jetty)
  (:gen-class))

(defn- start-application []
  (logger/info "starting the application on port " 3000)
  (run-jetty app {:port 3000}))

(defn- init []
  (com.netflix.hystrix.strategy.concurrency.HystrixRequestContext/initializeContext))

(defn -main [& args]
  (init)
  (start-application))
