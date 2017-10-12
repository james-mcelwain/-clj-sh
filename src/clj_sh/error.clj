(ns clj-sh.error)

(defn ENOENT [file] (str "No such file or directory " file))

(defn ENOTDIR [file] (str "Not a directory " file))
