USE [HTR]

CREATE TABLE [dbo].[Ticket_parovi](
	F081IDT int NOT NULL PRIMARY KEY,   -- ID Ticketa
	F081IDU int NOT NULL,               -- ID Para - tablica F081 se ve�e na ovo
    F081PAR1 varchar(255) NOT NULL,         -- Isplata
	F081PAR2 varchar(255) NOT NULL,     -- Dobitak
	F081TIP decimal (5,2) NOT NULL,     -- Ulog
	F081KOEF decimal (5,2) NOT NULL,    -- Bonus
    F018DVT datetime NOT NULL,              -- Datum\Vrijeme kreiranja ticketa
    F081UID int,                            -- UserID
		FOREIGN KEY (F081UID) REFERENCES Users(F01ID),
		FOREIGN KEY (F081IDU) REFERENCES Ticket(F08IDT)
	)