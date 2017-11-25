(ns clj-sh.mkdir
  (:require
   [clj-sh.util.file :refer [file-path]]
   [clj-sh.error :as error]))

(defn mkdir [dir]
  (let [file (:file (file-path dir))]
    (if (.mkdir file)
      dir
      (error/EEXIST dir))))


