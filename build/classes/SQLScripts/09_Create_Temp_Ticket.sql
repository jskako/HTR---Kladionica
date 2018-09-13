
USE [HTR]

CREATE TABLE [dbo].[Temp_Ticket](
    F09PRK int NOT NULL PRIMARY KEY,   -- PK Ticketa
	F09IDT int NOT NULL,               -- ID Para
	F09TIM1 varchar(100) NOT NULL,     -- Odigrani par
	F09TIM2 varchar(100) NOT NULL,     -- Odigrani par
    F09TIP varchar(1) NOT NULL,        -- Tip koji smo igrali (1,X,2)
	F09KOE decimal NOT NULL,            -- Koeficijent odabranog tipa
	F09UID int NOT NULL,               -- User ID
	F09TIS int NOT NULL,               -- Tip sporta
	F09DIG date NOT NULL,          -- Datum igranja
	F09VIG time NOT NULL,          -- Vrijeme igranja
    F09DVT datetime NOT NULL,          -- Datum\Vrijeme kreiranja ticketa
        FOREIGN KEY (F09UID) REFERENCES Users(F01ID),
        FOREIGN KEY (F09IDT) REFERENCES Parovi(F07IDP),
		FOREIGN KEY (F09TIS) REFERENCES Sportovi(F02SID)
	)