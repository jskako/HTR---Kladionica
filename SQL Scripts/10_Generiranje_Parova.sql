USE [HTR]

DECLARE @myDate date
DECLARE @isEmpty date
set @myDate = GETDATE()
set @isEmpty = ( SELECT TOP 1 F07DTI FROM parovi where F07DTI = @myDate ORDER BY F07DTI DESC)
SELECT LEFT(CONVERT(VARCHAR, @isEmpty, 120), 10)

if @isEmpty is null
begin

DECLARE @TopRow int
set @TopRow = (SELECT TOP 1 F07IDP FROM parovi ORDER BY F07IDP DESC)
if @TopRow is null
set @TopRow = 1
set @TopRow = @TopRow+1
Declare @TopRowTop int
set @TopRowTop = @TopRow + 100
Declare @RandomTeamOneInt int
Declare @RandomTeamTwoInt int
Declare @RandomTeamOne varchar(200)
Declare @RandomTeamTwo varchar(200)
DECLARE @startTime Time = '00:00:00'
DECLARE @endTime TIME = '23:59:59'
Declare @Time time
Declare @KoefOne decimal (5,2)
Declare @KoefTwo decimal (5,2)
Declare @KoefTie decimal (5,2)
Declare @MyTip int
Declare @DateStart	Date =  CONVERT(date,GETDATE())
		,@DateEnd	Date = (SELECT DATEADD(day, 7, CONVERT(date,GETDATE())) AS DateAdd)


While @TopRow <= @TopRowTop
Begin 
   DoesPairExist: 
   Set @RandomTeamOneInt = (SELECT TOP 1 F05EID FROM Ekipe ORDER BY NEWID())
   Set @RandomTeamOne = (SELECT F05IME  FROM Ekipe WHERE F05EID = @RandomTeamOneInt)
   Set @MyTip = (SELECT F05TIP From Ekipe WHERE F05EID = @RandomTeamOneInt)
   IsTeamSame:
   Set @RandomTeamTwoInt = (SELECT TOP 1 F05EID FROM Ekipe WHERE F05TIP = @MyTip ORDER BY NEWID())
   Set @RandomTeamTwo = (SELECT F05IME  FROM Ekipe WHERE F05EID = @RandomTeamTwoInt)
   if(@RandomTeamOne = @RandomTeamTwo)
   GOTO IsTeamSame
   Declare @Counter int 
   Set @Counter = 1
   WHILE(@Counter < @TopRow)
   BEGIN
   IF(@RandomTeamOne = (Select F07TM1 from Parovi where F07IDP = @Counter) AND @RandomTeamTwo = (Select F07TM2 from Parovi where F07IDP = @Counter))
   GOTO DoesPairExist
   Set @Counter = @Counter + 1
   END
   
   SET @KOEFONE = ((Select F05KOE from EKIPE where F05EID = @RandomTeamOneInt))
   SET @KOEFTWO = ((Select F05KOE from EKIPE where F05EID = @RandomTeamTwoInt))

   if(@KOEFONE = @KOEFTWO)
   SET @KOEFONE = @KOEFONE+1
   if(@KOEFONE < @KOEFTWO)
   SET @KOEFONE = @KOEFONE/2
   if(@KOEFTWO < @KOEFONE)
   SET @KOEFTWO = @KOEFTWO/2
   if(@KOEFTWO < 1)
   SET @KOEFTWO = @KOEFTWO+1
   if(@KOEFONE < 1)
   SET @KOEFONE = @KOEFONE+1

   SET @KoefTie = ((@KOEFTWO+@KOEFONE)/2)
   if(@MyTip = 3)
   SET @KoefTie = 0


   DECLARE @maxSeconds int = DATEDIFF(ss, @startTime, @endTime)
   DECLARE @randomSeconds int = (@maxSeconds + 1) * RAND(convert(varbinary, newId() )) 

   SET @Time = (SELECT (convert(Time, DateAdd(second, @randomSeconds, @startTime)))  AS RandomTime)
   Insert Into Parovi (F07IDP, F07TM1, F07TM2, F07KO1, F07KO2, F07KOX, F07DTI, F07VRI, F07SPO)
   values (@TopRow,@RandomTeamOne,@RandomTeamTwo,@KOEFONE,@KOEFTWO,@KoefTie, (Select DateAdd(Day, Rand() * DateDiff(Day, @DateStart, @DateEnd), @DateStart)),@Time, @MyTip)

   Set @TopRow = @TopRow + 1
End

end

--delete from parovi

--select * from parovi