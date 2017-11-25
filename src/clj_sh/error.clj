(ns clj-sh.error
  (:require [clojure.core.match :refer [match]]))

(defn unwrap
  ([either] (unwrap either identity))
  ([either f] (match either
                     [:left err] err
                     [:right val] (f val))))

(defn EEXIST [file] (str "File exists " file))

(defn ENOENT [file] (str "No such file or directory " file))

(defn ENOTDIR [file] (str "Not a directory " file))

(defn EISDIR [file] (str "Is a directory " file))
