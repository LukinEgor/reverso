(ns multitran-cli.core)

(def url "https://context.reverso.net/translation/english-russian/hello+world")

(def SOURCE_COLUMN_PATTERN #"<div class=\"src ltr\">\s*<span class=\"text\">\s*(.*)\s*<\/span>\s*<\/div>")
(def TARGET_COLUMN_PATTERN #"<div class=\"trg ltr\">\s*<span class=\"icon jump-right\"><\/span>\s*<span class=\"text\" lang=\'.{2}\'>\s*(.*)\s*<\/span>\s*<\/div>")

(defn extract [content]
  (let [source-column (re-seq SOURCE_COLUMN_PATTERN content)
        target-column (re-seq TARGET_COLUMN_PATTERN content)]
    (map
     vector
     (map #(nth % 1) source-column)
     (map #(nth % 1) target-column))))
