(ns clj-sh.core-test
  (:require [clojure.test :refer :all]
            [clj-sh.core :refer :all]
            [clj-sh.env :refer [env]]))

(deftest cd-home
  (cd "~")
  (is (= (env :home) (env :cwd))))

(deftest cd-root
  (cd "/lib")
  (is (= (env :cwd) "/lib")))

(deftest cd-relative
  (cd "./src")
  (is (= false true)))
