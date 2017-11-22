(ns clj-sh.cp
  (:require
   [clj-sh.error :as error]
   [clj-sh.util.file :refer [file-path]]))

(defmulti cp (fn [& args] (first args)))

(defmethod cp :default [p1 p2]
  (let [{file1 :file path1 :path} (file-path p1)
        {file2 :file path2 :path} (file-path p2)]
    (cond
      (not (.exists file1)) (error/ENOENT p1)
      (.exists file2) (error/EEXIST p2)
      (not (.exists (.getParentFile file2))) (error/ENOENT p2)
      :else (java.nio.file.Files/copy path1 path2 java.nio.file.StandardCopyOption/COPY_ATTRIBUTES))))

(cp "./project.clj" "../ok")
