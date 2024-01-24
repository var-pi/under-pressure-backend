CREATE TABLE subject_instances (
  id int AUTO_INCREMENT PRIMARY KEY,
  user_id varchar(255),
  subject_id integer,
  if_followed boolean
);