/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.Timer;

/**
 *
 * @author josips
 */
public class LoginForm extends javax.swing.JFrame {

    private Connection Conn;

    //Spajanje na bazu
    ResultSet RS = null;
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();

    //Init potrebnih parametara
    int MyFirstLogUsername = 0;
    int MyFirstLogPassword = 0;
    int KorisnikNeaktivan = 0;

    /**
     * Creates new form LoginForm
     *
     * @param conn
     */
    public LoginForm(Connection conn) {
        this.Conn = conn;
        initComponents();

        //Brišemo title bar
        Frame loginFrame = new Frame();
        loginFrame.setUndecorated(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        AvatarLbl = new javax.swing.JLabel();
        lbl_mainUserLogin = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        TB_myUsername = new javax.swing.JTextField();
        BT_login = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        ExitLbl = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TB_myPassword = new javax.swing.JPasswordField();
        RegistrationButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        img_passVisible = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("loginFrame"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(66, 76, 82));
        jPanel1.setToolTipText("");

        jPanel3.setBackground(new java.awt.Color(0, 126, 167));

        AvatarLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/rsz_login-avatar.png"))); // NOI18N

        lbl_mainUserLogin.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lbl_mainUserLogin.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mainUserLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_mainUserLogin.setText("USER LOGIN");

        TB_myUsername.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        TB_myUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TB_myUsername.setText("username");
        TB_myUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TB_myUsernameFocusGained(evt);
            }
        });
        TB_myUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_myUsernameMouseClicked(evt);
            }
        });

        BT_login.setBackground(new java.awt.Color(0, 168, 232));
        BT_login.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        BT_login.setForeground(new java.awt.Color(255, 255, 255));
        BT_login.setText("L O G I N");
        BT_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_loginActionPerformed(evt);
            }
        });

        ExitLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/rsz_exit.png"))); // NOI18N
        ExitLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExitLblMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nemate korisnički račun?");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ukoliko ste novi korisnik mi Vas častimo sa 100kn na računu");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kod prve uplate dobivate 50% više na svoj račun");

        TB_myPassword.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        TB_myPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TB_myPassword.setText("thisismypass");
        TB_myPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TB_myPasswordFocusGained(evt);
            }
        });
        TB_myPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TB_myPasswordMouseClicked(evt);
            }
        });
        TB_myPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TB_myPasswordKeyPressed(evt);
            }
        });

        RegistrationButton.setBackground(new java.awt.Color(0, 126, 167));
        RegistrationButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        RegistrationButton.setForeground(new java.awt.Color(255, 255, 0));
        RegistrationButton.setText("REGISTRACIJA");
        RegistrationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrationButtonActionPerformed(evt);
            }
        });

        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });

        img_passVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/rsz_visible.png"))); // NOI18N
        img_passVisible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                img_passVisibleMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                img_passVisibleMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(jLabel4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addComponent(RegistrationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BT_login, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TB_myPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                            .addComponent(TB_myUsername, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(img_passVisible)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jLabel5))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jLabel6)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 138, Short.MAX_VALUE)
                                .addComponent(AvatarLbl)
                                .addGap(106, 106, 106)
                                .addComponent(ExitLbl))
                            .addComponent(lbl_mainUserLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(AvatarLbl))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ExitLbl)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_mainUserLogin)
                .addGap(13, 13, 13)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TB_myUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TB_myPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addComponent(img_passVisible))
                .addGap(18, 18, 18)
                .addComponent(BT_login, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RegistrationButton)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("HTR");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitLblMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_ExitLblMouseClicked

    private void TB_myUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_myUsernameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TB_myUsernameMouseClicked

    private void TB_myPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TB_myPasswordMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TB_myPasswordMouseClicked

    private void RegistrationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrationButtonActionPerformed
        // TODO add your handling code here:
        RegistrationForm CALRegistration = new RegistrationForm(Conn);
        CALRegistration.setLocationRelativeTo(null);
        CALRegistration.setVisible(true);
        dispose();
    }//GEN-LAST:event_RegistrationButtonActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
        TB_myPassword.setEchoChar((char) 0);
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:
        TB_myPassword.setEchoChar('*');
    }//GEN-LAST:event_jLabel1MouseReleased

    private void BT_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_loginActionPerformed
        // TODO add your handling code here:
        // Da li postoji user?
        try {
            RS = CALIzb.main(Conn, "select * from users where F01USR = '" + TB_myUsername.getText().trim() + "' and F01PWD = '" + TB_myPassword.getText().trim() + "'");

            if (RS.next()) {
                System.out.println("Uspjeh");
                // Provjera da li je user aktivan
                RS = CALIzb.main(Conn, "select F01AKT from users where F01USR = '" + TB_myUsername.getText().trim() + "' and F01PWD = '" + TB_myPassword.getText().trim() + "'");
                String isUserActive = null;
                String isUserAdmin = null;
                while (RS.next()) {
                    isUserActive = RS.getString("F01AKT");
                    if (Integer.parseInt(isUserActive.trim()) == 1) {
                        // Logiram se te idem dalje
                        System.out.println("User je aktivan!");
                        // Gledam da li je user Admin
                        RS = CALIzb.main(Conn, "select F01NIV from users where F01USR = '" + TB_myUsername.getText().trim() + "' and F01PWD = '" + TB_myPassword.getText().trim() + "'");
                        while (RS.next()) {
                            isUserAdmin = RS.getString("F01NIV");
                            if (Integer.parseInt(isUserAdmin.trim()) == 5) {
                                // User je admin
                                //Upisujemo zadnji login date-time
                                RS = CALIzb.main(Conn, "update users set F01DVL = GETDATE() where F01USR = '" + TB_myUsername.getText().trim() + "'");

                                AdminView CALAdmin = new AdminView(Conn, TB_myUsername.getText().trim());
                                CALAdmin.setLocationRelativeTo(null);
                                CALAdmin.setVisible(true);
                                dispose();

                            } else {
                                // User nije admin
                                //Upisujemo zadnji login date-time
                                RS = CALIzb.main(Conn, "update users set F01DVL = GETDATE() where F01USR = '" + TB_myUsername.getText().trim() + "'");
                                //Provjeravamo da li postoje podaci za naredni tjedan te ako ne upisujemo

                                UserView CALUser = new UserView(Conn, TB_myUsername.getText().trim());
                                CALUser.setLocationRelativeTo(null);
                                //CALUser.setExtendedState(Frame.MAXIMIZED_BOTH);
                                CALUser.setVisible(true);
                                dispose();
                            }
                        }
                    } else {
                        lbl_mainUserLogin.setText("Korisnik neaktivan!");
                        Timer timer = new Timer(2000, e -> lbl_mainUserLogin.setText("USER LOGIN"));
                        timer.setRepeats(false);
                        timer.start();
                    }
                }
            } else {
                lbl_mainUserLogin.setText("Korisnik ne postoji!");
                Timer timer = new Timer(2000, e -> lbl_mainUserLogin.setText("USER LOGIN"));
                timer.setRepeats(false);
                timer.start();

                KorisnikNeaktivan += 1;
                if (KorisnikNeaktivan == 3) {
                    BT_login.setEnabled(false);
                    timer = new Timer(10000, e -> BT_login.setEnabled(true));
                    timer.setRepeats(false);
                    timer.start();
                    KorisnikNeaktivan = 0;
                    lbl_mainUserLogin.setText("Too many login attempts!");
                    timer = new Timer(10000, e -> lbl_mainUserLogin.setText("USER LOGIN"));
                    timer.setRepeats(false);
                    timer.start();

                }
            }
        } catch (Exception e) {

        }

    }//GEN-LAST:event_BT_loginActionPerformed

    private void TB_myUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TB_myUsernameFocusGained
        // TODO add your handling code here:
        if (MyFirstLogUsername == 0) {
            TB_myUsername.setText("");
            MyFirstLogUsername++;
        }
    }//GEN-LAST:event_TB_myUsernameFocusGained

    private void TB_myPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TB_myPasswordFocusGained
        // TODO add your handling code here:
        if (MyFirstLogPassword == 0) {
            TB_myPassword.setText("");
            MyFirstLogPassword++;
        }
    }//GEN-LAST:event_TB_myPasswordFocusGained

    private void img_passVisibleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_img_passVisibleMousePressed
        // TODO add your handling code here:
        TB_myPassword.setEchoChar((char) 0);
    }//GEN-LAST:event_img_passVisibleMousePressed

    private void img_passVisibleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_img_passVisibleMouseReleased
        // TODO add your handling code here:
        TB_myPassword.setEchoChar('*');
    }//GEN-LAST:event_img_passVisibleMouseReleased

    private void TB_myPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TB_myPasswordKeyPressed
        // TODO add your handling code here:
        char vChar = evt.getKeyChar();
        if (vChar == KeyEvent.VK_ENTER) {
            BT_loginActionPerformed(null);
        }
    }//GEN-LAST:event_TB_myPasswordKeyPressed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AvatarLbl;
    private javax.swing.JButton BT_login;
    private javax.swing.JLabel ExitLbl;
    private javax.swing.JButton RegistrationButton;
    private javax.swing.JPasswordField TB_myPassword;
    private javax.swing.JTextField TB_myUsername;
    private javax.swing.JLabel img_passVisible;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbl_mainUserLogin;
    // End of variables declaration//GEN-END:variables
}
