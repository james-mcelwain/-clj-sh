(ns clj-sh.ls
  (:require
   [clj-sh.error :as error]
   [clj-sh.util.file :refer [file-path]]
   [clj-sh.util.java :refer [to-alist]]))


(defn ls-printer [res file]
  (let [d (if (.isDirectory file) "d" "-")
        r (if (.canRead file) "r" "-")
        w (if (.canWrite file) "w" "-")
        x (if (.canExecute file) "x" "-")
        name (.getName file)]
    (str res d r w x " " name "\n")))

(defn get-sorted-files [dir]
  (let [files (to-alist (.listFiles dir))]
    (do
      (java.util.Collections/sort files ci-compare)
      files)))

(get-sorted-files (:file (file-path "/home")))

(defn ls-la [target]
  (let [{dir :file} (file-path target)]
      (if (not (.isDirectory dir))
          (error/ENOTDIR target))
      (let [files (get-sorted-files dir)]
        (reduce ls-printer "" files))))

(defmulti ls (fn [& args] (first args)))
(defmethod ls :default [target] target)
(defmethod ls :la [_ target] (ls-la target))
