/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.event.ListSelectionEvent;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author josips
 */
public class AdminKorisnici extends javax.swing.JFrame {

    private Connection Conn;
    private int User;

    String rowClicked = null;
    int rowClickedInt = 0;
    int trenutnaAktivnost = 0;
    String dohvaceniUser = null;

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();
    ResultSet RS = null;

    /**
     * Creates new form AdminKorisnici
     */
    public AdminKorisnici(Connection con, int user) {
        this.Conn = con;
        this.User = user;
        initComponents();
        AddActionListener();
        PrvoPunjenje();
    }

    private void PrvoPunjenje() {
        RS = CALIzb.main(Conn, " select F01ID ID, F01USR Usrname, F01IME Ime, F01PRE Prezime, F01EMA Mail, CASE WHEN F01AKT = '1' THEN 'Aktivan' ELSE 'Neaktivan' END AS 'Status' from users");
        tUsers.setModel(DbUtils.resultSetToTableModel(RS));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        btnExit = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        mySearchText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        bTrazi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tUsers = new javax.swing.JTable();
        bDeaktivirajAktiviraj = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(27, 152, 224));

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/rsz_exit.png"))); // NOI18N
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/logoHTR.png"))); // NOI18N
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Korisnici");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(249, 249, 249)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(btnExit)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        mySearchText.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        mySearchText.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setText("User");

        bTrazi.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bTrazi.setText("Trazi");
        bTrazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTraziActionPerformed(evt);
            }
        });

        tUsers.setBackground(new java.awt.Color(204, 204, 204));
        tUsers.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tUsers);

        bDeaktivirajAktiviraj.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bDeaktivirajAktiviraj.setText("Aktiviraj / Deaktiviraj");
        bDeaktivirajAktiviraj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeaktivirajAktivirajActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bDeaktivirajAktiviraj)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mySearchText, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bTrazi)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(mySearchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bTrazi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bDeaktivirajAktiviraj)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void bTraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTraziActionPerformed
        RS = CALIzb.main(Conn, " select F01ID ID, F01USR Usrname, F01IME Ime, F01PRE Prezime, F01EMA Mail, CASE WHEN F01AKT = '1' THEN 'Aktivan' ELSE 'Neaktivan' END AS 'Status' from users where F01USR like '%" + mySearchText.getText().trim() + "%'");
        tUsers.setModel(DbUtils.resultSetToTableModel(RS));
    }//GEN-LAST:event_bTraziActionPerformed

    private void bDeaktivirajAktivirajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeaktivirajAktivirajActionPerformed
        // TODO add your handling code here:
        //Dohvacanje aktivnosti
        try {
            RS = CALIzb.main(Conn, "select F01AKT from users where F01ID = '" + rowClickedInt + "'");
            while (RS.next()) {
                trenutnaAktivnost = RS.getInt("F01AKT");
            }
        } catch (Exception e) {

        }
        DohvacanjeUsera();
        //Dohvacanje Usera
        if (trenutnaAktivnost == 0) {
            RS = CALIzb.main(Conn, "update users set F01AKT = '1' where F01ID = '" + rowClickedInt + "'");
            PopError CALError = new PopError();
            CALError.infoBox("Korisnik " + dohvaceniUser + " aktiviran!", "Success!");
            dispose();
        } else {
            RS = CALIzb.main(Conn, "update users set F01AKT = '0' where F01ID = '" + rowClickedInt + "'");
            PopError CALError = new PopError();
            CALError.infoBox("Korisnik " + dohvaceniUser + " deaktiviran!", "Success!");
            dispose();
        }

    }//GEN-LAST:event_bDeaktivirajAktivirajActionPerformed

    private void DohvacanjeUsera() {
        try {
            RS = CALIzb.main(Conn, "select F01USR from users where F01ID = '" + rowClickedInt + "'");
            while (RS.next()) {
                dohvaceniUser = RS.getString("F01USR");
            }
        } catch (Exception e) {

        }
    }

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitMouseClicked

    //Provjera kliknutih polja
    private void AddActionListener() {

        UpisivanjeNaTempTicket CALRegistration = new UpisivanjeNaTempTicket();

        //Nogomet
        tUsers.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                //Get row to int
                rowClicked = tUsers.getValueAt(tUsers.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDeaktivirajAktiviraj;
    private javax.swing.JButton bTrazi;
    private javax.swing.JLabel btnExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mySearchText;
    private javax.swing.JTable tUsers;
    // End of variables declaration//GEN-END:variables
}
