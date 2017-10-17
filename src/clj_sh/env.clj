(ns clj-sh.env
  (:require [clojure.java.io :as io]))

(def !env (atom {}))
(defn- get-env [key]
  (let [val (get @!env key false)]
    (if-not (= val false)
      val
      "")))

(defn- set-env [key value]
  (swap! !env assoc key value)
  value)

(defn env
  ([] @!env)
  ([key] (get-env key))
  ([key value] (set-env key value)))

(env :cwd (.getCanonicalPath (io/file ".")))
(env :home (System/getenv "HOME"))


