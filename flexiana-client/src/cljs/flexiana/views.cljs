(ns flexiana.views
  (:require
    [flexiana.events :as events]
    [flexiana.subs :as subs]
    [re-frame.core :as rf]))


(defn main-panel
  []
  (let [remote-result (rf/subscribe [::subs/remote-result])]
    [:div
     [:div "Enter string 1"]
     [:input {:type "text"
              :value @(rf/subscribe [::subs/str1])
              :on-change #(rf/dispatch [::events/str1-change (-> % .-target .-value)])}]
     [:div "Enter string 2"]
     [:input {:type "text"
              :value @(rf/subscribe [::subs/str2])
              :on-change #(rf/dispatch [::events/str2-change (-> % .-target .-value)])}]
     [:div]
     [:input {:type "button"
              :value "Submit"
              :on-click #(rf/dispatch [::events/send-button-press])}]
     [:div]
     [:h1 @remote-result]]))
