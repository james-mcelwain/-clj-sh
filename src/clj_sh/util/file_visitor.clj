(ns clj-sh.util.file-visitor)

(def visitor
  (reify java.nio.file.FileVisitor
    (postVisitDirectory [this dir exc] (java.nio.file.FileVisitResult/CONTINUE))
    (preVisitDirectory [this dir atts] (java.nio.file.FileVisitResult/CONTINUE))
    (visitFile [this file attrs] (java.nio.file.FileVisitResult/CONTINUE))
    (visitFileFailed [this file exc] (java.nio.file.FileVisitResult/TERMINATE))))

(defn walk-file-tree [path]
  (java.nio.file.Files/walkFileTree path visitor))
