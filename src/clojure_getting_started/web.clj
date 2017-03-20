(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET ANY]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [clojure-getting-started.views.layout :as layout]
            [clojure-getting-started.views.contents :as contents]
            ))

(def symbollist
     (concat 
       (map #(symbol (str "enemy" %)) (range 1 7))
       (map #(symbol (str "teammate" %)) (range 1 6))
     )
)

(def keylist
          (concat 
            (map #(keyword (str "enemy" %)) (range 1 7)) 
            (map #(keyword (str "teammate" %)) (range 1 6))
          )
)

(def myparams
  {  
   (zipmap symbollist keylist)
   :params
  }
)


(defroutes routes
  (GET "/" [] (layout/application "Select Team Members" (contents/index)))
  (GET "/results"  {
                    {enemy1 :enemy1 enemy2 :enemy2 enemy3 :enemy3 enemy4 :enemy4
                     enemy5 :enemy5 enemy6 :enemy6 teammate1 :teammate1 teammate2
                     :teammate2 teammate3 :teammate3 teammate4 :teammate4 teammate5
                     :teammate5 } :params} 
       (layout/application "Results" (contents/builder [enemy1 enemy2 enemy3 enemy4 
                     enemy5 enemy6 teammate1 teammate2 teammate3 teammate4 teammate5])))
  (route/resources "/")
  (ANY "*" [] (route/not-found (layout/application "Page Not Found" (contents/not-found)))))

(def application (handler/site routes))


(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (jetty/run-jetty application {:port port :join? false})))