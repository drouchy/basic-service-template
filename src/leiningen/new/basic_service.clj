(ns leiningen.new.basic-service
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string      :as s]))

(def render (renderer "basic-service"))

(defn pad [len]
  (apply str (repeat len " ")))

(defn template-data [name opts]
  {:name           name
   :name-camel     (str (s/upper-case (subs name 0 1)) (subs name 1))
   :sanitized      (name-to-path name)
   :cl-test-readme "\n### Run the tests\n\n`lein test`\n"})

(defmulti option-files (fn [option data] option))

(defmethod option-files :base [_ data]
  [["src/{{sanitized}}/handler.clj"  (render "handler.clj" data)]
   ["src/{{sanitized}}/main.clj"     (render "main.clj" data)]
   ["src/logback.xml"                (render "logback.xml" data)]
   ["project.clj"                    (render "project.clj" data)]
   ["profiles.clj"                   (render "profiles.clj" data)]
   ["README.md"                      (render "README.md" data)]

   ["test/{{sanitized}}/core_test.clj" (render "test/clojure_core_test.clj" data)]
   ["test/utils/web.clj"               (render "test/web.clj" data)]])

(defmethod option-files :database [_ data]
  [])

(defn active-options [args]
  (for [arg args :when (re-matches #"\+[A-Za-z0-9-]+" arg)]
    (keyword (subs arg 1))))

(defn basic-service
  "Basic Api service project, inspired from the compojure api project"
  [name & args]
  (let [opts  (cons :base (active-options args))
        data  (template-data name opts)
        files (reduce into [] (map #(option-files % data) opts))]
    (main/info "Generating fresh 'lein new' basic-service project.")
    (apply ->files data files)))
