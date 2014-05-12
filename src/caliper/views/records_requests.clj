(ns caliper.views.records-requests
  (:use hiccup.core
        hiccup.element
        caliper.views.layout)
  (:require [caliper.models.db :as db]
            [clj-time.core :as t]))

(defn- today-date []
  (let [today (t/today)]
    [:p (str (t/month today)
             "/"
             (t/day today)
             "/"
             (t/year today))]))

(defn- letterhead []
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
    [:p "jpzisson@gmail.com"]]])

(defn- records-department-info
  [records-department]
  (let [{:keys [department_title hospital_name address_line_1 address_line_2]}
        records-department]
    [:section.records-department-info
     [:p department_title]
     [:p hospital_name]
     [:p address_line_1]
     [:p address_line_2]]))

(defn show [client records-department]
  (html
    [:div.records-request
     (letterhead)
     (today-date)
     (records-department-info records-department)
     ]))
