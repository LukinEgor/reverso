(ns reverso.core-test
  (:require [clojure.test :refer :all]
            [reverso.core :refer :all]))

(def en-ru-fixture-path "test/fixtures/en-ru-content.html")
(def ru-en-fixture-path "test/fixtures/ru-en-content.html")

(deftest test-extracting-from-en-ru-html
  (let [html (slurp en-ru-fixture-path)
        items (extract html)]
  (testing ""
    (is (= "This will write \"hello world\" on the display." (:source (nth items 0))))
    (is (= "В результате на индикаторе появится \"hello world\"." (:target (nth items 0)))))))

(deftest test-extracting-from-ru-en-html
  (let [html (slurp ru-en-fixture-path)
        items (extract html)]
  (testing ""
    (is (= "Привет, мир, вот песня, которую мы поем" (:source (nth items 0))))
    (is (= "Hello, world, here's a song that we're singing" (:target (nth items 0)))))))
