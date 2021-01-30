(ns flexiana.views
  (:require
    [flexiana.events :as events]
    [flexiana.subs :as subs]
    [re-frame.core :as rf]))


(defn main-panel
  []
  (let [name (rf/subscribe [::subs/name])
        success (rf/subscribe [::subs/success])]
    [:div
     [:h1 "Hello from " @name]
     [:div "Enter string 1"]
     [:input {:type "text"
              :value @(rf/subscribe [::subs/string1])
              :on-change #(rf/dispatch [::events/string1-change (-> % .-target .-value)])}]
     [:div "Enter string 2"]
     [:input {:type "text"
              :value @(rf/subscribe [::subs/string2])
              :on-change #(rf/dispatch [::events/string2-change (-> % .-target .-value)])}]
     [:div]
     [:input {:type "button"
              :value "Check"
              :on-click #(rf/dispatch [::events/send-button-press])}]
     [:div]
     [:h1 "Success : " @success]]))
