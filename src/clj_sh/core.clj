(ns clj-sh.core
  (:require
   [clj-sh.error :as error]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def !cwd (atom (.getCanonicalPath (io/file "."))))

;; environment variables are atoms, even if they aren't changed in practice
(def !home (atom (System/getenv "HOME")))

(defn pwd [] @!cwd)

(defn cd [target-dir]
  (let [change-path
        (fn [file path]
          (cond (not (.exists file)) (error/ENOENT path)
                (not (.isDirectory file)) (error/ENOTDIR path)
                :else (swap! !cwd (fn [dir] (.toString path)))))]


    (cond

      ;; relative to home
      (= (first target-dir) \~)
      (let [path (str/replace-first target-dir "~" @!home)
            file (io/file path)]
        (change-path file path))

      ;; absolute path
      (= (first target-dir) \/)
      (let [path target-dir
            file (io/file target-dir)]
        (change-path file path))

      ;; relative to cwd
      :else
      (let [parts (concat (str/split @!cwd #"/") (str/split target-dir #"/"))
            path (.normalize (java.nio.file.Paths/get "/" (into-array parts)))
            file (io/file path)]
        (change-path file path)))))
