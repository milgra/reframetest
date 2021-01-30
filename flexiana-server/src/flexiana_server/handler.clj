(ns flexiana-server.handler
  (:require
    [clojure.data.json :as json]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.cors :refer [wrap-cors]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defn chars-in-string?
  [str1 str2]
  (if (and (> (count str1) 0)
           (> (count str2) 0))
    (let [fr1 (frequencies str1)
          fr2 (frequencies str2)
          sub (reduce (fn [prt key]
                        (let [fr1cnt (get fr1 key)
                              fr2cnt (get fr2 key)]
                          (if (nil? fr2cnt) ;; fr2 doesn't have this letter
                            (concat prt (take fr1cnt (repeat key)))
                            (if (< fr2cnt fr1cnt) ;; fr2 has less letters from this kind
                              (concat prt (take (- fr1cnt fr2cnt) (repeat key)))
                              prt)))) [] (keys fr1))]
      (empty? sub))
    false))


(defroutes app-routes
  (GET "/chars-in-string" [str1 str2] (json/write-str {:result (chars-in-string? str1 str2)}))
  (route/not-found "Not Found"))


(def app
  (-> app-routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get])
      (wrap-defaults site-defaults)))
