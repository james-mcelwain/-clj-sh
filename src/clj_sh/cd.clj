(ns clj-sh.cd
  (:require
   [clj-sh.error :as error]
   [clj-sh.env :refer [env]]
   [clj-sh.util.file :refer [file-path]]))

(defn maybe-cd [target-dir]
  (let [{file :file path :path} (file-path target-dir)]
    (cond (not (.exists file)) [:left (error/ENOENT path)]
          (not (.isDirectory file)) [:left (error/ENOTDIR path)]
          :else [:right (env :cwd (.toString path))])))

(defn cd [target-dir]
  ;; discard error, we can return it normally
  (second (maybe-cd target-dir)))
