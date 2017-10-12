(ns clj-sh.core
  (:require
   [clj-sh.error :as error]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def !cwd (atom (.getCanonicalPath (io/file "."))))

(defn pwd [] @!cwd)

(defn cd [tar]
  (let [parts (concat (str/split @!cwd #"/") (str/split tar #"/"))
        path (.normalize (java.nio.file.Paths/get "/" (into-array parts)))
        file (io/file path)]
    (if (not (.exists file))
      (error/ENOENT path)
      (swap! !cwd (fn [dir] (.toString path))))))
