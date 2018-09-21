/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Toolkit;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
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

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();

    //Global Var
    int MyUserID;
    double MyUserBalance;
    ResultSet RS = null;
    String sd = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DecimalFormat decFormat = new DecimalFormat(".##");
    Date date = new Date();
    String rowClicked;
    String tempClicked;
    String userName;
    int tempClickedInt;
    int rowClickedInt;
    int colClickedInt;
    double koeficijent;
    double iznosUplate;
    double pdvnaIznos;
    double ukupniIznosSPdv;
    double ukupanDobitak;
    double porezNaUkupanIznos;
    double iznosIsplate;
    Frame UserView = new Frame();

    public UserView(Connection con, String user) {
        this.Conn = con;
        this.User = user;
        this.setAlwaysOnTop(true);

        initComponents();
        PocetnePostavke();

    }

    private void PocetnePostavke() {
        //Brisanje starih parova iz temp tablice
        BrisanjeStarihParova();

        //Postavljanje ROW-HEIGHT tablice
        tableNogomet.setRowHeight(22);
        tableKosarka.setRowHeight(22);
        tableHokej.setRowHeight(22);
        tableTenis.setRowHeight(22);
        tableTemp.setRowHeight(22);

        //Sakrij Prikaz Poreza na listicu
        lblPrikazPorezaJedan.setVisible(false);
        lblPrikazPorezaDva.setVisible(false);
        lblPrikazPorezaTri.setVisible(false);
        lblPrikazPorezaCetri.setVisible(false);
        lblPrikazPorezaPet.setVisible(false);
        lblPrikazPorezaSest.setVisible(false);

        //Postavi Full-screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);

        //Postavljanje tablica
        //ACTION LISTENER
        AddActionListener();

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
        GetUserName();

        lblWB.setText("Welcome back, " + userName);
        lblWB.setHorizontalAlignment(lblWB.CENTER);
        lbl_Balance.setText("Balance: " + MyUserBalance + "kn");
        lbl_UserID.setText("( " + User + ", ID: " + Integer.toString(MyUserID) + ")");
        lbl_UserID.setHorizontalAlignment(lbl_UserID.CENTER);

        //Brišemo title bar     
        UserView.setUndecorated(true);
    }

    private void GetUserName() {
        try {
            RS = CALIzb.main(Conn, "select F01IME from Users where F01ID = '" + MyUserID + "'");
            while (RS.next()) {
                userName = RS.getString("F01IME");
            }
        } catch (Exception e) {
        }
    }

    private void BrisanjeStarihParova() {
        //Brisanje starih parova iz temp tablice
        RS = CALIzb.main(Conn, "Declare @currdate date;Declare @currtime time; Set @currdate = getdate(); Set @currtime = getdate(); delete from Temp_Ticket where F09DIG < @currdate OR (F09DIG = @currdate AND F09VIG < @currtime);");
    }

    public void PostavljanjeTablica() {
        PunjenjeTempTablice();
        PostavljanjeBrojaParova();
        PostavljanjeKoeficijenta();
        PostavljanjePDVnaIznos();
        PostavljanjeUkupnogDobitka();
        IzracunPorezaNaUkupno();
        PostavljanjeIznosaIsplate();
    }

    private void PostavljanjeIznosaIsplate() {
        iznosIsplate = ukupanDobitak - porezNaUkupanIznos;
        lblIsplata.setText(String.valueOf(decFormat.format(iznosIsplate)));
    }

    private void PostavljanjePDVnaIznos() {
        iznosUplate = Double.parseDouble(txtIznosUplate.getText().trim());
        pdvnaIznos = (double) (iznosUplate * (5.0f / 100.0f));
        ukupniIznosSPdv = iznosUplate - pdvnaIznos;
        lblPorezNaIznUplate.setText("-" + decFormat.format(pdvnaIznos) + " kn (5% mt) = " + decFormat.format(ukupniIznosSPdv) + " kn");
    }

    private void IzracunPorezaNaUkupno() {
        if (ukupanDobitak < 10000) {
            porezNaUkupanIznos = (double) (ukupanDobitak * (10.0f / 100.0f));
            lblPorez.setText(String.valueOf(decFormat.format(porezNaUkupanIznos)));
        } else {
            double tempPDV;
            double tempIznos = ukupanDobitak;
            tempPDV = (double) (10000 * (10.0f / 100.0f));
            tempIznos -= 10000;
            tempPDV = tempPDV + ((tempIznos * (15.0f / 100.0f)));
            porezNaUkupanIznos = tempPDV;
            lblPorez.setText(String.valueOf(decFormat.format(porezNaUkupanIznos)));
        }
    }

    private void PostavljanjeUkupnogDobitka() {
        ukupanDobitak = ukupniIznosSPdv * koeficijent;
        lblDobitak.setText(String.valueOf(decFormat.format(ukupanDobitak)));
    }

    private void PostavljanjeBrojaParova() {
        int brojParova;
        try {
            RS = CALIzb.main(Conn, "SELECT COUNT(F09PRK ) F09PRK FROM Temp_Ticket WHERE F09UID='" + MyUserID + "'");
            while (RS.next()) {
                brojParova = RS.getInt("F09PRK");
                lblParovi.setText("Broj parova: " + brojParova);
            }
        } catch (Exception e) {

        }
    }

    private void PostavljanjeKoeficijenta() {
        try {
            RS = CALIzb.main(Conn, "select (EXP(SUM(LOG(NULLIF(F09KOE, 0))))) F09KOE from temp_ticket where F09UID = '" + MyUserID + "'");
            while (RS.next()) {
                koeficijent = RS.getDouble("F09KOE");
                lblTecaj.setText("Tečaj: " + decFormat.format(koeficijent));
            }
        } catch (Exception e) {

        }
    }

    //Provjera kliknutih polja
    private void AddActionListener() {

        UpisivanjeNaTempTicket CALRegistration = new UpisivanjeNaTempTicket();

        //Nogomet
        tableNogomet.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                //Get row to int
                rowClicked = tableNogomet.getValueAt(tableNogomet.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableNogomet.getSelectedColumn();
                CALRegistration.UpisivanjeNaTempTicket(rowClickedInt, colClickedInt, MyUserID, Conn);
                PostavljanjeTablica();
            }
        });

        //Tenis
        tableTenis.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            //Get row to int
            if (!event.getValueIsAdjusting()) {
                rowClicked = tableTenis.getValueAt(tableTenis.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableTenis.getSelectedColumn();
                CALRegistration.UpisivanjeNaTempTicket(rowClickedInt, colClickedInt, MyUserID, Conn);
                PostavljanjeTablica();
            }
        });

        //Košarka
        tableKosarka.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                //Get row to int
                rowClicked = tableKosarka.getValueAt(tableKosarka.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableKosarka.getSelectedColumn();
                CALRegistration.UpisivanjeNaTempTicket(rowClickedInt, colClickedInt, MyUserID, Conn);
                PostavljanjeTablica();
            }
        });

        //Hokej
        tableHokej.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                //Get row to int
                rowClicked = tableHokej.getValueAt(tableHokej.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableHokej.getSelectedColumn();
                CALRegistration.UpisivanjeNaTempTicket(rowClickedInt, colClickedInt, MyUserID, Conn);
                PostavljanjeTablica();
            }
        });

        //Temp Ticket
        tableTemp.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                //Get row to int
                tempClicked = tableTemp.getValueAt(tableTemp.getSelectedRow(), 0).toString();
                tempClickedInt = Integer.parseInt(tempClicked);
            }
        });
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

            //Punjenje TEMP tablice
            PostavljanjeTablica();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void PunjenjeTempTablice() {
        //
        RS = CALIzb.main(Conn, "select F09PRK ID, F09TIM1 Domacin, F09TIM2 Protivnik, F09TIP Tip, F09KOE Koeficijent from temp_ticket where F09UID = '" + MyUserID + "' ORDER BY F09DVT ASC");
        tableTemp.setModel(DbUtils.resultSetToTableModel(RS));
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
        pickDate = new com.toedter.calendar.JDateChooser();
        btnPotvrdi = new javax.swing.JButton();
        bUIRacun = new javax.swing.JButton();
        bMojiTicketi = new javax.swing.JButton();
        bOdjava = new javax.swing.JButton();
        lbl_UserID = new javax.swing.JLabel();
        lbl_Balance = new javax.swing.JLabel();
        lblWB = new javax.swing.JLabel();
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
        jPanel3 = new javax.swing.JPanel();
        lblParovi = new javax.swing.JLabel();
        lblTecaj = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIznosUplate = new javax.swing.JTextField();
        lblPorezNaIznUplate = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblIsplata = new javax.swing.JLabel();
        lblDobitak = new javax.swing.JLabel();
        lblBonus = new javax.swing.JLabel();
        lblPorez = new javax.swing.JLabel();
        lblPrikazPorezaJedan = new javax.swing.JLabel();
        lblPrikazPorezaDva = new javax.swing.JLabel();
        lblPrikazPorezaTri = new javax.swing.JLabel();
        lblPrikazPorezaCetri = new javax.swing.JLabel();
        lblPrikazPorezaPet = new javax.swing.JLabel();
        lblPrikazPorezaSest = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        bUplati = new javax.swing.JButton();
        btnPrikazPoreza = new javax.swing.JToggleButton();
        brisiParTemp = new javax.swing.JButton();
        brisiSveTemp = new javax.swing.JButton();
        maxUlog = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableTemp = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        lblError = new javax.swing.JLabel();
        lblCopyRight = new javax.swing.JLabel();
        lbl_Facebook = new javax.swing.JLabel();
        lbl_Twitter = new javax.swing.JLabel();
        lbl_linkedIN = new javax.swing.JLabel();
        lbl_Instagram = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        setName("UserView"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(26, 83, 92));

        img_Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/logoHTR.png"))); // NOI18N

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

        btnPotvrdi.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnPotvrdi.setText("Potvrdi");
        btnPotvrdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotvrdiActionPerformed(evt);
            }
        });

        bUIRacun.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bUIRacun.setText("Uplata / Isplata");
        bUIRacun.setToolTipText("");
        bUIRacun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUIRacunActionPerformed(evt);
            }
        });

        bMojiTicketi.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bMojiTicketi.setText("Moji ticketi");
        bMojiTicketi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMojiTicketiActionPerformed(evt);
            }
        });

        bOdjava.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bOdjava.setText("Odjava");
        bOdjava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOdjavaActionPerformed(evt);
            }
        });

        lbl_UserID.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lbl_UserID.setForeground(new java.awt.Color(255, 255, 255));
        lbl_UserID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_UserID.setText("UserID");

        lbl_Balance.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lbl_Balance.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Balance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Balance.setText("Balance");

        lblWB.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblWB.setForeground(new java.awt.Color(255, 255, 255));
        lblWB.setText("Welcome back, Josip.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pickDate, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPotvrdi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(bUIRacun, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bMojiTicketi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bOdjava))
                    .addComponent(lblWB))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addComponent(img_Logo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(lbl_UserID, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_Balance, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_UserID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(img_Logo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblWB)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bUIRacun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bMojiTicketi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bOdjava, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnPotvrdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pickDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lbl_Balance, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        MySoccerPanel.setBackground(new java.awt.Color(42, 157, 143));
        MySoccerPanel.setMaximumSize(new java.awt.Dimension(32767, 86));

        img_Soccer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/football-ball.png"))); // NOI18N

        lbl_Nogomet1.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Nogomet1.setText("Nogomet");

        javax.swing.GroupLayout MySoccerPanelLayout = new javax.swing.GroupLayout(MySoccerPanel);
        MySoccerPanel.setLayout(MySoccerPanelLayout);
        MySoccerPanelLayout.setHorizontalGroup(
            MySoccerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MySoccerPanelLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(img_Soccer)
                .addGap(18, 18, 18)
                .addComponent(lbl_Nogomet1)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        MySoccerPanelLayout.setVerticalGroup(
            MySoccerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MySoccerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MySoccerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Nogomet1)
                    .addComponent(img_Soccer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MyBasketPanel.setBackground(new java.awt.Color(233, 196, 106));
        MyBasketPanel.setMaximumSize(new java.awt.Dimension(32767, 86));

        img_basketball.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/basketball.png"))); // NOI18N

        lbl_Kosarka.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Kosarka.setText("Košarka");

        javax.swing.GroupLayout MyBasketPanelLayout = new javax.swing.GroupLayout(MyBasketPanel);
        MyBasketPanel.setLayout(MyBasketPanelLayout);
        MyBasketPanelLayout.setHorizontalGroup(
            MyBasketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyBasketPanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(img_basketball)
                .addGap(18, 18, 18)
                .addComponent(lbl_Kosarka)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        MyBasketPanelLayout.setVerticalGroup(
            MyBasketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyBasketPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MyBasketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Kosarka)
                    .addComponent(img_basketball)))
        );

        MyHockeyPanel.setBackground(new java.awt.Color(244, 162, 97));
        MyHockeyPanel.setMaximumSize(new java.awt.Dimension(32767, 86));

        imgHockey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/hockey-stick.png"))); // NOI18N

        lbl_Hokej.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Hokej.setText("Hokej");

        javax.swing.GroupLayout MyHockeyPanelLayout = new javax.swing.GroupLayout(MyHockeyPanel);
        MyHockeyPanel.setLayout(MyHockeyPanelLayout);
        MyHockeyPanelLayout.setHorizontalGroup(
            MyHockeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyHockeyPanelLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(imgHockey)
                .addGap(18, 18, 18)
                .addComponent(lbl_Hokej)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        MyHockeyPanelLayout.setVerticalGroup(
            MyHockeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyHockeyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MyHockeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Hokej)
                    .addComponent(imgHockey))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MyTennisPanel.setBackground(new java.awt.Color(231, 111, 81));
        MyTennisPanel.setMaximumSize(new java.awt.Dimension(32767, 86));

        imgTennis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/tennis-racket.png"))); // NOI18N

        lbl_Tenis.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lbl_Tenis.setText("Tenis");

        javax.swing.GroupLayout MyTennisPanelLayout = new javax.swing.GroupLayout(MyTennisPanel);
        MyTennisPanel.setLayout(MyTennisPanelLayout);
        MyTennisPanelLayout.setHorizontalGroup(
            MyTennisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyTennisPanelLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(imgTennis)
                .addGap(18, 18, 18)
                .addComponent(lbl_Tenis)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        MyTennisPanelLayout.setVerticalGroup(
            MyTennisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MyTennisPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MyTennisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Tenis)
                    .addComponent(imgTennis))
                .addContainerGap())
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
                .addGap(90, 90, 90)
                .addComponent(lbl_MojTicket)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_MojTicket)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableNogomet.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
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
        tableNogomet.setFillsViewportHeight(true);
        tableNogomet.setSelectionBackground(new java.awt.Color(42, 157, 143));
        tableNogomet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNogometMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableNogomet);

        tableKosarka.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
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
        tableKosarka.setFillsViewportHeight(true);
        tableKosarka.setSelectionBackground(new java.awt.Color(233, 196, 106));
        jScrollPane2.setViewportView(tableKosarka);

        tableHokej.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
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
        tableHokej.setFillsViewportHeight(true);
        tableHokej.setSelectionBackground(new java.awt.Color(244, 162, 97));
        jScrollPane3.setViewportView(tableHokej);

        tableTenis.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
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
        tableTenis.setFillsViewportHeight(true);
        tableTenis.setSelectionBackground(new java.awt.Color(231, 111, 81));
        jScrollPane4.setViewportView(tableTenis);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N

        lblParovi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblParovi.setForeground(new java.awt.Color(255, 255, 255));
        lblParovi.setText("4 PARA");

        lblTecaj.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTecaj.setForeground(new java.awt.Color(255, 255, 255));
        lblTecaj.setText("TEČAJ: 192,2");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ulog");

        txtIznosUplate.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        txtIznosUplate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIznosUplate.setText("1");
        txtIznosUplate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIznosUplateFocusLost(evt);
            }
        });

        lblPorezNaIznUplate.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPorezNaIznUplate.setForeground(new java.awt.Color(255, 255, 255));
        lblPorezNaIznUplate.setText("-3,35 kn (5% mt) = 63,65 kn");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Isplata");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Dobitak");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Bonus");
        jLabel7.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Porez *");
        jLabel8.setToolTipText("");
        jLabel8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel8KeyPressed(evt);
            }
        });

        lblIsplata.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIsplata.setForeground(new java.awt.Color(255, 255, 255));
        lblIsplata.setText("10.908,05 kn");

        lblDobitak.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblDobitak.setForeground(new java.awt.Color(255, 255, 255));
        lblDobitak.setText("12.233,53 kn");

        lblBonus.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblBonus.setForeground(new java.awt.Color(255, 255, 255));
        lblBonus.setText("0.000,00 kn");
        lblBonus.setToolTipText("");

        lblPorez.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblPorez.setForeground(new java.awt.Color(255, 255, 255));
        lblPorez.setText("1.325,48 kn");
        lblPorez.setToolTipText("");

        lblPrikazPorezaJedan.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPrikazPorezaJedan.setForeground(new java.awt.Color(255, 255, 255));
        lblPrikazPorezaJedan.setText("0 - 10.000 kn\t");

        lblPrikazPorezaDva.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPrikazPorezaDva.setForeground(new java.awt.Color(255, 255, 255));
        lblPrikazPorezaDva.setText("10%");

        lblPrikazPorezaTri.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPrikazPorezaTri.setForeground(new java.awt.Color(255, 255, 255));
        lblPrikazPorezaTri.setText("1000kn");

        lblPrikazPorezaCetri.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPrikazPorezaCetri.setForeground(new java.awt.Color(255, 255, 255));
        lblPrikazPorezaCetri.setText("10.000 - 30.000 kn");

        lblPrikazPorezaPet.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPrikazPorezaPet.setForeground(new java.awt.Color(255, 255, 255));
        lblPrikazPorezaPet.setText("15%");

        lblPrikazPorezaSest.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPrikazPorezaSest.setForeground(new java.awt.Color(255, 255, 255));
        lblPrikazPorezaSest.setText("325,48 kn");

        bUplati.setText("Uplati");
        bUplati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUplatiActionPerformed(evt);
            }
        });

        btnPrikazPoreza.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnPrikazPoreza.setText("?");
        btnPrikazPoreza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrikazPorezaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bUplati, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblParovi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTecaj, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblPorezNaIznUplate)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtIznosUplate)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(lblPrikazPorezaCetri)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrikazPoreza, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPrikazPorezaJedan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(lblPrikazPorezaPet)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPrikazPorezaSest))
                            .addComponent(lblPrikazPorezaTri, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPrikazPorezaDva)
                            .addComponent(lblIsplata, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(lblDobitak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPorez, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBonus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblParovi)
                    .addComponent(lblTecaj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIznosUplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPorezNaIznUplate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblIsplata))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblDobitak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblBonus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblPorez)
                    .addComponent(btnPrikazPoreza, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrikazPorezaJedan)
                    .addComponent(lblPrikazPorezaDva)
                    .addComponent(lblPrikazPorezaTri))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrikazPorezaCetri)
                    .addComponent(lblPrikazPorezaPet)
                    .addComponent(lblPrikazPorezaSest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bUplati, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblPorezNaIznUplate.getAccessibleContext().setAccessibleName("lblPorezNaIznUplate");

        brisiParTemp.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        brisiParTemp.setText("Izbriši par");
        brisiParTemp.setToolTipText("");
        brisiParTemp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        brisiParTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brisiParTempActionPerformed(evt);
            }
        });

        brisiSveTemp.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        brisiSveTemp.setText("Izbriši sve");
        brisiSveTemp.setToolTipText("");
        brisiSveTemp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        brisiSveTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brisiSveTempActionPerformed(evt);
            }
        });

        maxUlog.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        maxUlog.setText("Max. ulog");
        maxUlog.setToolTipText("");
        maxUlog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        maxUlog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxUlogActionPerformed(evt);
            }
        });

        tableTemp.setBackground(new java.awt.Color(204, 204, 204));
        tableTemp.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tableTemp.setModel(new javax.swing.table.DefaultTableModel(
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
        tableTemp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableTemp.setFillsViewportHeight(true);
        tableTemp.setSelectionBackground(new java.awt.Color(204, 0, 51));
        jScrollPane6.setViewportView(tableTemp);

        jPanel4.setBackground(new java.awt.Color(26, 83, 92));

        lblError.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 102, 102));
        lblError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblCopyRight.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lblCopyRight.setForeground(new java.awt.Color(255, 255, 255));
        lblCopyRight.setText("CopyRight © by Jskako.");

        lbl_Facebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/icn_facebook.png"))); // NOI18N
        lbl_Facebook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_FacebookMouseClicked(evt);
            }
        });

        lbl_Twitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/icn_twitter .png"))); // NOI18N
        lbl_Twitter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_TwitterMouseClicked(evt);
            }
        });

        lbl_linkedIN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/icn_LinkedIN.png"))); // NOI18N
        lbl_linkedIN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_linkedINMouseClicked(evt);
            }
        });

        lbl_Instagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/htr/Images/icn_Instagram.png"))); // NOI18N
        lbl_Instagram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_InstagramMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Facebook)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_Twitter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_linkedIN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_Instagram)
                .addGap(11, 11, 11)
                .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCopyRight)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Instagram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_linkedIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Twitter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Facebook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCopyRight, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MySoccerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MyBasketPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MyHockeyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(MyTennisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(brisiParTemp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(brisiSveTemp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maxUlog, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(MyHockeyPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(MySoccerPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MyBasketPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MyTennisPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(brisiParTemp)
                            .addComponent(brisiSveTemp)
                            .addComponent(maxUlog))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        brisiParTemp.getAccessibleContext().setAccessibleName("brisiParTemp");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pickDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pickDatePropertyChange

    }//GEN-LAST:event_pickDatePropertyChange

    private void pickDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pickDateFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_pickDateFocusLost

    private void btnPotvrdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotvrdiActionPerformed
        // TODO add your handling code here:
        tableNogomet.setModel(new DefaultTableModel());
        tableKosarka.setModel(new DefaultTableModel());
        tableHokej.setModel(new DefaultTableModel());
        tableTenis.setModel(new DefaultTableModel());

        sd = dateFormat.format(pickDate.getDate());
        PunjenjeTablicePodacima(sd);
    }//GEN-LAST:event_btnPotvrdiActionPerformed

    private void pickDateVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_pickDateVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_pickDateVetoableChange

    private void tableNogometMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNogometMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableNogometMouseClicked

    private void bOdjavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOdjavaActionPerformed
        // TODO add your handling code here:
        LoginForm CALLogin = new LoginForm(Conn);
        CALLogin.setLocationRelativeTo(null);
        CALLogin.setVisible(true);
        dispose();
    }//GEN-LAST:event_bOdjavaActionPerformed

    private void jLabel8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel8KeyPressed

    }//GEN-LAST:event_jLabel8KeyPressed

    private void btnPrikazPorezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrikazPorezaActionPerformed
        // TODO add your handling code here:
        if (btnPrikazPoreza.isSelected()) {
            lblPrikazPorezaJedan.setVisible(true);
            lblPrikazPorezaDva.setVisible(true);
            lblPrikazPorezaTri.setVisible(true);
            lblPrikazPorezaCetri.setVisible(true);
            lblPrikazPorezaPet.setVisible(true);
            lblPrikazPorezaSest.setVisible(true);
        } else {
            lblPrikazPorezaJedan.setVisible(false);
            lblPrikazPorezaDva.setVisible(false);
            lblPrikazPorezaTri.setVisible(false);
            lblPrikazPorezaCetri.setVisible(false);
            lblPrikazPorezaPet.setVisible(false);
            lblPrikazPorezaSest.setVisible(false);
        }
    }//GEN-LAST:event_btnPrikazPorezaActionPerformed

    private void brisiParTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brisiParTempActionPerformed
        // TODO add your handling code here:
        RS = CALIzb.main(Conn, "delete from Temp_Ticket where F09PRK = '" + tempClickedInt + "'");
        PostavljanjeTablica();
    }//GEN-LAST:event_brisiParTempActionPerformed

    private void brisiSveTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brisiSveTempActionPerformed
        // TODO add your handling code here:
        setErrorLabel("Temp ticket izbrisan!");
        RS = CALIzb.main(Conn, "delete from Temp_Ticket where F09UID = '" + MyUserID + "'");
        PostavljanjeTablica();
    }//GEN-LAST:event_brisiSveTempActionPerformed

    private void maxUlogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxUlogActionPerformed
        // TODO add your handling code here:
        txtIznosUplate.setText(String.valueOf(MyUserBalance));
    }//GEN-LAST:event_maxUlogActionPerformed

    private void bUplatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUplatiActionPerformed
        // TODO add your handling code here:
        BrisanjeStarihParova();
        int myTempID = 0;
        int myTempParID = 0;
        String mySelectedRow = null;
        double myTempUserStanje = 0;
        try {
            //Dohvacanje zadnjeg ID-a
            RS = CALIzb.main(Conn, "SELECT TOP 1 F08IDT FROM ticket ORDER BY F08IDT DESC");
            while (RS.next()) {
                myTempID = RS.getInt("F08IDT");
                myTempID += 1;
            }
            if (myTempID == 0) {
                myTempID += 1;
            }
        } catch (Exception e) {

        }
        try {

            //Upis u ticket
            getUserBalance();
            myTempUserStanje = MyUserBalance - Double.parseDouble(txtIznosUplate.getText().trim());
            if (tableTemp.getRowCount() != 0) {
                if (myTempUserStanje >= 0) {
                    RS = CALIzb.main(Conn, "update User_Stanje set F03STA = '" + myTempUserStanje + "' where F03UID = '" + MyUserID + "'");
                    lbl_Balance.setText(Double.toString(myTempUserStanje));
                    //Upis ticketa
                    RS = CALIzb.main(Conn, "insert into Ticket values ('" + myTempID + "', '" + String.valueOf(decFormat.format(iznosIsplate)) + "', '" + decFormat.format(ukupanDobitak) + "', '" + decFormat.format(ukupniIznosSPdv) + "', '0','" + decFormat.format(porezNaUkupanIznos) + "', '" + decFormat.format(koeficijent) + "', GETDATE(), '" + MyUserID + "')");

                    //Upis parova
                    int rows = tableTemp.getRowCount();
                    //Idemo kroz tablicu
                    for (int i = 0; i < rows; i++) {
                        //Dohvacanje ID-a
                        RS = CALIzb.main(Conn, "SELECT TOP 1 F081IDT FROM Ticket_parovi ORDER BY F081IDT DESC");
                        while (RS.next()) {
                            myTempParID = RS.getInt("F081IDT");
                            myTempParID += 1;
                        }
                        if (myTempParID == 0) {
                            myTempParID += 1;
                        }

                        mySelectedRow = tableTemp.getValueAt(i, 0).toString();
                        System.out.println(mySelectedRow);
                        RS = CALIzb.main(Conn, "insert into Ticket_parovi values('" + myTempParID + "', '" + myTempID + "', (select F09TIM1 from Temp_Ticket where F09PRK = '" + mySelectedRow + "'), (select F09TIM2 from Temp_Ticket where F09PRK = '" + mySelectedRow + "'), (select F09TIP from Temp_Ticket where F09PRK = '" + mySelectedRow + "'), (select F09KOE from Temp_Ticket where F09PRK = '" + mySelectedRow + "'), GETDATE(), '" + MyUserID + "')");
                    }
                    RS = CALIzb.main(Conn, "delete from Temp_Ticket where F09UID = '" + MyUserID + "'");
                    PostavljanjeTablica();
                    setErrorLabel("Uplata uspješna!");

                } else {
                    setErrorLabel("Nemate dovoljno sredstava na računu!");
                }
            } else {
                setErrorLabel("Niste odigrali niti jedan par!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_bUplatiActionPerformed

    private void txtIznosUplateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIznosUplateFocusLost
        // TODO add your handling code here:
        PostavljanjeTablica();
    }//GEN-LAST:event_txtIznosUplateFocusLost

    private void bMojiTicketiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMojiTicketiActionPerformed
        // TODO add your handling code here:
        MojiTicketi CALMojiTicketi = new MojiTicketi(Conn, MyUserID);
        CALMojiTicketi.setLocationRelativeTo(null);
        UserView.setUndecorated(false);
        CALMojiTicketi.setVisible(true);
    }//GEN-LAST:event_bMojiTicketiActionPerformed

    private void bUIRacunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUIRacunActionPerformed
        // TODO add your handling code here:
        UIOdobrenje CALUIOdob = new UIOdobrenje(Conn, MyUserID, MyUserBalance);
        CALUIOdob.setLocationRelativeTo(null);
        CALUIOdob.setVisible(true);
    }//GEN-LAST:event_bUIRacunActionPerformed

    private void lbl_FacebookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_FacebookMouseClicked
        // TODO add your handling code here:
        GoLink("https://www.facebook.com/jskako");
    }//GEN-LAST:event_lbl_FacebookMouseClicked

    private void lbl_TwitterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_TwitterMouseClicked
        // TODO add your handling code here:
        GoLink("https://twitter.com/search-home");
    }//GEN-LAST:event_lbl_TwitterMouseClicked

    private void lbl_linkedINMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_linkedINMouseClicked
        // TODO add your handling code here:
        GoLink("https://www.linkedin.com/in/josipskako");
    }//GEN-LAST:event_lbl_linkedINMouseClicked

    private void lbl_InstagramMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_InstagramMouseClicked
        // TODO add your handling code here:
        GoLink("https://www.instagram.com/?hl=hr");
    }//GEN-LAST:event_lbl_InstagramMouseClicked

    private void GoLink(String Link) {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI(Link.trim());
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setErrorLabel(String error) {
        lblError.setText(error.trim());
        Timer timer = new Timer(2000, e -> lblError.setText(""));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MyBasketPanel;
    private javax.swing.JPanel MyHockeyPanel;
    private javax.swing.JPanel MySoccerPanel;
    private javax.swing.JPanel MyTennisPanel;
    private javax.swing.JButton bMojiTicketi;
    private javax.swing.JButton bOdjava;
    private javax.swing.JButton bUIRacun;
    private javax.swing.JButton bUplati;
    private javax.swing.JButton brisiParTemp;
    private javax.swing.JButton brisiSveTemp;
    private javax.swing.JButton btnPotvrdi;
    private javax.swing.JToggleButton btnPrikazPoreza;
    private javax.swing.JLabel imgHockey;
    private javax.swing.JLabel imgTennis;
    private javax.swing.JLabel img_Logo;
    private javax.swing.JLabel img_Soccer;
    private javax.swing.JLabel img_basketball;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblBonus;
    private javax.swing.JLabel lblCopyRight;
    private javax.swing.JLabel lblDobitak;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblIsplata;
    private javax.swing.JLabel lblParovi;
    private javax.swing.JLabel lblPorez;
    private javax.swing.JLabel lblPorezNaIznUplate;
    private javax.swing.JLabel lblPrikazPorezaCetri;
    private javax.swing.JLabel lblPrikazPorezaDva;
    private javax.swing.JLabel lblPrikazPorezaJedan;
    private javax.swing.JLabel lblPrikazPorezaPet;
    private javax.swing.JLabel lblPrikazPorezaSest;
    private javax.swing.JLabel lblPrikazPorezaTri;
    private javax.swing.JLabel lblTecaj;
    private javax.swing.JLabel lblWB;
    private javax.swing.JLabel lbl_Balance;
    private javax.swing.JLabel lbl_Facebook;
    private javax.swing.JLabel lbl_Hokej;
    private javax.swing.JLabel lbl_Instagram;
    private javax.swing.JLabel lbl_Kosarka;
    private javax.swing.JLabel lbl_MojTicket;
    private javax.swing.JLabel lbl_Nogomet1;
    private javax.swing.JLabel lbl_Tenis;
    private javax.swing.JLabel lbl_Twitter;
    private javax.swing.JLabel lbl_UserID;
    private javax.swing.JLabel lbl_linkedIN;
    private javax.swing.JButton maxUlog;
    private com.toedter.calendar.JDateChooser pickDate;
    private javax.swing.JTable tableHokej;
    private javax.swing.JTable tableKosarka;
    private javax.swing.JTable tableNogomet;
    private javax.swing.JTable tableTemp;
    private javax.swing.JTable tableTenis;
    private javax.swing.JTextField txtIznosUplate;
    // End of variables declaration//GEN-END:variables
}
