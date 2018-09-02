USE [HTR]

CREATE TABLE [dbo].[Porezi](
	F06PID int NOT NULL PRIMARY KEY,  -- ID Poreza
	F06IME varchar(50) NOT NULL,     -- Ime poreza
	F06POR int NOT NULL,              -- Iznos poreza
	)

INSERT INTO Porezi (F06PID, F06IME, F06POR)
VALUES
    ('1', 'PDV','25'),
    ('2', 'PP','5'),
    ('3', 'FREE','1');