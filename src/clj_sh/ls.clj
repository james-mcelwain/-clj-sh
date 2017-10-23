(ns clj-sh.ls
  (:require
   [clj-sh.error :as error]
   [clj-sh.util.file :refer [file-path]]
   [clj-sh.util.java :refer [to-alist]]))

;; NB: We have to use C-c C-p or cider-pprint-eval-last-sexp to be able
;; to see the pretty printed version of this command

(defn ls-la-printer [res file]
  (let [d (if (.isDirectory file) "d" "-")
        r (if (.canRead file) "r" "-")
        w (if (.canWrite file) "w" "-")
        x (if (.canExecute file) "x" "-")
        name (.getName file)]
    (str res d r w x " " name "\n")))

(defn ls-printer [files target]
  (let [prefix (if (= (last target) \/) target (str target "/"))]
    (clojure.string/join " " (map #(clojure.string/replace % (re-pattern prefix) "") files))))

(defn get-sorted-files [dir]
  (let [files (to-alist (.listFiles dir))]
    (do
      (java.util.Collections/sort files ci-compare)
      files)))

(defn hidden-file? [name]
  (not (= \. (first (.getName name)))))

(defn ls- [target]
  (let [{dir :file} (file-path target)]
    (if (not (.isDirectory dir))
      (error/ENOTDIR target))
    (let [files (filter hidden-file? (get-sorted-files dir))]
      (ls-printer (into [] files) target))))


(defn ls-a [target]
  (let [{dir :file} (file-path target)]
    (if (not (.isDirectory dir))
      (error/ENOTDIR target))
    (let [files (get-sorted-files dir)]
      (ls-printer (into [] files) target))))

(defn ls-la [target]
  (let [{dir :file} (file-path target)]
    (if (not (.isDirectory dir))
      (error/ENOTDIR target))
    (let [files (get-sorted-files dir)]
      (reduce ls-la-printer "" files))))

(defmulti ls (fn [& args] (first args)))
(defmethod ls :default [target] (ls- target))
(defmethod ls :a [_ target] (ls-a target))
(defmethod ls :la [_ target] (ls-la target))
