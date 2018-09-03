USE [HTR]

CREATE TABLE [dbo].[Users](
	F01ID int NOT NULL PRIMARY KEY,  -- ID
	F01USR varchar(20) NOT NULL,    -- Username
	F01IME varchar(50) NOT NULL,    -- Ime 
	F01PRE varchar(50) NOT NULL,    -- Prezime 
	F01PWD varchar(12) NOT NULL,    -- Šifra
	F01EMA varchar(100) NOT NULL,    -- Mail
	F01DMR date NOT NULL,            -- Datum roðenja
	F01GRA varchar(50) NOT NULL,    -- Grad roðenja
	F01DRZ varchar(50) NOT NULL,    -- Država 
	F01ADR varchar(100) NOT NULL,    -- Adresa
	F01PBR int NOT NULL,             -- Poštanski broj
	F01BRT int NOT NULL,             -- Broj telefona
	F01NIV int NOT NULL,             -- Nivo rada
	F01DVK datetime,                 -- Datum i vrijeme kreiranja
	F01DVL datetime,                  -- Datum i vrijeme zadnjeg logiranja
	F01AKT int NOT NULL,             -- Aktivnost
	F01MIN int NOT NULL             -- Dozvoljen minus
	)

INSERT INTO Users (	
	F01ID,
	F01USR,
	F01IME,
	F01PRE,
	F01PWD,
	F01EMA,
	F01DMR,
	F01GRA,
	F01DRZ,
	F01ADR,
	F01PBR,     
	F01BRT, 
	F01NIV, 
	F01DVK,                 
	F01DVL,             
	F01AKT,             
	F01MIN
	)            
VALUES (
	'1', 
	'admin', 
	'admin',
	'admin',
	'999',
	'admin@admin.hr',
	 CONVERT (date, GETDATE()) ,
	'Split',
	'Hrvatska',
	'NoAdressAvailable',
	'21000',
	'000000000',
	'5',
	CONVERT (datetime, GETDATE()),
	CONVERT (datetime, GETDATE()),
	'1',
	'0'
);