CREATE TABLE "users" ( 
  "id" SERIAL PRIMARY KEY, -- TODO Changed from varchar to int
  "given_name" varchar(255),
  "google_sub" integer
);

CREATE TABLE "subjects" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(255)
);

CREATE TABLE "subject_instances" (
  "id" SERIAL PRIMARY KEY,
  "user_id" integer, -- TODO Changed from varchar to integer
  "subject_id" integer,
  "if_followed" boolean
);

CREATE TABLE "entries" (
  "id" SERIAL PRIMARY KEY,
  "subject_instance_id" integer,
  "creation_date" date, -- TODO Changed from 'created_at' to 'creation_date'
  "stress_level" integer
);

-- ALTER TABLE "subject_instances" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

-- ALTER TABLE "subject_instances" ADD FOREIGN KEY ("subject_id") REFERENCES "subjects" ("id");

-- ALTER TABLE "entries" ADD FOREIGN KEY ("subject_instance_id") REFERENCES "subject_instances" ("id");

-- COMMENT ON COLUMN "entries"."stress_level" IS '0-100';
