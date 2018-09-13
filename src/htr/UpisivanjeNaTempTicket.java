/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author josips
 */
public class UpisivanjeNaTempTicket {

    private int row;
    private int col;
    private int MyUserID;
    private Connection Conn;

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();
    ResultSet RS = null;

    public void UpisivanjeNaTempTicket(int Row, int Col, int myUserID, Connection conn) {

        this.row = Row;
        this.col = Col;
        this.MyUserID = myUserID;
        this.Conn = conn;

        //Row nam je ID para
        int myRowTemp = 0;
        int myTipTemp = 0;
        int myLastID = 0;
        String myTipConv = null;
        String imeKoe = null;

        if (col == 3) {
            imeKoe = "F07KO1";
            myTipConv = "1";
        } else if (col == 4) {
            imeKoe = "F07KO2";
            myTipConv = "2";
        } else if (col == 5) {
            imeKoe = "F07KOX";
            myTipConv = "X";
        }

        //Col nam je ID kolone (1,2,x) <-> (3,4,5)
        System.out.println(myRowTemp);
        System.out.println(myRowTemp);

        //Dohvacanje zadnjeg ID-a
        try {
            RS = CALIzb.main(Conn, "DECLARE @lastNumb int; set @lastNumb = (SELECT TOP 1 F09PRK FROM temp_ticket ORDER BY F09PRK DESC); select F09PRK from temp_ticket where F09PRK = @lastNumb");
            while (RS.next()) {
                myLastID = RS.getInt("F01ID");
                myLastID += 1;
            }
            if (myLastID == 0) {
                myLastID += 1;
            }
        } catch (Exception ex) {

        }

        //Ukoliko postoji replace ga ukoliko ima razlicitu Col ili brisemo ukoliko ima istu
        //Ukoliko ne postoji dodajemo ga
        //Upisujemo koeficijente i preglede
        //Refreshamo pregled listica i dobitka
        //ZNANI PROBLEMI - Forma za slanje mail-a se suzi, mail se ne salje, napraviti placanje, napravidi admin formu, oznaciti upisane parove u tablici, 
        //napraviti uplatu i isplatu, napraviti pregled mojih racuna, napraviti bonuse
        try {
            //Provjerajemo da li par na ticketu vec postoji
            RS = CALIzb.main(Conn, "select F09IDT from Temp_Ticket where F09IDT = '" + row + "'");
            while (RS.next()) {
                myRowTemp = RS.getInt("F09IDT");
            }
            if (myRowTemp != 0) {
                //Ako postoji
                System.out.println("Usao u IF");
                //Ako postoji s istim tipom
                if (myTipTemp == col) {
                    RS = CALIzb.main(Conn, "delete from Temp_Ticket where F09IDT = '" + row + "'");
                } //Ako je tip razlicit
                else {
                    RS = CALIzb.main(Conn, "delete from Temp_Ticket where F09IDT = '" + row + "'");
                    RS = CALIzb.main(Conn, "insert into Temp_Ticket values('" + myLastID + "','" + row + "',(Select F07TM1 from parovi where F07IDP = '" + row + "'),(Select F07TM2 from parovi where F07IDP = '" + row + "'),'" + myTipConv + "',(SELECT " + imeKoe + " FROM parovi where F07IDP = '" + row + "'),'" + MyUserID + "',(select F07SPO from parovi where F07IDP = '" + row + "'),(select F07DTI from parovi where F07IDP = '" + row + "'),(select F07VRI from parovi where F07IDP = '" + row + "'),getdate());");
                }

            } else {
                //Ako ne postoji
                System.out.println("Usao u ELSE");
                //Tip 1
                if (col == 3 || col == 4 || col == 5) {
                    RS = CALIzb.main(Conn, "insert into Temp_Ticket values('" + myLastID + "','" + row + "',(Select F07TM1 from parovi where F07IDP = '" + row + "'),(Select F07TM2 from parovi where F07IDP = '" + row + "'),'" + myTipConv + "',(SELECT " + imeKoe + " FROM parovi where F07IDP = '" + row + "'),'" + MyUserID + "',(select F07SPO from parovi where F07IDP = '" + row + "'),(select F07DTI from parovi where F07IDP = '" + row + "'),(select F07VRI from parovi where F07IDP = '" + row + "'),getdate());");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
