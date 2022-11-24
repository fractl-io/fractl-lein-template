(ns {{ns-name}}.core
  (:require [fractl.core :as fractl]
            [{{ns-name}}.model.model])
  (:gen-class
   :name {{ns-name}}.core))

(defn -main [& args] (apply fractl/-main args))
