(ns {{name}}.handler
  (:require [compojure.api.sweet :refer :all]
            [compojure.core          :refer [ANY]]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(def ^:private ok-response        {:health-check {:status "OK"}})
(def ^:private not-found-response {:error {:code 404 :message "uri not found"}})

(s/defschema HealthCheck {:health-check {:status String}})
(s/defschema ApiError    {:error {:code Integer :message String}})

(defapi app
  (swagger-docs
    :title "{{name}}")
  (swaggered "api"
    :description "healthcheck"
    (GET* "/healthcheck" []
      :return HealthCheck
      :summary "returns the healthcheck status"
      (ok ok-response))
    (ANY "*" []
      :return ApiError
      :summary "returns en error as the uri is unknown"
      (not-found not-found-response))))

