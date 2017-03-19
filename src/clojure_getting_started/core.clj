(ns clojure-getting-started.core
  (:require [compojure.core :refer [defroutes GET ANY]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [clojure-getting-started.views.layout :as layout]
            [clojure-getting-started.views.contents :as contents]))

(defroutes routes
  (GET "/" [] (layout/application "Home" (contents/index)))
  (route/resources "/")
  (ANY "*" [] (route/not-found (layout/application "Page Not Found" (contents/not-found)))))

(def application (handler/site routes))

(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (jetty/run-jetty application {:port port :join? false})))