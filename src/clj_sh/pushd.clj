(ns clj-sh.pushd
  (:require
   [clj-sh.cd :refer [maybe-cd]]
   [clj-sh.env :refer [env]]
   [clojure.core.match :refer [match]]))

(env :dir-stack '())

(defn pushd [dir]
  (let [either (maybe-cd dir)
        prev-dir (env :cwd)]
    (match either
           [:left error] error
           [:right res] (do (env :dir-stack (cons prev-dir (env :dir-stack))) dir))))
