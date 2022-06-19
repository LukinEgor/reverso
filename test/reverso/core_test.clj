(ns reverso.core-test
  (:require [clojure.test :refer :all]
            [reverso.core :refer :all]))

(def fixture-path "test/fixtures/content.html")

(deftest test-html-extracting
  (let [html (slurp fixture-path)
        items (extract html)]
  (testing ""
    (is (= "This will write \"hello world\" on the display." (:source (nth items 0))))
    (is (= "В результате на индикаторе появится \"hello world\"." (:target (nth items 0)))))))
