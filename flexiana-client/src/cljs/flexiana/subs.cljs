(ns flexiana.subs
  (:require
    [re-frame.core :as rf]))


(rf/reg-sub
  ::str1
  (fn [db]
    (:str1 db)))


(rf/reg-sub
  ::str2
  (fn [db]
    (:str2 db)))


(rf/reg-sub
  ::remote-result
  (fn [db]
    (:remote-result db)))
