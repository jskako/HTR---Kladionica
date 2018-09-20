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
public class AdminView extends javax.swing.JFrame {

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();
    ResultSet RS = null;
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
        pic_Admin = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_Exit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_Korisnici = new javax.swing.JButton();
        btn_Uplata = new javax.swing.JButton();
        btn_Isplata = new javax.swing.JButton();
        btn_Ticketi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(239, 195, 230));

        pic_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/pic_admin.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Admin console");

        lbl_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/rsz_exit.png"))); // NOI18N
        lbl_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_ExitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pic_Admin)
                .addGap(122, 122, 122)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_Exit)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Exit)
                            .addComponent(pic_Admin)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(240, 230, 239));

        btn_Korisnici.setBackground(new java.awt.Color(240, 166, 202));
        btn_Korisnici.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btn_Korisnici.setText("Korisnici");
        btn_Korisnici.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_KorisniciActionPerformed(evt);
            }
        });

        btn_Uplata.setBackground(new java.awt.Color(240, 166, 202));
        btn_Uplata.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btn_Uplata.setText("Uplata");
        btn_Uplata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UplataActionPerformed(evt);
            }
        });

        btn_Isplata.setBackground(new java.awt.Color(240, 166, 202));
        btn_Isplata.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btn_Isplata.setText("Isplata");
        btn_Isplata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_IsplataActionPerformed(evt);
            }
        });

        btn_Ticketi.setBackground(new java.awt.Color(240, 166, 202));
        btn_Ticketi.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btn_Ticketi.setText("Ticketi");
        btn_Ticketi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TicketiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Korisnici, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Uplata, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Isplata, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Ticketi, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Korisnici, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Uplata, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Isplata, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Ticketi, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ExitMouseClicked
        // TODO add your handling code here:
        LoginForm CALLogin = new LoginForm(Conn);
        CALLogin.setLocationRelativeTo(null);
        CALLogin.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbl_ExitMouseClicked

    private void btn_KorisniciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_KorisniciActionPerformed
        // TODO add your handling code here:
        AdminKorisnici CALKorisnici = new AdminKorisnici(Conn, MyUserID);
        CALKorisnici.setLocationRelativeTo(null);
        CALKorisnici.setVisible(true);
    }//GEN-LAST:event_btn_KorisniciActionPerformed

    private void btn_UplataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UplataActionPerformed
        // TODO add your handling code here:
        AdminUplata CALUplata = new AdminUplata(Conn, MyUserID);
        CALUplata.setLocationRelativeTo(null);
        CALUplata.setVisible(true);
    }//GEN-LAST:event_btn_UplataActionPerformed

    private void btn_IsplataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_IsplataActionPerformed
        // TODO add your handling code here:
        AdminIsplata CALUsplata = new AdminIsplata(Conn, MyUserID);
        CALUsplata.setLocationRelativeTo(null);
        CALUsplata.setVisible(true);
    }//GEN-LAST:event_btn_IsplataActionPerformed

    private void btn_TicketiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TicketiActionPerformed
        // TODO add your handling code here:
        AdminTicketi CALTicketi = new AdminTicketi(Conn, MyUserID);
        CALTicketi.setLocationRelativeTo(null);
        CALTicketi.setVisible(true);
    }//GEN-LAST:event_btn_TicketiActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Isplata;
    private javax.swing.JButton btn_Korisnici;
    private javax.swing.JButton btn_Ticketi;
    private javax.swing.JButton btn_Uplata;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_Exit;
    private javax.swing.JLabel pic_Admin;
    // End of variables declaration//GEN-END:variables
}
