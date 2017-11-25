(ns clj-sh.rm
  (:require
   [clj-sh.env :refer [env]]
   [clj-sh.error :as error]
   [clj-sh.util.file :refer [file-path]]
   [clj-sh.util.file-visitor :refer [walk-file-tree]]))

;; visitor overrides
(defn visit-file [this file attrs]
  (java.nio.file.Files/delete file)
  (java.nio.file.FileVisitResult/CONTINUE))

(defn post-visit-directory [this dir exc]
  (if-not (nil? exc)
    (throw exc)
    (do (java.nio.file.Files/delete dir)
        (java.nio.file.FileVisitResult/CONTINUE))))

(defmulti rm (fn [& args] (first args)))

(defmethod rm :default [target]
  (let [{file :file path :path } (file-path target)]
    (if (not (.exists file))
      (error/ENOENT path)
      (do
        (java.nio.file.Files/delete path)
        target))))

(defmethod rm :rf [_ target]
  (let [{file :file path :path} (file-path target)]
    (if (not (.exists file))
      (error/ENOENT path)
      (try
        (do
          (walk-file-tree path {:visit-file visit-file
                                :post-visit-directory post-visit-directory})
          target)
        (catch clojure.lang.ExceptionInfo e
          (.toString e))))))

