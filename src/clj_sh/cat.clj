(ns clj-sh.cat
  (:require [clj-sh.util.file :refer [read-file-to-text]]
            [clj-sh.error :as error]))

(defn cat [& targets]
  (clojure.string/join "\n" (map #(-> % read-file-to-text error/unwrap) targets)))
