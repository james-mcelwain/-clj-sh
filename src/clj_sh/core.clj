(ns clj-sh.core
  (:require
   [clj-sh.cat :refer [cat]]
   [clj-sh.cd :refer [cd]]
   [clj-sh.env :refer [env]]
   [clj-sh.ls :refer [ls]]
   [clj-sh.popd :refer [popd]]
   [clj-sh.pushd :refer [pushd]]
   [clj-sh.pwd :refer [pwd]]
   [clj-sh.rm :refer [rm]]
   [clj-sh.touch :refer [touch]]))
