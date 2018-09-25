/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.event.ListSelectionEvent;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author josips
 */
public class UplataIsplata extends javax.swing.JFrame {

    private Connection Conn;
    private int User;

    String userIme;
    String userPrezime;
    String userUsername;
    String rowClicked = null;
    int rowClickedInt = 0;

    int myTempUser = -1;
    int myTempStatus = -1;
    double myTempIznos = 0;
    double myUserIznos = 0;
    int myTempID = 0;
    int UplataIsplata = 0;

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();
    ResultSet RS = null;

    /**
     * Creates new form AdminTicketi
     */
    public UplataIsplata(Connection con, int user) {
        this.Conn = con;
        this.User = user;
        initComponents();
        GetUser(User);
        AddActionListener();
    }

    //Provjera kliknutih polja
    private void AddActionListener() {

        //Nogomet
        tbl_PrikazTickets.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                //Get row to int
                rowClicked = tbl_PrikazTickets.getValueAt(tbl_PrikazTickets.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);

            }
        });
    }

    private void GetUser(int User) {

        try {
            RS = CALIzb.main(Conn, "select F01IME, F01PRE from users where F01ID = '" + User + "'");
            if (!RS.next()) {

            } else {
                RS = CALIzb.main(Conn, "select F01IME, F01PRE, F01USR from users where F01ID = '" + User + "'");
                while (RS.next()) {
                    userIme = RS.getString("F01IME");
                    userPrezime = RS.getString("F01PRE");
                    userUsername = RS.getString("F01USR");

                }
            }
        } catch (Exception e) {
        }
    }

    public void ZabraniSlova(java.awt.event.KeyEvent evt) {
        char vChar = evt.getKeyChar();
        if ((!Character.isDigit(vChar)
                || (vChar == KeyEvent.VK_BACK_SPACE)
                || (vChar == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pic_Exit = new javax.swing.JLabel();
        pic_Profile = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        sliderTicket = new javax.swing.JSlider();
        lblIsplata = new javax.swing.JLabel();
        lblUplata = new javax.swing.JLabel();
        btn_Prikazi = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_PrikazTickets = new javax.swing.JTable();
        btn_Reset = new javax.swing.JButton();
        btn_Prihvatiti = new javax.swing.JButton();
        btn_Odbiti = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(27, 152, 224));

        pic_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/rsz_exit.png"))); // NOI18N
        pic_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pic_ExitMouseClicked(evt);
            }
        });

        pic_Profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/logoHTR.png"))); // NOI18N
        pic_Profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pic_ProfileMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Uplata \\ Isplata");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pic_Profile)
                .addGap(158, 158, 158)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pic_Exit)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pic_Exit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(pic_Profile)))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(24, 24, 24))
        );

        sliderTicket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sliderTicketMouseReleased(evt);
            }
        });

        lblIsplata.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblIsplata.setText("Isplata");

        lblUplata.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblUplata.setText("Uplata");

        btn_Prikazi.setBackground(new java.awt.Color(232, 241, 242));
        btn_Prikazi.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btn_Prikazi.setText("Prikazi");
        btn_Prikazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PrikaziActionPerformed(evt);
            }
        });

        tbl_PrikazTickets.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tbl_PrikazTickets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbl_PrikazTickets);

        btn_Reset.setBackground(new java.awt.Color(232, 241, 242));
        btn_Reset.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btn_Reset.setText("Reset");
        btn_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetActionPerformed(evt);
            }
        });

        btn_Prihvatiti.setBackground(new java.awt.Color(232, 241, 242));
        btn_Prihvatiti.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btn_Prihvatiti.setText("Prihvatiti");
        btn_Prihvatiti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PrihvatitiActionPerformed(evt);
            }
        });

        btn_Odbiti.setBackground(new java.awt.Color(232, 241, 242));
        btn_Odbiti.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btn_Odbiti.setText("Odbiti");
        btn_Odbiti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OdbitiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblIsplata)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sliderTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUplata)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                        .addComponent(btn_Reset))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addComponent(btn_Prihvatiti, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(btn_Odbiti, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(btn_Prikazi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIsplata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sliderTicket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUplata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Prikazi, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Prihvatiti)
                    .addComponent(btn_Odbiti))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pic_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pic_ExitMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_pic_ExitMouseClicked

    private void pic_ProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pic_ProfileMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pic_ProfileMouseClicked

    private void sliderTicketMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sliderTicketMouseReleased
        // TODO add your handling code here:
        if (sliderTicket.getValue() < 50) {
            sliderTicket.setValue(0);
        }
        if (sliderTicket.getValue() > 50) {
            sliderTicket.setValue(100);
        }
    }//GEN-LAST:event_sliderTicketMouseReleased

    private void btn_PrikaziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PrikaziActionPerformed
        // TODO add your handling code here:
        switch (sliderTicket.getValue()) {
            case 50:
                //Prikazi sve
                RS = CALIzb.main(Conn, "select F04UIID ID, F04IZN  Iznos, CASE WHEN F04UI = 0 THEN 'Isplata' ELSE 'Uplata' END AS 'Uplata\\Isplata', CASE WHEN F04UID = '' THEN 'Nema' ELSE (Select F01USR from Users where F01ID = F04UID ) END AS 'Korisnik', CASE WHEN F04STA = '0' THEN 'Nije obrađeno' ELSE 'Obrađeno' END AS 'Status' from  User_UI_Odobrenje");
                tbl_PrikazTickets.setModel(DbUtils.resultSetToTableModel(RS));
                break;
            case 0:
                //Prikazi isplatu za sve usere
                RS = CALIzb.main(Conn, "select F04UIID ID, F04IZN  Iznos, CASE WHEN F04UI = 0 THEN 'Isplata' ELSE 'Uplata' END AS 'Uplata\\Isplata', CASE WHEN F04UID = '' THEN 'Nema' ELSE (Select F01USR from Users where F01ID = F04UID ) END AS 'Korisnik', CASE WHEN F04STA = '0' THEN 'Nije obrađeno' ELSE 'Obrađeno' END AS 'Status' from  User_UI_Odobrenje where F04UI = '0'");
                tbl_PrikazTickets.setModel(DbUtils.resultSetToTableModel(RS));
                break;
            case 100:
                //Prikazi uplatu za sve usere
                RS = CALIzb.main(Conn, "select F04UIID ID, F04IZN  Iznos, CASE WHEN F04UI = 0 THEN 'Isplata' ELSE 'Uplata' END AS 'Uplata\\Isplata', CASE WHEN F04UID = '' THEN 'Nema' ELSE (Select F01USR from Users where F01ID = F04UID ) END AS 'Korisnik', CASE WHEN F04STA = '0' THEN 'Nije obrađeno' ELSE 'Obrađeno' END AS 'Status' from  User_UI_Odobrenje where F04UI = '1'");
                tbl_PrikazTickets.setModel(DbUtils.resultSetToTableModel(RS));
                break;
            default:
                break;
        }
    }//GEN-LAST:event_btn_PrikaziActionPerformed

    private void btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetActionPerformed
        // TODO add your handling code here:
        sliderTicket.setValue(50);
    }//GEN-LAST:event_btn_ResetActionPerformed

    //Dohvacanje zadnjeg ID-a
    private void ZadnjiID() {
        try {
            //Dohvacanje zadnjeg ID-a
            RS = CALIzb.main(Conn, "SELECT TOP 1 F04UIID FROM User_UI_Odobrenje ORDER BY F04UIID DESC");
            while (RS.next()) {
                myTempID = RS.getInt("F04UIID");
                myTempID += 1;
            }
            if (myTempID == 0) {
                myTempID += 1;
            }
        } catch (Exception e) {

        }
    }

    private void UplataIsplata() {
        try {
            RS = CALIzb.main(Conn, "select F04UI from User_UI_Odobrenje where F04UIID = '"+rowClickedInt+"'");
            while (RS.next()) {
                UplataIsplata = RS.getInt("F04UI");
            }
        } catch (Exception e) {

        }
    }

    private void DohvacanjeUsera() {
        //Dohvacanje usera
        try {
            RS = CALIzb.main(Conn, "select F04UID from User_UI_Odobrenje where F04UIID = '" + rowClickedInt + "'");
            while (RS.next()) {
                myTempUser = RS.getInt("F04UID");
            }
        } catch (Exception e) {
        }
    }

    private void btn_PrihvatitiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PrihvatitiActionPerformed

        ZadnjiID();
        DohvacanjeUsera();
        UplataIsplata();

        // Dohvacanje statusa
        try {
            RS = CALIzb.main(Conn, "Select F04STA from User_UI_Odobrenje where F04UIID = '" + rowClickedInt + "'");
            while (RS.next()) {
                myTempStatus = RS.getInt("F04STA");
            }
        } catch (Exception e) {

        }

        //Ako vec nije prihvaceno\odbijeno
        if (myTempStatus == 0) {
            //Dohvacanje iznosa uplate
            try {
                RS = CALIzb.main(Conn, "select F04IZN from User_UI_Odobrenje where F04UIID = '" + rowClickedInt + "'");
                while (RS.next()) {
                    myTempIznos = RS.getDouble("F04IZN");
                }
            } catch (Exception e) {
            }

            //Dohvacanje ukupnog uplacenog
            double myTempUkStanje = 0;
            try {
                RS = CALIzb.main(Conn, "select F03ZUP from User_Stanje where F03UID = '" + myTempUser + "'");
                while (RS.next()) {
                    myTempUkStanje = RS.getDouble("F03ZUP");
                }
            } catch (Exception e) {
            }

            //Dohvacanje trenutnog stanja usera
            try {
                RS = CALIzb.main(Conn, "select F03STA from user_stanje where F03UID = '" + myTempUser + "'");
                while (RS.next()) {
                    myUserIznos = RS.getDouble("F03STA");
                }
            } catch (Exception e) {
            }

            if (UplataIsplata == 1) {
                double myTempIz = myTempIznos + myUserIznos;
                double myTmpUkSt = myTempIznos + myTempUkStanje;
                //Uplata na racun
                RS = CALIzb.main(Conn, "update User_Stanje set F03STA = '" + myTempIz + "' where F03UID = '" + myTempUser + "'");
                //Izmjena stanja
                RS = CALIzb.main(Conn, "UPDATE User_UI_Odobrenje set F04STA = '1' where F04UIID = '" + rowClickedInt + "'");
                //Izmjena ukupne uplate
                RS = CALIzb.main(Conn, "update User_Stanje set F03ZUP = '" + myTmpUkSt + "' where F03UID = '" + myTempUser + "'");
                PopError CALError = new PopError();
                CALError.infoBox("Uplata uspješno obradena!", "Success!");
                dispose();
            } else {
                if (myUserIznos > myTempIznos) {
                    double myTempIz = myUserIznos - myTempIznos;
                    double myTmpUkSt = myTempUkStanje + myTempIznos;
                    //Uplata na racun
                    RS = CALIzb.main(Conn, "update User_Stanje set F03STA = '" + myTempIz + "' where F03UID = '" + myTempUser + "'");
                    //Izmjena stanja
                    RS = CALIzb.main(Conn, "UPDATE User_UI_Odobrenje set F04STA = '1' where F04UIID = '" + rowClickedInt + "'");
                    //Izmjena ukupne uplate
                    RS = CALIzb.main(Conn, "update User_Stanje set F03ZUP = '" + myTmpUkSt + "' where F03UID = '" + myTempUser + "'");
                    PopError CALError = new PopError();
                    CALError.infoBox("Isplata uspješno obradena!", "Success!");
                    dispose();
                } else {
                    PopError CALError = new PopError();
                    CALError.infoBox("Nemate dovoljno sredstava na racunu!", "Error!");
                }
            }
        } else {
            PopError CALError = new PopError();
            CALError.infoBox("Uplata-Isplata vec obradena!", "Error!");
        }
    }//GEN-LAST:event_btn_PrihvatitiActionPerformed

    private void btn_OdbitiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OdbitiActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_OdbitiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Odbiti;
    private javax.swing.JButton btn_Prihvatiti;
    private javax.swing.JButton btn_Prikazi;
    private javax.swing.JButton btn_Reset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblIsplata;
    private javax.swing.JLabel lblUplata;
    private javax.swing.JLabel pic_Exit;
    private javax.swing.JLabel pic_Profile;
    private javax.swing.JSlider sliderTicket;
    private javax.swing.JTable tbl_PrikazTickets;
    // End of variables declaration//GEN-END:variables
}
