(ns {{name}}.core
  (:require [fractl.core :as fractl]
            [{{name}}.model.model])
  (:gen-class
   :name {{name}}.core))

(defn -main [& args] (apply fractl/-main args))
