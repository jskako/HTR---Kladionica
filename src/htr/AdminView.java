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
import java.util.Calendar;

/**
 *
 * @author josips
 */
public class AdminView extends javax.swing.JFrame {

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();
    ResultSet RS = null;

    private static final DateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * Creates new form AdminView
     */
    private Connection Conn;
    private String User;
    int MyUserID;

    public AdminView(Connection conn, String user) {
        this.Conn = conn;
        this.User = user;
        initComponents();

        Calendar cal = Calendar.getInstance();
        mainTitle.setText(User);

        getUserID();
    }

    private void getUserID() {
        try {
            RS = CALIzb.main(Conn, "select F01ID from Users where F01USR = '" + User + "'");
            while (RS.next()) {
                MyUserID = RS.getInt("F01ID");
            }
        } catch (Exception e) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mainTitle = new javax.swing.JLabel();
        pic_Admin = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_Korisnici = new javax.swing.JButton();
        btn_UplataIsplata = new javax.swing.JButton();
        btn_Ticketi = new javax.swing.JButton();
        btn_logOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(25, 133, 161));

        mainTitle.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        mainTitle.setForeground(new java.awt.Color(255, 255, 255));
        mainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainTitle.setText("Admin console");

        pic_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/pic_admin.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pic_Admin)
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pic_Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(220, 220, 221));

        btn_Korisnici.setBackground(new java.awt.Color(197, 195, 198));
        btn_Korisnici.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btn_Korisnici.setText("Korisnici");
        btn_Korisnici.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_KorisniciActionPerformed(evt);
            }
        });

        btn_UplataIsplata.setBackground(new java.awt.Color(197, 195, 198));
        btn_UplataIsplata.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btn_UplataIsplata.setText("Uplata\\Isplata");
        btn_UplataIsplata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UplataIsplataActionPerformed(evt);
            }
        });

        btn_Ticketi.setBackground(new java.awt.Color(197, 195, 198));
        btn_Ticketi.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btn_Ticketi.setText("Ticketi");
        btn_Ticketi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TicketiActionPerformed(evt);
            }
        });

        btn_logOut.setBackground(new java.awt.Color(197, 195, 198));
        btn_logOut.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        btn_logOut.setText("LogOut");
        btn_logOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Ticketi, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_UplataIsplata, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                        .addComponent(btn_Korisnici, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btn_logOut, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_UplataIsplata, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Korisnici, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Ticketi, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_logOut, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_KorisniciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_KorisniciActionPerformed
        // TODO add your handling code here:
        AdminKorisnici CALKorisnici = new AdminKorisnici(Conn, MyUserID);
        CALKorisnici.setLocationRelativeTo(null);
        CALKorisnici.setVisible(true);
    }//GEN-LAST:event_btn_KorisniciActionPerformed

    private void btn_UplataIsplataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UplataIsplataActionPerformed
        // TODO add your handling code here:
        UplataIsplata CALUplataIsplata = new UplataIsplata(Conn, MyUserID);
        CALUplataIsplata.setLocationRelativeTo(null);
        CALUplataIsplata.setVisible(true);
    }//GEN-LAST:event_btn_UplataIsplataActionPerformed

    private void btn_TicketiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TicketiActionPerformed
        // TODO add your handling code here:
        AdminTicketi CALTicketi = new AdminTicketi(Conn, MyUserID);
        CALTicketi.setLocationRelativeTo(null);
        CALTicketi.setVisible(true);

    }//GEN-LAST:event_btn_TicketiActionPerformed

    private void btn_logOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logOutActionPerformed
        // TODO add your handling code here:
        LoginForm CALLogin = new LoginForm(Conn);
        CALLogin.setLocationRelativeTo(null);
        CALLogin.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_logOutActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Korisnici;
    private javax.swing.JButton btn_Ticketi;
    private javax.swing.JButton btn_UplataIsplata;
    private javax.swing.JButton btn_logOut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel mainTitle;
    private javax.swing.JLabel pic_Admin;
    // End of variables declaration//GEN-END:variables
}
