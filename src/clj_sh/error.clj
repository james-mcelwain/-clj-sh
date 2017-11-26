(ns clj-sh.error)

(defn right? [[hand val]]
  (= hand :right))

(defn left? [[hand val]]
  (= hand :left))

(defn unwrap
  ([either] (unwrap either identity))
  ([either f]
   (cond
     (left? either) (second either)
     (right? either) (f (second either))
     :else (throw "must pass either"))))

(defn EEXIST [file] (str "File exists " file))

(defn ENOENT [file] (str "No such file or directory " file))

(defn ENOTDIR [file] (str "Not a directory " file))

(defn EISDIR [file] (str "Is a directory " file))
