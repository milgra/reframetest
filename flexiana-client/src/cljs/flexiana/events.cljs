(ns flexiana.events
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.http-fx]
    [flexiana.db :as db]
    [re-frame.core :as rf]))


(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))


(rf/reg-event-db
  ::string1-change
  (fn [db [_ new-string-value]]
    (assoc db :string1 new-string-value)))


(rf/reg-event-db
  ::string2-change
  (fn [db [_ new-string-value]]
    (assoc db :string2 new-string-value)))


(rf/reg-event-db
  ::send-button-press
  (fn [db [_]]
    (assoc db :string2 "CHANGE")))


(rf/reg-event-fx
  ::send-button-press
  (fn [{db :db} _]
    {:http-xhrio {:method          :get
                  :uri             "http://localhost:3000/check"
                  :params {:string1 (:string1 db)
                           :string2 (:string2 db)}
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::success-get-result]
                  :on-failure      [::failure-get-result]}
     :db (assoc db :loading? true)}))


(rf/reg-event-db
  ::success-get-result
  (fn [db [_ result]]
    (println "success" result)
    (assoc db :success result)))


(rf/reg-event-db
  ::failure-get-result
  (fn [db [_ result]]
    (println "failure" result)
    (assoc db :success result)))
