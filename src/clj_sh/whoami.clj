(ns clj-sh.whoami)

(defn whoami [] (System/getProperty "user.name"))
