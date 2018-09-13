/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    //Dohvacanje datuma i vremena
    private static final DateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat currentTime = new SimpleDateFormat("HH:mm:ss");

    public void UpisivanjeNaTempTicket(int Row, int Col, int myUserID, Connection conn) {

        this.row = Row;
        this.col = Col;
        this.MyUserID = myUserID;
        this.Conn = conn;

        //Generiranje varijabli vremena
        String myCurrentStringDate;
        String myCurrentStringTime;
        String myTimDate = null;
        String myTimTime = null;

        //Generiranje varijabli
        int myRowTemp = 0;
        int myTipTemp = 0;
        int myLastID = 0;
        String myTipConv = null;
        String imeKoe = null;

        //Dohvacanje trenutnog datuma kako bi ga usporedili s parom
        Date date = new Date();
        myCurrentStringDate = (currentDate.format(date));
        myCurrentStringTime = (currentTime.format(date));

        //Dohvacanje datuma od para
        try {
            RS = CALIzb.main(Conn, "SELECT F07DTI, F07VRI FROM parovi where F07IDP = '" + row + "'");
            while (RS.next()) {
                myTimDate = RS.getString("F07DTI");
                myTimTime = RS.getString("F07VRI");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Remove ":" and "-" from 
        int myCurrentStringDateInt = Integer.parseInt(myCurrentStringDate);
        int myCurrentStringTimeInt = Integer.parseInt(myCurrentStringTime);
        int myTimDateInt = Integer.parseInt(myTimDate);
        int myTimTimeInt = Integer.parseInt(myTimTime);

        //Test printanja
        System.out.println("Trenutni datum: " + myCurrentStringDateInt);
        System.out.println("Trenutno vrijeme: " + myCurrentStringTimeInt);
        System.out.println("Datum igranja: " + myTimDateInt);
        System.out.println("Vrijeme igranja: " + myTimTimeInt);

        if (myLastID == 0) {  //Ovdje cemo provjeriti da li su datum i vrijeme para odgovarajuci, myLastID je stavljen bezveze
            //Trebat ce izbrisati sve kojima je prosao datum nakon logiranja te prije uplate
            //Dohvacanje zadnjeg ID-a
            try {
                RS = CALIzb.main(Conn, "SELECT TOP 1 F09PRK FROM temp_ticket ORDER BY F09PRK DESC");
                while (RS.next()) {
                    myLastID = RS.getInt("F09PRK");
                    myLastID += 1;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println(myLastID);
            System.out.println(myLastID);
            System.out.println(myLastID);

            if (col == 3) {
                imeKoe = "F07KO1";
                myTipConv = "1";
            } else if (col == 4) {
                imeKoe = "F07KOX";
                myTipConv = "X";
            } else if (col == 5) {
                imeKoe = "F07KO2";
                myTipConv = "2";
            }

            try {
                //Provjerajemo da li par na ticketu vec postoji
                RS = CALIzb.main(Conn, "select F09IDT from Temp_Ticket where F09IDT = '" + row + "'");
                while (RS.next()) {
                    myRowTemp = RS.getInt("F09IDT");
                }
                if (myRowTemp != 0) {
                    //Ako postoji
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
}
