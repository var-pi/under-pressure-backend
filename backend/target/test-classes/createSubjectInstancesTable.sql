CREATE TABLE subject_instances (
                                   id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                   user_id integer,
                                   subject_uuid varchar(255),
                                   if_followed boolean
);