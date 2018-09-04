USE [HTR]

CREATE TABLE [dbo].[Parovi](
	F07IDP int NOT NULL PRIMARY KEY, -- ID Para
	F07TM1 varchar(100) NOT NULL,    -- Prvi par
	F07TM2 varchar(100) NOT NULL,    -- Drugi par
	F07KO1 decimal(5,2) NOT NULL,    -- Koeficijent na prvi par
	F07KO2 decimal(5,2) NOT NULL,    -- Koeficijent na drugi par
	F07KOX decimal(5,2) NOT NULL,    -- Koeficijent na X
	F07DTI date NOT NULL,            -- Datum igranja
	F07VRI time NOT NULL,            -- Vrijeme igranja
        F07SPO int NOT NULL,             -- Vrsta sporta 
        FOREIGN KEY (F07SPO) REFERENCES Sportovi(F02SID)
	)
