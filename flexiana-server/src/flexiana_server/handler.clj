(ns flexiana-server.handler
  (:require
    [clojure.data.json :as json]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.cors :refer [wrap-cors]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defn substract-strings
  [str1 str2]
  (let [g1 (frequencies str1)
        g2 (frequencies str2)
        res (reduce (fn [res key]
                      (let [g1cnt (get g1 key)
                            g2cnt (get g2 key)]
                        (if (= g2cnt nil)
                          (conj res key)
                          (if (< g2cnt g1cnt)
                            (conj res key)
                            res))))
                    [] (keys g1))]
    (empty? res)))


(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/check" [string1 string2]
       (let [cont? (substract-strings string1 string2)]
         (println "cont?" cont?)
         (json/write-str {:result cont?})))
  (route/not-found "Not Found"))


(def app
  (-> app-routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:post :get]
                 :access-control-allow-credentials "true"
                 :Access-Control-Allow-Headers "Content-Type, Accept, Authorization, Authentication, If-Match, If-None-Match, If-Modified-Since, If-Unmodified-Since")
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
