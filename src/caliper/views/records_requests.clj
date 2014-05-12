(ns caliper.views.records-requests
  (:use hiccup.core
        hiccup.element
        caliper.views.layout)
  (:require [caliper.models.db :as db]
            [clj-time.core :as t]))

(defn- today-date []
  (let [today (t/today)]
    [:p.date (str (t/month today)
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

(defn- records-department-info [records-department]
  (let [{:keys [department_title hospital_name address_line_1 address_line_2]}
        records-department]
    [:section.records-department-info
     [:p department_title]
     [:p hospital_name]
     [:p address_line_1]
     [:p address_line_2]]))

(defn- client-info [client]
  (let [{:keys [id first_name last_name date_of_birth date_of_accident]}
        client]
    [:section.client-info
     [:p (str "Re:   " first_name " " last_name)]
     [:p#date_of_birth (str "D.O.B. " date_of_birth)]
     [:p (str "Our File No. " id)]]))

(defn- prose [client]
  (list
    [:p "Dear Sir/Madam:"]

    [:p (format "This is to request a <strong>complete, unabridged, and
                certified copy of any and all records and bills</strong> in
                your possession regarding your care and treatment of %s from %s
                to the present. Enclosed is a release authorizing you to
                furnish me with the requested records. <strong>If the medical
                bills are copied by another department, please forward a copy
                on to that department as well.</strong>"
                (str (:first_name client) " " (:last_name client))
                (:date_of_accident client))]

    [:p "When providing the materials as requested, kindly forward your bill
        for duplication, and we will promptly reimburse you.

        Should you have any questions or concerns regarding compliance with
        this request, kindly contact me at your earliest convenience.
        Otherwise, I will look forward to receiving the requested records."]))

(defn- signoff []
  [:section.signoff
   [:p "Very truly yours,"]
   (image {:id "sig"} "/img/josh-sig.png")
   [:p "Joshua P. Zisson"]])

(defn- enc []
  [:section.enc
   [:p "Enc."]])

(defn show [client records-department]
  (html
    [:div.records-request
     (letterhead)

     (today-date)
     (records-department-info records-department)
     (client-info client)

     (prose client)

     (signoff)

     (enc)
     ]))
