(ns caliper.views.records-requests
  (:use hiccup.core
        hiccup.element
        caliper.views.layout)
  (:require [caliper.models.db :as db]))

(defn show [client records-department]
  (html
    [:div.records-request
     [:section.letterhead
      [:h2 "Joshua P. Zisson"]
      [:h3 "Attorney at law"]
      [:br]
      [:section.contact-info
       [:p "101 Tremont Street"]
       [:p "Suite 1111"]
       [:p "Boston, MA 02108"]
       [:p "TEL (617) 444-9626"]
       [:p "FAX (617) 315-8982"]
       [:p "jpzisson@gmail.com"]]]]))
