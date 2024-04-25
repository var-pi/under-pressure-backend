CREATE TABLE "users" (
                         "id" SERIAL PRIMARY KEY,
                         "google_sub" varchar(255),
                         "given_name" varchar(255)
);

CREATE TABLE "subjects" (
                            "uuid" varchar(255) PRIMARY KEY,
                            "name" varchar(255)
);

CREATE TABLE "subject_instances" (
                                     "id" SERIAL PRIMARY KEY,
                                     "user_id" integer,
                                     "subject_uuid" varchar(255),
                                     "if_followed" boolean
);

CREATE TABLE "entries" (
                           "id" SERIAL PRIMARY KEY,
                           "subject_instance_id" integer,
                           "creation_date" date,
                           "stress_level" integer
);

