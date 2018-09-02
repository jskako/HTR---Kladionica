USE [HTR]

CREATE TABLE [dbo].[Temp_Ticket](
	F09IDT int NOT NULL PRIMARY KEY, -- ID Ticketa
	F09PAR varchar(255) NOT NULL,    -- Odigrani par
        F09TIP varchar(1) NOT NULL,      -- Odigrani tip
	F09KOE int NOT NULL,             -- Koeficijent
	F09DOB int NOT NULL,             -- Dobitak
	F09POR int NOT NULL,             -- Porez
	F09ULO int NOT NULL,             -- Ulog
        F09DVT datetime NOT NULL,        -- Datum\Vrijeme kreiranja ticketa
        F09UID int,                      -- UserID
        FOREIGN KEY (F09UID) REFERENCES Users(F01ID),
        FOREIGN KEY (F09POR) REFERENCES Porezi(F06PID)
	)