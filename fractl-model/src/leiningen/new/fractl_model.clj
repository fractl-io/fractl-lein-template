(ns leiningen.new.fractl-model
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as s]
            [camel-snake-kebab.core :as csk]))

(def render (renderer "fractl-model"))

(defn- parse-name [n]
  (s/split n #":"))

(def ^:private app-name first)
(def ^:private app-version second)

(defn- capsfirst [s]
  (str (s/upper-case (first s)) (apply str (rest s))))

(defn- normalize-component-name [n]
  (keyword (capsfirst (csk/->camelCase (name n)))))

(defn fractl-model
  [app-info]
  (let [xs (parse-name app-info)
        n (app-name xs)
        data {:name n
              :version (or (app-version xs) "0.0.1")
              :component-name (normalize-component-name n)
              :sanitized (name-to-path n)}]
    (main/info "Generating fresh 'lein new' fractl-model project.")
    (->files data
             ["README.md" (render "README.md" data)]
             ["config.edn" (render "config.edn" data)]
             ["project.clj" (render "project.clj" data)]
             ["src/{{sanitized}}/model/model.clj" (render "model.clj" data)]
             ["src/{{sanitized}}/model/{{sanitized}}/core.clj" (render "model_core.clj" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)])))
