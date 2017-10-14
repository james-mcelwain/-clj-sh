(ns clj-sh.pwd
  (:require [clj-sh.env :refer [env]]))

(defn pwd [] (env :cwd))
