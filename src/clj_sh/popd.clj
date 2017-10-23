(ns clj-sh.popd
  (:require
   [clj-sh.env :refer [env]]
   [clj-sh.error :as error]
   [clj-sh.cd :refer [maybe-cd]]))

(defn popd []
  (if (empty? (env :dir-stack))
    "Directory stack is empty..."
    (let [[x & xs] (env :dir-stack)
          either (maybe-cd x)]
      (error/unwrap either #(do (env :dir-stack xs) %)))))
