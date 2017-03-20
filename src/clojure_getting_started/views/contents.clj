(ns clojure-getting-started.views.contents
  (:use [hiccup.form]
        [hiccup.element :only (link-to)]
        [clojure.string :only [lower-case join replace capitalize]]
  )
)

(def heroes ["Genji" "McCree" "Pharah" "Reaper" "Soldier:76" "Sombra" "Tracer" 
             "Bastion" "Hanzo" "Junkrat" "Mei" "Torbjorn" "Widowmaker"
             "D.Va" "Reinhardt" "Roadhog" "Winston" "Zarya"
             "Ana" "Lucio" "Mercy" "Symmetra" "Zenyatta"
             ])

(def countermap {
 :ana {
	:counters ["bastion" "lucio" "mercy" "pharah" "torbjorn" "zenyatta"] 
	:countered-by nil}
 :bastion {
	:counters ["reinhardt" "winston" "zarya"] 
	:countered-by ["ana" "d.va" "genji" "hanzo" "junkrat" "roadhog" "soldier:76" "sombra" "tracer" "widowmaker"]}
 :d.va {
	:counters ["bastion" "hanzo" "mercy" "widowmaker"] 
	:countered-by ["mei" "pharah" "reaper" "zarya"]}
 :genji {
	:counters ["bastion" "hanzo" "mercy" "widowmaker"] 
	:countered-by ["mei" "winston" "zarya"]}
 :hanzo {
	:counters ["bastion" "torbjorn"]
 	:countered-by ["d.va" "genji" "tracer" "widowmaker" "winston"]}
 :junkrat {
	:counters ["bastion" "reinhardt" "torbjorn"] 
	:countered-by ["pharah" "widowmaker" "zarya"]}
 :lucio {
	:counters nil 
	:countered-by ["ana" "mccree" "mei" "roadhog"]}
 :mccree {
	:counters ["lucio" "pharah" "tracer"] 
	:countered-by nil}
 :mei {
	:counters ["d.va" "genji" "lucio" "reinhardt" "roadhog"]
 	:countered-by ["pharah" "zarya"]}
 :mercy {
	:counters nil 
	:countered-by ["ana" "d.va" "genji" "roadhog"]}
 :pharah {
	:counters ["d.va" "junkrat" "mei" "reaper" "reinhardt" "symmetra" "zarya"] 
	:countered-by ["ana" "mccree" "soldier:76" "widowmaker"]}
 :reaper {
	:counters ["d.va" "reinhardt" "roadhog" "winston" "zarya" "zenyatta"] 
	:countered-by ["pharah"]}
 :reinhardt {
	:counters nil 
	:countered-by ["bastion" "pharah" "reaper" "sombra"]}
 :roadhog {
	:counters ["bastion" "lucio" "mercy" "tracer" "winston"]
 	:countered-by ["mei" "reaper"]}
 :soldier:76 {
	:counters ["bastion" "pharah" "torbjorn"] 
	:countered-by nil}
 :sombra {
	:counters ["bastion" "reinhardt" "tracer"] 
	:countered-by nil}
 :symmetra {
	:counters nil 
	:countered-by ["pharah" "winston"]}
 :torbjorn {
	:counters ["tracer"] 
	:countered-by ["ana" "hanzo" "junkrat" "soldier:76" "widowmaker" "zarya"]}
 :tracer {
	:counters ["bastion" "zenyatta"] 
	:countered-by ["mccree" "roadhog" "sombra" "torbjorn"]}
 :widowmaker {
	:counters ["bastion" "hanzo" "junkrat" "pharah" "torbjorn"] 
	:countered-by ["d.va" "genji" "winston"]}
 :winston {
	:counters ["genji" "hanzo" "symmetra" "widowmaker" "zenyatta"] 
	:countered-by ["bastion" "reaper" "roadhog"]}
 :zarya {
	:counters ["d.va" "genji" "junkrat" "mei" "torbjorn" "zenyatta"] 
	:countered-by ["bastion" "pharah" "reaper"]}
 :zenyatta {
	:counters nil 
	:countered-by ["ana" "reaper" "tracer" "zarya"]}
 })

(defn herosugg [teams]
  (name (key (apply max-key val 
  (apply merge-with + (into []
        (for [enemy (take 6 teams)] 
         (apply merge-with + (into [] (concat 
           (list (zipmap heroes (repeat 0)))
           (list (hash-map :hanzo -100))
	         (for [enemycounters (get (get countermap (keyword enemy)) :counters)] 
	           {(keyword enemycounters) -1}
	         )
	         (for [enemycounteredby (get (get countermap (keyword enemy)) :countered-by)] 
             {(keyword enemycounteredby) 1}
           ) 
           (for [teammate (drop 6 teams)]
	           {(keyword teammate) -100}
           )
))))))))))
  

(defn index []
  [:body {:style "background-color:#D3D3D3;"}
	  [:div {:id "content" :style "background-color:#D3D3D3;"}
	   [:img {:src "imgs/overwatch.png" :style "background-color:#D3D3D3;"}]
	   [:h1 {:style "text-align:center;" } "Hero Suggestor"]
	 [:form {:action "/results"}
	    [:h1 "Enemy Team"]
	  (for [i (range 1 7 )]
	   (drop-down  {:style "width:180px;height:25px;"}(join "" ["enemy" i]) heroes)
	  )
	    [:h1 "Your Team"]
	    (for [i (range 1 6 )]
	     (drop-down {:style "width:180px;height:25px;"}(join "" ["teammate" i]) heroes)
	    )
	    (submit-button {:style "width:180px;height:25px;"} "Who should I pick?")
	 ]
  ]
])

(defn builder [input]
	(def sugg (capitalize (herosugg (map lower-case input))))
  [:body {:style "background-color:#D3D3D3;"}
	  [:div {:id "content" }
	   [:h1 "Enemy Team"]
	   (for [i (take 6 input)]
	      [:img {:src (str "imgs/" (replace i #":" "") ".png" )}]
	    )
	   [:h1 "Your Team"]
	   (for [i (drop 6 input)]
	     [:img {:src (str "imgs/" (replace i #":" "") ".png" )}]
	   )
     [:div {:style "text-align:center; margin-bottom: 100px;" }
		   [:h1 (str "Suggested Hero: " sugg)]
		   [:img {:src (str "imgs/" (replace sugg #":" "") ".png" )}]
	     [:form {:action "/"}
	      (submit-button {:class "btn"} "Select new heroes")
	     ]
	  ]
   ]
  ]
 )

(defn not-found []
  [:div
   [:h1 {:class "info-warning"} "Page Not Found"]
   [:p "There's no requested page. "]
   (link-to {:class "btn btn-primary"} "/" "Take me to Home")])