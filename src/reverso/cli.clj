(ns reverso.cli
  (:require [reverso.core :refer [search]]
            [clojure.string :as str]
            [cheshire.core :refer [generate-string]]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

;; (def available-langs ["enlish" "russian"])

(def cli-options
  [["-s" "--source LANG" "Original language"
    :id :origin
    :default "english"]
   ["-t" "--target LANG" "Target language"
    :id :target
    :default "russian"]
   ["-f" "--format Type" "format"
    :id :format
    :default "json"]
   ["--text" "--text TEXT" "text"
    :id :text]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["Tool for search on reverso context site."
        ""
        "Usage: reverso [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  search    Start a new server"
        ""
        "Please refer to the manual page for more information."]
       (str/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn validate-args [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options)
      {:exit-message (usage summary) :ok? true}
      errors
      {:exit-message (error-msg errors)}
      (and (= 1 (count arguments))
           (#{"search"} (first arguments)))
      {:action (first arguments) :options options}
      :else
      {:exit-message (usage summary)})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn pretty-print [items format]
  (case format
    (= format "json") (println (generate-string items {:pretty true}))))

(defn -main [& args]
  (let [{:keys [action options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (let [{:keys [target origin text format]} options]
        (case action
          "search"  ((pretty-print (search text origin target) format)))))))
