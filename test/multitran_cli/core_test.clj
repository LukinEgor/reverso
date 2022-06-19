(ns multitran-cli.core-test
  (:require [clojure.test :refer :all]
            [multitran-cli.core :refer :all]))

(def fixture-path "test/fixtures/content.html")

(deftest test-html-extracting
  (let [html (slurp fixture-path)
        items (extract html)]
  (testing ""
    (is (= "This will write \"hello world\" on the display." (nth (nth items 0) 0)))
    (is (= "В результате на индикаторе появится \"hello world\"." (nth (nth items 0) 1))))))
