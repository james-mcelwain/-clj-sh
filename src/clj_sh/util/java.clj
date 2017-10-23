(ns clj-sh.util.java)

(defn to-alist [lseq]
  (reduce #(do (.add %1 %2) %1) (java.util.ArrayList.) lseq))
