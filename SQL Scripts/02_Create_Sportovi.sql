USE [HTR]

CREATE TABLE [dbo].[Sportovi](
	F02SID int NOT NULL PRIMARY KEY,  -- Sport ID
	F02SPO varchar(100) NOT NULL,     -- Ime Sporta
	)

INSERT INTO Sportovi (F02SID, F02SPO)
VALUES
    ('1', 'Nogomet'),
    ('2', 'Košarka'),
    ('3', 'Tenis'),
    ('4', 'NHL');