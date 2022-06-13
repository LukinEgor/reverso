(ns multitran-cli.core-test
  (:require [clojure.test :refer :all]
            [multitran-cli.core :refer :all]))

(def fixture-path "test/fixtures/content.html")

(def html (slurp fixture-path))

(get-in [1] [0])

a
(first a)
[(first a) (second 2)]


(nth a 0)
(get a 0)
items
(deftest test-html-extracting
  (def items (extract html))
  (get-in items [0 0])
  a
  ;; (def content (slurp fixture-path))

  (nth items 0)
  (testing ""
    (is (= (get-in items [0 0]) "test")))
  )
