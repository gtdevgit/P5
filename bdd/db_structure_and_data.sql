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
INSERT INTO "Task" ("id","projectId","name","creationTimestamp") VALUES (1,1,'tache 1',1611845180892),
 (2,1,'tache 2',1611845180892),
 (3,1,'tache 3',1611845180892),
 (4,1,'tache 4',1611845180892),
 (5,1,'tache 5',1611845180892),
 (6,1,'tache 6',1611845180892);
INSERT INTO "Project" ("id","name","color") VALUES (1,'Projet Tartampion',-1385775),
 (2,'Projet Lucidia',-4928070),
 (3,'Projet Circus',-6041902);
COMMIT;
