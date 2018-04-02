CREATE TABLE User (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL
);

CREATE TABLE Oficiu (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nume VARCHAR(100) NOT NULL UNIQUE,
  locatie VARCHAR(500) NOT NULL
);
ALTER TABLE User ADD COLUMN idOficiu INT NOT NULL
  REFERENCES Oficiu(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

CREATE TABLE Participant (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  varsta INT NOT NULL
);

ALTER TABLE Participant ADD COLUMN idOficiu INT NOT NULL
  REFERENCES Oficiu(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

CREATE TABLE Proba (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  distanta INT NOT NULL,
  stilul INT NOT NULL,
  UNIQUE (distanta, stilul)
);

CREATE TABLE ParticipantProba (
  idPart INT NOT NULL REFERENCES Participant(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  idProb INT NOT NULL REFERENCES Proba(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  PRIMARY KEY (idPart, idProb)
);