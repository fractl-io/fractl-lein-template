(ns {{name}}.core
  (:require [fractl.core :as fractl]
            [{{name}}.model.model]))

(defn -main [& args] (apply fractl/-main args))
