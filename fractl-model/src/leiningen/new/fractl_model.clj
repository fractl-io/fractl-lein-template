(ns leiningen.new.fractl-model
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as s]))

(def render (renderer "fractl-model"))

(defn fractl-model
  [app-name]
  (let [data {:name app-name
              :component-name (keyword (s/capitalize (name app-name)))
              :sanitized (name-to-path app-name)}]
    (main/info "Generating fresh 'lein new' fractl-model project.")
    (->files data
             ["README.md" (render "README.md" data)]
             ["config.edn" (render "config.edn" data)]
             ["project.clj" (render "project.clj" data)]
             ["src/{{sanitized}}/model/model.clj" (render "model.clj" data)]
             ["src/{{sanitized}}/model/{{sanitized}}/core.clj" (render "model_core.clj" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)])))
