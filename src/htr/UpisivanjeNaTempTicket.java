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
    boolean parIstekao = false;
    String myCurrentTip = null;

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

        //Generiranje varijabli
        int myRowTemp = 0;

        //Varijable za provjeru
        int myTipTemp = 0;

        int myLastID = 0;

        String myTipConv = null;
        String imeKoe = null;
        double myKoe = 0;

        try {
            switch (col) {
                case 3:
                    imeKoe = "F07KO1";
                    myTipConv = "1";
                    break;
                case 4:
                    imeKoe = "F07KOX";
                    myTipConv = "X";
                    break;
                case 5:
                    imeKoe = "F07KO2";
                    myTipConv = "2";
                    break;
                default:
                    return;
            }
        } catch (Exception el) {
            el.printStackTrace();
        }

        //Ukoliko postoji dohvati njegov tip
        try {
            RS = CALIzb.main(Conn, "select F09TIP F09TIP from Temp_Ticket where F09IDT = '" + row + "'");
            while (RS.next()) {
                myCurrentTip = RS.getString("F09TIP");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if ("1".equals(myCurrentTip)) {
            myTipTemp = 3;
        } else if ("X".equals(myCurrentTip)) {
            myTipTemp = 4;
        } else if ("2".equals(myCurrentTip)) {
            myTipTemp = 5;
        }

        provjeriVrijeme();

        //Da li je koeficijent 0
        try {
            RS = CALIzb.main(Conn, "Select " + imeKoe + " FROM parovi where F07IDP = '" + row + "'");
            while (RS.next()) {
                myKoe = RS.getDouble(imeKoe);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (myKoe != 0) {
            if (parIstekao == true) {
                System.out.println("Usao sam u par istekao");
                try {
                    RS = CALIzb.main(Conn, "SELECT TOP 1 F09PRK FROM temp_ticket ORDER BY F09PRK DESC");
                    while (RS.next()) {
                        myLastID = RS.getInt("F09PRK");
                        myLastID += 1;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    //Provjerajemo da li par na ticketu vec postoji
                    RS = CALIzb.main(Conn, "select F09IDT from Temp_Ticket where F09IDT = '" + row + "'");
                    while (RS.next()) {
                        myRowTemp = RS.getInt("F09IDT");
                    }

                    if (myRowTemp != 0) {
                        //Ako postoji
                        //Provjerajemo trenutni tip

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
            } //Vrijeme ili datum su istekli
            else {

            }
        } //Koeficijent je 0
        else {

        }
    }

    private void provjeriVrijeme() {
        System.out.println("Usao sam u provjeru");
        //Generiranje varijabli vremena
        String myCurrentStringDate;
        String myCurrentStringTime;
        String myTimDate = null;
        String myTimTime = null;

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
        myCurrentStringTime = myCurrentStringTime.replace(".", "");
        myTimTime = myTimTime.replace(".", "");

        //myCurrentStringTime = myCurrentStringTime.substring(0, myCurrentStringTime.length()-7);
        myTimTime = myTimTime.substring(0, myTimTime.length() - 7);

        int myCurrentStringDateInt = Integer.parseInt(myCurrentStringDate.replace("-", ""));
        int myCurrentStringTimeInt = Integer.parseInt(myCurrentStringTime.replace(":", ""));
        int myTimDateInt = Integer.parseInt(myTimDate.replace("-", ""));
        int myTimTimeInt = Integer.parseInt(myTimTime.replace(":", ""));

        //Test printanja
        System.out.println("Trenutni datum: " + myCurrentStringDateInt);
        System.out.println("Trenutno vrijeme: " + myCurrentStringTimeInt);
        System.out.println("Datum igranja: " + myTimDateInt);
        System.out.println("Vrijeme igranja: " + myTimTimeInt);

        if (myTimDateInt > myCurrentStringDateInt) {
            System.out.println("Vrijeme je true1");
            parIstekao = true;
        } else if ((myTimDateInt == myCurrentStringDateInt) && (myTimTimeInt > myCurrentStringTimeInt)) {
            System.out.println("Vrijeme je true2");
            parIstekao = true;
        } else {
            System.out.println("Vrijeme je false");
            parIstekao = false;
        }

    }

}
