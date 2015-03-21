(ns {{name}}.core-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [{{name}}.handler :refer :all]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest healhcheck-test
  (testing "Test GET request to /healthcheck returns the expected response"
    (let [response (app (-> (mock/request :get  "/healthcheck")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= body               {:health-check {:status "OK"}})))))

(deftest not-found
  (testing "Test GET request to an unknown endpoint returns an error"
    (let [response (app (-> (mock/request :get  "/unknown")))
          body     (parse-body (:body response))]
      (is (= (:status response)              404))
      (is (= (get-in body [:error :code])    404))
      (is (= (get-in body [:error :message]) "uri not found")))))
