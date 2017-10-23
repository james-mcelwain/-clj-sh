(ns clj-sh.util.file-visitor)

(defn walk-file-tree [path {visit-file :visit-file
                            post-visit-directory :post-visit-directory
                            visit-file-failed :visit-file-failed
                            pre-visit-directory :pre-visit-directory}]
  (let [visitor (reify java.nio.file.FileVisitor
                  (postVisitDirectory [this dir exc]
                    (if post-visit-directory
                      (post-visit-directory this dir exc)
                      (java.nio.file.FileVisitResult/CONTINUE)))
                  (preVisitDirectory [this dir attrs]
                    (if pre-visit-directory
                      (pre-visit-directory this dir attrs)
                      (java.nio.file.FileVisitResult/CONTINUE)))
                  (visitFile [this file attrs]
                    (if visit-file
                      (visit-file this file attrs)
                      (java.nio.file.FileVisitResult/CONTINUE)))
                  (visitFileFailed [this file exc]
                    (if visit-file-failed
                      (visit-file-failed this file exc)
                      (java.nio.file.FileVisitResult/TERMINATE))))]
    (java.nio.file.Files/walkFileTree path visitor)))
