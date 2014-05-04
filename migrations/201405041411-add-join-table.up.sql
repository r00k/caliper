CREATE TABLE clients_records_departments
(id serial PRIMARY KEY,
  clients_id integer REFERENCES clients,
  records_departments_id integer REFERENCES records_departments);
