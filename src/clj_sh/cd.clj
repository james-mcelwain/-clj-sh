(ns clj-sh.cd
  (:require [clj-sh.env :refer [env]]
            [clj-sh.error :as error]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn cd [target-dir]
  (let [change-path
        (fn [file path]
          (cond (not (.exists file)) (error/ENOENT path)
                (not (.isDirectory file)) (error/ENOTDIR path)
                :else (env :cwd (.toString path))))]

    (cond

      ;; relative to home
      (= (first target-dir) \~)
      (let [path (str/replace-first target-dir "~" (env :home))
            file (io/file path)]
        (change-path file path))

      ;; absolute path
      (= (first target-dir) \/)
      (let [path target-dir
            file (io/file target-dir)]
        (change-path file path))

      ;; relative to cwd
      :else
      (let [parts (concat (str/split (env :cwd) #"/") (str/split target-dir #"/"))
            path (.normalize (java.nio.file.Paths/get "/" (into-array parts)))
            file (io/file path)]
        (change-path file path)))))
