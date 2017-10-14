(ns clj-sh.rm
  (:require [clj-sh.env :refer [env]]))

(defmulti rm identity)
(defmethod rm :rf [_ path] path)
(defmethod rm :default [_] :default)
