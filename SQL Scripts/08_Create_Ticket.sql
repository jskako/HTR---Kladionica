USE [HTR]

CREATE TABLE [dbo].[Ticket](
	F08IDT int NOT NULL PRIMARY KEY, -- ID Ticketa
	F08PAR varchar(255) NOT NULL,    -- Odigrani par
        F08TIP varchar(1),               -- Odigrani tip
	F08KOE int NOT NULL,             -- Koeficijent
	F08DOB int NOT NULL,             -- Dobitak
	F08POR int NOT NULL,             -- Porez
	F08ULO int NOT NULL,             -- Ulog
        F08DVT datetime NOT NULL,        -- Datum\Vrijeme kreiranja ticketa
        F08UID int,                      -- UserID
        FOREIGN KEY (F08UID) REFERENCES Users(F01ID),
        FOREIGN KEY (F08POR) REFERENCES Porezi(F06PID)
	)