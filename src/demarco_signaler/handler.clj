(ns demarco-signaler.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:use [hiccup.page :only (html5 include-css include-js)]
        [hiccup.form :only (form-to text-field submit-button)]))

(defn home
  [req]
  (html5
    [:head
     [:title "Demarco Signaler"]
    (include-js "https://code.jquery.com/jquery-3.4.1.min.js")
     (include-js "signaler.js")]
     [:body  {:style "background-color:#ffffff"}
      "colour: "
      (text-field "colour")
      [:button {:type "button" :value "demarco" :onclick( str "send();")} "demarco"]]))

(def colour (atom "#ffffff"))

(defn colour-get
  [req]
  (html5
  "#"
  ))

(defn colour-post
  [newcolour]
  (reset! colour newcolour)
  (future
    (Thread/sleep 1000)
    (if (= @colour newcolour)
      (reset! colour "#ffffff")
      nil))
  (str @colour))


(defroutes app-routes
  (GET "/" request (home request))
  (GET "/colour" request (str @colour))
  (POST "/colour" [newcolour] (colour-post newcolour))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
