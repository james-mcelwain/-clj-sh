(ns clj-sh.util.file
  (:require
   [clj-sh.env :refer [env]]
   [clj-sh.error :as error]
   [clojure.java.io :as io]
   [clojure.string :as str]))


(defn file-path [target]
  (cond
    ;; relative to home
    (= (first target) \~)
    (let [path (str/replace-first target "~" (env :home))
          file (io/file path)]
      {:file file :path path})

    ;; absolute path
    (= (first target) \/)
    (let [path target
          file (io/file target)]
      {:file file :path path})

    ;; relative to cwd
    :else
    (let [parts (concat (str/split (env :cwd) #"/") (str/split target #"/"))
          path (.normalize (java.nio.file.Paths/get "/" (into-array parts)))
          file (io/file path)]
      {:file file :path path})))

(defn read-file-to-text [target]
  (let [{file :file} (file-path target)]
    (cond (not (.exists file)) [:left (error/ENOENT target)]
          (.isDirectory file) [:left (error/EISDIR target)]
          :else [:right (slurp file)])))
