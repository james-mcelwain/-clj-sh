(ns clj-sh.cat
  (:require [clj-sh.util.file :refer [read-file-to-text]]))

;; we are not going to implement all forms of cat obviously, just start with
;; printing the file

(defn cat [target]
  (read-file-to-text target))
