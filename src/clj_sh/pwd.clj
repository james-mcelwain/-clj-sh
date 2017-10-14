(ns clj-sh.pwd)

(defn pwd [] (env :cwd))
