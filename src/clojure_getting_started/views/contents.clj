(ns clojure-getting-started.views.contents
  (:use [hiccup.form]
        [hiccup.element :only (link-to)]))

(defn index []
  [:div {:id "content"}
   [:h1 {:class "text-success"} "Hello Hiccup"]
   [:img {:src "https://hydra-media.cursecdn.com/overwatch.gamepedia.com/9/99/Icon-Junkrat.png?version=b3ccd9300355289771f1aab255cb935f"}]])

(defn not-found []
  [:div
   [:h1 {:class "info-warning"} "Page Not Found"]
   [:p "There's no requested page. "]
   (link-to {:class "btn btn-primary"} "/" "Take me to Home")])