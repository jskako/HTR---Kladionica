/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author josips
 */
public class UserView extends javax.swing.JFrame {

    /**
     * Creates new form UserView
     */
    private Connection Conn;
    private String User;
    //private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();

    //Global Var
    int MyUserID;
    double MyUserBalance;
    ResultSet RS = null;
    String sd = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();

    public UserView(Connection con, String user) {
        this.Conn = con;
        this.User = user;

        initComponents();

        //OVO JE TESTNI DIO KODA KOJI CE BITI ACTION LISTENER
        tableNogomet.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                System.out.println(tableNogomet.getValueAt(tableNogomet.getSelectedRow(), 0).toString());
                System.out.println(tableNogomet.getSelectedColumn());
            }
        });

        try {
            pickDate.setDate(date);

            //Punjenje odabranim datumom    
            sd = dateFormat.format(pickDate.getDate());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Punjenje tablica podacima
        PunjenjeTablicePodacima(sd);

        //Dohvacanje vrijednosti
        getUserID();
        getUserBalance();

        lbl_UserName.setText(User);
        lbl_UserName.setHorizontalAlignment(lbl_UserName.CENTER);
        lbl_Balance.setText("| " + MyUserBalance + "kn");
        lbl_Balance.setHorizontalAlignment(lbl_UserName.CENTER);
        //Brišemo title bar
        Frame UserView = new Frame();
        UserView.setUndecorated(true);
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

    private void getUserBalance() {
        try {
            RS = CALIzb.main(Conn, "Select F03STA from User_Stanje where F03UID = '" + MyUserID + "'");
            while (RS.next()) {
                MyUserBalance = RS.getDouble("F03STA");
            }
        } catch (Exception e) {

        }
    }

    private void PunjenjeTablicePodacima(String Time) {
        try {
            //Nogomet
            RS = CALIzb.main(Conn, "select F07IDP ID, F07TM1 Domacin, F07TM2 Protivnik, F07KO1 '1', F07KOX X, F07KO2 '2', REPLACE( F07VRI, RIGHT(F07VRI, 11), '' ) Vrijeme from parovi where F07SPO = '1' and F07DTI = '" + Time + "' ORDER BY F07VRI ASC");
            tableNogomet.setModel(DbUtils.resultSetToTableModel(RS));

            //Kosarka
            RS = CALIzb.main(Conn, "select F07IDP ID, F07TM1 Domacin, F07TM2 Protivnik, F07KO1 '1', F07KOX X, F07KO2 '2', REPLACE( F07VRI, RIGHT(F07VRI, 11), '' ) Vrijeme from parovi where F07SPO = '2' and F07DTI = '" + Time + "' ORDER BY F07VRI ASC");
            tableKosarka.setModel(DbUtils.resultSetToTableModel(RS));

            //Tenis
            RS = CALIzb.main(Conn, "select F07IDP ID, F07TM1 Domacin, F07TM2 Protivnik, F07KO1 '1', F07KOX X, F07KO2 '2', REPLACE( F07VRI, RIGHT(F07VRI, 11), '' ) Vrijeme from parovi where F07SPO = '4' and F07DTI = '" + Time + "' ORDER BY F07VRI ASC");
            tableHokej.setModel(DbUtils.resultSetToTableModel(RS));

            //Hokej
            RS = CALIzb.main(Conn, "select F07IDP ID, F07TM1 Domacin, F07TM2 Protivnik, F07KO1 '1', F07KOX X, F07KO2 '2', REPLACE( F07VRI, RIGHT(F07VRI, 11), '' ) Vrijeme from parovi where F07SPO = '3' and F07DTI = '" + Time + "' ORDER BY F07VRI ASC");
            tableTenis.setModel(DbUtils.resultSetToTableModel(RS));

        } catch (Exception ex) {
            ex.printStackTrace();
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
        img_Logo = new javax.swing.JLabel();
        lbl_UserName = new javax.swing.JLabel();
        lbl_Balance = new javax.swing.JLabel();
        pickDate = new com.toedter.calendar.JDateChooser();
        btnPotvrdi = new javax.swing.JButton();
        MySoccerPanel = new javax.swing.JPanel();
        img_Soccer = new javax.swing.JLabel();
        lbl_Nogomet1 = new javax.swing.JLabel();
        MyBasketPanel = new javax.swing.JPanel();
        img_basketball = new javax.swing.JLabel();
        lbl_Kosarka = new javax.swing.JLabel();
        MyHockeyPanel = new javax.swing.JPanel();
        imgHockey = new javax.swing.JLabel();
        lbl_Hokej = new javax.swing.JLabel();
        MyTennisPanel = new javax.swing.JPanel();
        imgTennis = new javax.swing.JLabel();
        lbl_Tenis = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        lbl_MojTicket = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNogomet = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableKosarka = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableHokej = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableTenis = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(247, 255, 247));
        setName("UserView"); // NOI18N
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(26, 83, 92));

        img_Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/logoHTR.png"))); // NOI18N

        lbl_UserName.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lbl_UserName.setForeground(new java.awt.Color(255, 255, 255));
        lbl_UserName.setText("Username");

        lbl_Balance.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lbl_Balance.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Balance.setText("Balance");

        pickDate.setDateFormatString("yyyy-MM-dd");
        pickDate.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        pickDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                pickDateFocusLost(evt);
            }
        });
        pickDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                pickDatePropertyChange(evt);
            }
        });
        pickDate.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                pickDateVetoableChange(evt);
            }
        });

        btnPotvrdi.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        btnPotvrdi.setText("Potvrdi");
        btnPotvrdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotvrdiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(759, Short.MAX_VALUE)
                .addComponent(lbl_UserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_Balance)
                .addGap(621, 621, 621)
                .addComponent(img_Logo)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pickDate, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPotvrdi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_UserName)
                        .addComponent(lbl_Balance))
                    .addComponent(img_Logo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPotvrdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pickDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        MySoccerPanel.setBackground(new java.awt.Color(42, 157, 143));

        img_Soccer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/football-ball.png"))); // NOI18N

        lbl_Nogomet1.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Nogomet1.setText("Nogomet");

        javax.swing.GroupLayout MySoccerPanelLayout = new javax.swing.GroupLayout(MySoccerPanel);
        MySoccerPanel.setLayout(MySoccerPanelLayout);
        MySoccerPanelLayout.setHorizontalGroup(
            MySoccerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MySoccerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(img_Soccer)
                .addGap(30, 30, 30)
                .addComponent(lbl_Nogomet1)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        MySoccerPanelLayout.setVerticalGroup(
            MySoccerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MySoccerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(img_Soccer)
                .addContainerGap())
            .addGroup(MySoccerPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbl_Nogomet1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MyBasketPanel.setBackground(new java.awt.Color(233, 196, 106));

        img_basketball.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/basketball.png"))); // NOI18N

        lbl_Kosarka.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Kosarka.setText("Košarka");

        javax.swing.GroupLayout MyBasketPanelLayout = new javax.swing.GroupLayout(MyBasketPanel);
        MyBasketPanel.setLayout(MyBasketPanelLayout);
        MyBasketPanelLayout.setHorizontalGroup(
            MyBasketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyBasketPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(img_basketball)
                .addGap(41, 41, 41)
                .addComponent(lbl_Kosarka)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        MyBasketPanelLayout.setVerticalGroup(
            MyBasketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyBasketPanelLayout.createSequentialGroup()
                .addGroup(MyBasketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MyBasketPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(img_basketball))
                    .addGroup(MyBasketPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lbl_Kosarka)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MyHockeyPanel.setBackground(new java.awt.Color(244, 162, 97));

        imgHockey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/hockey-stick.png"))); // NOI18N

        lbl_Hokej.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Hokej.setText("Hokej");

        javax.swing.GroupLayout MyHockeyPanelLayout = new javax.swing.GroupLayout(MyHockeyPanel);
        MyHockeyPanel.setLayout(MyHockeyPanelLayout);
        MyHockeyPanelLayout.setHorizontalGroup(
            MyHockeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyHockeyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgHockey)
                .addGap(43, 43, 43)
                .addComponent(lbl_Hokej)
                .addContainerGap(154, Short.MAX_VALUE))
        );
        MyHockeyPanelLayout.setVerticalGroup(
            MyHockeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MyHockeyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgHockey)
                .addContainerGap())
            .addGroup(MyHockeyPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbl_Hokej)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MyTennisPanel.setBackground(new java.awt.Color(231, 111, 81));

        imgTennis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/tennis-racket.png"))); // NOI18N

        lbl_Tenis.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Tenis.setText("Tenis");

        javax.swing.GroupLayout MyTennisPanelLayout = new javax.swing.GroupLayout(MyTennisPanel);
        MyTennisPanel.setLayout(MyTennisPanelLayout);
        MyTennisPanelLayout.setHorizontalGroup(
            MyTennisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyTennisPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgTennis)
                .addGap(51, 51, 51)
                .addComponent(lbl_Tenis)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MyTennisPanelLayout.setVerticalGroup(
            MyTennisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MyTennisPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgTennis)
                .addContainerGap())
            .addGroup(MyTennisPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbl_Tenis)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel2.setBackground(new java.awt.Color(26, 83, 92));

        lbl_MojTicket.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lbl_MojTicket.setForeground(new java.awt.Color(255, 255, 255));
        lbl_MojTicket.setText("Moj Ticket");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(lbl_MojTicket)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_MojTicket)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableNogomet.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        tableNogomet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Domaćin", "Protivnik", "Vrijeme", "1", "X", "2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableNogomet.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableNogomet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNogometMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableNogomet);

        tableKosarka.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        tableKosarka.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Domaćin", "Protivnik", "Vrijeme", "1", "X", "2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableKosarka.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(tableKosarka);

        tableHokej.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        tableHokej.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Domaćin", "Protivnik", "Vrijeme", "1", "X", "2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableHokej.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(tableHokej);

        tableTenis.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        tableTenis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Domaćin", "Protivnik", "Vrijeme", "1", "X", "2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableTenis.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane4.setViewportView(tableTenis);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MySoccerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MyBasketPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MyHockeyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(MyTennisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MySoccerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MyBasketPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MyHockeyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MyTennisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pickDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pickDatePropertyChange

    }//GEN-LAST:event_pickDatePropertyChange

    private void pickDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pickDateFocusLost
        // TODO add your handling code here:
        tableNogomet.setModel(new DefaultTableModel());
        System.out.println(sd);
        System.out.println(sd);
        System.out.println(sd);
        System.out.println(sd);
        sd = dateFormat.format(pickDate.getDate());
        PunjenjeTablicePodacima(sd);
    }//GEN-LAST:event_pickDateFocusLost

    private void btnPotvrdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotvrdiActionPerformed
        // TODO add your handling code here:
        tableNogomet.setModel(new DefaultTableModel());
        System.out.println(sd);
        System.out.println(sd);
        System.out.println(sd);
        System.out.println(sd);
        sd = dateFormat.format(pickDate.getDate());
        PunjenjeTablicePodacima(sd);
    }//GEN-LAST:event_btnPotvrdiActionPerformed

    private void pickDateVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_pickDateVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_pickDateVetoableChange

    private void tableNogometMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNogometMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableNogometMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MyBasketPanel;
    private javax.swing.JPanel MyHockeyPanel;
    private javax.swing.JPanel MySoccerPanel;
    private javax.swing.JPanel MyTennisPanel;
    private javax.swing.JButton btnPotvrdi;
    private javax.swing.JLabel imgHockey;
    private javax.swing.JLabel imgTennis;
    private javax.swing.JLabel img_Logo;
    private javax.swing.JLabel img_Soccer;
    private javax.swing.JLabel img_basketball;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_Balance;
    private javax.swing.JLabel lbl_Hokej;
    private javax.swing.JLabel lbl_Kosarka;
    private javax.swing.JLabel lbl_MojTicket;
    private javax.swing.JLabel lbl_Nogomet1;
    private javax.swing.JLabel lbl_Tenis;
    private javax.swing.JLabel lbl_UserName;
    private com.toedter.calendar.JDateChooser pickDate;
    private javax.swing.JTable tableHokej;
    private javax.swing.JTable tableKosarka;
    private javax.swing.JTable tableNogomet;
    private javax.swing.JTable tableTenis;
    // End of variables declaration//GEN-END:variables
}
