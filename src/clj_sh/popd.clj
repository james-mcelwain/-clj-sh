(ns clj-sh.popd
  (:require
   [clj-sh.env :refer [env]]
   [clj-sh.cd :refer [maybe-cd]]
   [clojure.core.match :refer [match]]))

(defn popd []
  (if (empty? (env :dir-stack))
    "Directory stack is empty..."
    (let [[x & xs] (env :dir-stack)
          either (maybe-cd x)]
      (match either
             [:left error] error
             [:right res] (do (env :dir-stack xs) res)))))
