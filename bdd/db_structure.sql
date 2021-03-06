BEGIN TRANSACTION;
DROP TABLE IF EXISTS "Task";
CREATE TABLE IF NOT EXISTS "Task" (
	"id"	INTEGER NOT NULL,
	"projectId"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"creationTimestamp"	INTEGER NOT NULL,
	FOREIGN KEY("projectId") REFERENCES "Project"("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "Project";
CREATE TABLE IF NOT EXISTS "Project" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"color"	INTEGER NOT NULL,
	PRIMARY KEY("id")
);
COMMIT;
