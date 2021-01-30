(ns flexiana.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  ::name
  (fn [db]
    (:name db)))


(rf/reg-sub
  ::string1
  (fn [db]
    (:string1 db)))


(rf/reg-sub
  ::string2
  (fn [db]
    (:string2 db)))


(rf/reg-sub
  ::success
  (fn [db]
    (:success_http_result db)))
