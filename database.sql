CREATE TABLE "users" (
  "id" varchar(255) PRIMARY KEY
);

CREATE TABLE "subjects" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(255)
);

CREATE TABLE "subject_instances" (
  "id" SERIAL PRIMARY KEY,
  "user_id" varchar(255),
  "subject_id" integer
);

CREATE TABLE "entries" (
  "id" SERIAL PRIMARY KEY,
  "subject_instance_id" integer,
  "created_at" date,
  "stress_level" integer
);

-- ALTER TABLE "subject_instances" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

-- ALTER TABLE "subject_instances" ADD FOREIGN KEY ("subject_id") REFERENCES "subjects" ("id");

-- ALTER TABLE "entries" ADD FOREIGN KEY ("subject_instance_id") REFERENCES "subject_instances" ("id");

-- COMMENT ON COLUMN "entries"."stress_level" IS '0-100';
