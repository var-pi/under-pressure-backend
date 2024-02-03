CREATE TABLE subject_instances (
  id int AUTO_INCREMENT PRIMARY KEY,
  user_id integer,
  subject_id integer,
  if_followed boolean
);