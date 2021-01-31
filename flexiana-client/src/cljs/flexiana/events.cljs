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
  ::str1-change
  (fn [db [_ new-str-value]]
    (assoc db :str1 new-str-value)))


(rf/reg-event-db
  ::str2-change
  (fn [db [_ new-str-value]]
    (assoc db :str2 new-str-value)))


(rf/reg-event-fx
  ::send-button-press
  (fn [{db :db} _]
    {:http-xhrio {:method          :get
                  :uri             "http://localhost:3000/chars-in-string"
                  :params {:str1 (:str1 db)
                           :str2 (:str2 db)}
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::remote-result-success]
                  :on-failure      [::remote-result-failure]}
     :db (assoc db :loading? true)}))


(rf/reg-event-db
  ::remote-result-success
  (fn [db [_ {:keys [result]}]]
    (assoc db :remote-result (str result))))


(rf/reg-event-db
  ::remote-result-failure
  (fn [db [_ response]]
    (assoc db :remote-result "server error")))
