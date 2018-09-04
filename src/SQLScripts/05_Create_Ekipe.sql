USE [HTR]

CREATE TABLE [dbo].[Ekipe](
	F05EID int NOT NULL PRIMARY KEY,  -- ID Ekipe
	F05IME varchar(100) NOT NULL,     -- Ime ekipe
	F05TIP int NOT NULL,              -- Tip ekupe (Nogomet, košarka,...)
        F05KOE int NOT NULL,              -- Koeficijent jakosti
        FOREIGN KEY (F05TIP) REFERENCES Sportovi(F02SID)
	);

	GO
INSERT INTO Ekipe (F05EID, F05IME, F05TIP, F05KOE)
VALUES
    --1. Nogomet
    ('1', 'Dinamo','1','1'),
    ('2', 'Rijeka','1','2'),
    ('3', 'Osijek','1','3'),
    ('4', 'Lokomotiva','1','4'),
    ('5', 'Gorica','1','5'),
    ('6', 'Inter Zaprešiæ','1','4'),
    ('7', 'Slaven','1','4'),
    ('8', 'Hajduk','1','1'),
    ('9', 'Istra','1','5'),
    ('10', 'Rudeš','1','5'),


    --2. Košarka
    ('11', 'Cibona','2','1'),
    ('12', 'Split','2','2'),
    ('13', 'Zadar','2','3'),
    ('14', 'Cedevita','2','4'),
    ('15', 'GKK Šibenik','2','5'),
    ('16', 'Osijek','2','4'),
    ('17', 'Zabok','2','4'),
    ('18', 'Alkar','2','1'),
    ('19', 'Škrljevo','2','5'),
    ('20', 'Gorica','2','5'),

    --3. Tenis
    ('21', 'Rafael Nadal','3','1'),
    ('22', 'Federer Roger','3','2'),
    ('23', 'Djokovic Novak','3','3'),
    ('24', 'Èiliæ Marin','3','1'),
    ('25', 'Isner John','3','5'),
    ('26', 'Anderson Kevin','3','4'),
    ('27', 'Fognini Fabio','3','4'),
    ('28', 'Coric Borna','3','1'),
    ('29', 'Nishikori Kei','3','5'),
    ('30', 'Sock Jack','3','5'),

    --4. NHL
    ('31', 'Brest','4','1'),
    ('32', 'Lida','4','2'),
    ('33', 'Proprad','4','3'),
    ('34', 'Nitra','4','4'),
    ('35', 'Pelicans','4','5'),
    ('36', 'Avto','4','4'),
    ('37', 'Yugra','4','4'),
    ('38', 'Chelny','4','1'),
    ('39', 'Nove  Zamky','4','5'),
    ('40', 'SKA-1946','4','5');