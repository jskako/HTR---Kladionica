USE [HTR]

CREATE TABLE [dbo].[User_Stanje](
	F03IDS int NOT NULL PRIMARY KEY, -- ID Stanja
	F03STA decimal (5,2) NOT NULL,   -- Stanje raèuna
	F03ZUP decimal (5,2) NOT NULL,   -- Zadnja uplata
	F03ZIS decimal (5,2) NOT NULL,   -- Zadnja isplata
	F03OPI varchar(255) NULL,        -- Opis
        F03UID int NOT NULL,             -- User ID
        FOREIGN KEY (F03UID) REFERENCES Users(F01ID)
	)