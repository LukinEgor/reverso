(ns reverso.core
  (:require [clojure.string :as str]
            [clj-http.client :as client]))

(def host "https://context.reverso.net/")

(def SOURCE_COLUMN_PATTERN #"<div class=\"src ltr\"[^>]*>\s*<span class=\"text\"[^>]*>\s*(.*)\s*<\/span>\s*<\/div>")
(def TARGET_COLUMN_PATTERN #"<div class=\"trg ltr\"[^>]*>\s*<span class=\"icon jump-right\"[^>]*><\/span>\s*<span class=\"text\"[^>]*>\s*(.*)\s*<\/span>\s*<\/div>")
(def REMOVE_HTML_TAGS #"<\/?[^>]*>")

(def user-agents ["Mozilla/5.0"])
(def accept-header "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")

(defn remove-tags [content]
  (str/replace content REMOVE_HTML_TAGS ""))

(defn transform [content]
 (-> content
     (nth 1)
     (remove-tags)))

;; TODO refactoring
(defn extract [content]
  (let [source-column (re-seq SOURCE_COLUMN_PATTERN content)
        target-column (re-seq TARGET_COLUMN_PATTERN content)]
    (->> (map
          vector
          (map (fn [item] { :source (transform item) }) source-column)
          (map (fn [item] { :target (transform item) }) target-column))
         (map (fn [[t s]] (merge t s))))))

(defn format-text [text]
  (str/replace text #"\s+" "+"))

(defn compose-url [text source target]
  (str host "translation/" source "-" target "/" (format-text text)))

(defn fetch [text source target]
  (-> (compose-url text source target)
      (client/get
       {:headers
        {"User-Agent" (rand-nth user-agents),
         "Accept" accept-header}})))

(defn search [text source target]
  (-> (fetch text source target)
      (:body)
      (extract)))
