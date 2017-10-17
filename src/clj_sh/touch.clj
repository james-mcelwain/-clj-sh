(ns clj-sh.touch
  (:require
   [clj-sh.env :refer [env]]
   [clojure.java.io :as io]
   [clojure.string :as str]))


(defn touch [target]
  (let [parts (concat (str/split (env :cwd) #"/") (str/split target #"/"))
        path (.normalize (java.nio.file.Paths/get "/" (into-array parts)))
        file (io/file path)]

    (if (or (.exists file) (.isDirectory file))
      (.setLastModified file (System/currentTimeMillis))
      (io/writer file))
    '()))
