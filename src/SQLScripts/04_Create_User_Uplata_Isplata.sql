USE [HTR]

CREATE TABLE [dbo].[User_Uplata_Isplata](
	F04UIID int NOT NULL PRIMARY KEY, -- Uplata\Isplata ID
	F04IZN decimal (5,2) NOT NULL,    -- Iznos
	F04UI int NOT NULL,               -- 1.- Uplata, 2.- Isplata
	F04OPI varchar(255) NULL,         -- Opis
        F04DVI datetime NOT NULL,         -- Datum i vrijeme uplate/isplate
        F04KIS varchar(50) NOT NULL,     -- User isplate
        F04MIS varchar(100) NOT NULL,     -- Mjesto isplate
        F04UID int NOT NULL,              -- User ID
        F04ITI int NOT NULL,              -- ID Ticketa
        FOREIGN KEY (F04UID) REFERENCES Users(F01ID)
	)