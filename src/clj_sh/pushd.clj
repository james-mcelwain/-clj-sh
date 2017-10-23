(ns clj-sh.pushd
  (:require
   [clj-sh.cd :refer [maybe-cd]]
   [clj-sh.env :refer [env]]
   [clj-sh.error :as error]))

(env :dir-stack '())

(defn pushd [dir]
  (let [either (maybe-cd dir)
        prev-dir (env :cwd)]
    (error/unwrap either #(do (env :dir-stack (cons prev-dir (env :dir-stack))) %))))
