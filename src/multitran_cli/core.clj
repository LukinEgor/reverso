(ns multitran-cli.core
  (:require [clojure.string :as str]
            [clj-http.client :as client]))

(def host "https://context.reverso.net/")

(def SOURCE_COLUMN_PATTERN #"<div class=\"src ltr\">\s*<span class=\"text\">\s*(.*)\s*<\/span>\s*<\/div>")
(def TARGET_COLUMN_PATTERN #"<div class=\"trg ltr\">\s*<span class=\"icon jump-right\"><\/span>\s*<span class=\"text\" lang=\'.{2}\'>\s*(.*)\s*<\/span>\s*<\/div>")
(def REMOVE_HTML_TAGS #"<\/?[^>]*>")

(def user-agents ["Mozilla/5.0"])
(def accept-header "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")

(defn remove-tags [content]
  (str/replace content REMOVE_HTML_TAGS ""))

(defn transform [content]
 (-> content
     (nth 1)
     (remove-tags)))

(defn extract [content]
  (let [source-column (re-seq SOURCE_COLUMN_PATTERN content)
        target-column (re-seq TARGET_COLUMN_PATTERN content)]
    (map
     vector
     (map #(transform %) source-column)
     (map #(transform %) target-column))))

(defn format-phraze [phraze]
  (str/replace phraze #"\s+" "+"))

(defn compose-url
  ([phraze] (compose-url phraze "english" "russian"))
  ([phraze source-lang target-lang]
   (str host "translation/" source-lang "-" target-lang "/" (format-phraze phraze))))

(defn fetch [phraze]
  (-> phraze
      (compose-url)
      (client/get
       {:headers
        {"User-Agent" (rand-nth user-agents),
         "Accept" accept-header}})))

(defn fetch-and-extract
  [phraze]
  (-> phraze
      (fetch)
      (:body)
      (extract)))
