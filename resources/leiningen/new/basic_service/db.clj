(ns {{name}}.db
  (:require [environ.core :refer [env]]
            [ragtime.main          :as migration]
            [clojure.tools.logging :as log]
            [ragtime.sql.files])
  (:use korma.core
        [korma.db :only (defdb transaction)]))

(def db-config (env :db))

(def db-spec
  {:subprotocol (:protocol db-config)
   :subname (str "//" (:host db-config) ":" (:port db-config) "/" (:database db-config))
   :user (:user db-config)
   :password (:password db-config)})

(def migration-url
  (str "jdbc:" (:protocol db-config) "://" (:host db-config) ":" (:port db-config) "/" (:database db-config) "?user=" (:user db-spec) "&password=" (:password db-spec)))

(defn migrate-database []
  (migration/migrate {:database migration-url :migrations "ragtime.sql.files/migrations"}))

(defn- uuid [] (str (java.util.UUID/randomUUID)))
