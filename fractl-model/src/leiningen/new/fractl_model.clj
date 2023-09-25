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
(def ^:private fractl-version #(nth % 2))

(defn- capsfirst [s]
  (str (s/upper-case (first s)) (apply str (rest s))))

(defn- normalize-component-name [n]
  (keyword (capsfirst (csk/->camelCase (name n)))))

(defn fractl-model
  [app-info]
  (let [xs (parse-name app-info)
        n (app-name xs)
        data {:name n
              :ns-name (s/replace n "-" "_")
              :version (or (app-version xs) "0.0.1")
              :fractl-version (fractl-version xs)
              :component-name (normalize-component-name n)
              :sanitized (name-to-path n)
              :model-ns (s/replace n "-" ".")
              :model-path (s/replace n #"[\.\-_]" java.io.File/separator)}]
    (main/info "Generating fresh 'lein new' fractl-model project.")
    (->files data
             ["README.md" (render "README.md" data)]
             ["config.edn" (render "config.edn" data)]
             ["project.clj" (render "project.clj" data)]
             ["src/{{sanitized}}/model/model.cljc" (render "model.cljc" data)]
             ["src/{{sanitized}}/model/{{model-path}}/core.cljc" (render "model_core.cljc" data)]
             ["src/{{sanitized}}/core.cljc" (render "core.cljc" data)])))
