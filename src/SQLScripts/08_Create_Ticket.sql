USE [HTR]

CREATE TABLE [dbo].[Ticket](
	F08IDT int NOT NULL PRIMARY KEY, -- ID Ticketa
        F08ISP varchar(255) NOT NULL,   -- Isplata
	F08DOB varchar(255) NOT NULL,   -- Dobitak
	F08ULO varchar(255) NOT NULL,   -- Ulog
	F08BON varchar(255) NOT NULL,   -- Bonus
	F08PDV varchar(255) NOT NULL,   -- PDV
        F08TEC varchar(255) NOT NULL,   -- Tecaj
        F08DVT datetime NOT NULL,        -- Datum\Vrijeme kreiranja ticketa
        F08UID int,                      -- UserID
        FOREIGN KEY (F08UID) REFERENCES Users(F01ID),
	)