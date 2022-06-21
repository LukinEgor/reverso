(defproject reverso "0.0.2"
  :description "Tool for searching in Reverso Context"
  :url "https://github.com/LukinEgor/reverso"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.12.3"]
                 [cheshire "5.11.0"]
                 [org.clojure/tools.cli "1.0.206"]]
  :plugins [[lein-binplus "0.6.6"]]
  :main reverso.cli)
