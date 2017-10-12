(ns clj-sh.core-test
  (:require [clojure.test :refer :all]
            [clj-sh.core :refer :all]))


(deftest cd-relative
  (cd "./src"))

(deftest cd-home
  (cd "~")
  (is (= @!cwd @!home)))

(deftest cd-root
  (cd "/lib")
  (is (= @!cwd "/lib")))
