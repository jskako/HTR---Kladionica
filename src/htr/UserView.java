/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.awt.Frame;
import java.awt.Toolkit;
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

    //Spajanje na bazu
    IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();

    //Global Var
    int MyUserID;
    double MyUserBalance;
    ResultSet RS = null;
    String sd = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String rowClicked;
    int rowClickedInt;
    int colClickedInt;

    public UserView(Connection con, String user) {
        this.Conn = con;
        this.User = user;
        this.setAlwaysOnTop(true);

        initComponents();

        //Postavljanje ROW-HEIGHT tablice
        tableNogomet.setRowHeight(22);
        tableKosarka.setRowHeight(22);
        tableHokej.setRowHeight(22);
        tableTenis.setRowHeight(22);

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

        lbl_UserName.setText(User);
        lbl_UserName.setHorizontalAlignment(lbl_UserName.CENTER);
        lbl_Balance.setText("| " + MyUserBalance + "kn");
        lbl_Balance.setHorizontalAlignment(lbl_UserName.CENTER);
        //Brišemo title bar
        Frame UserView = new Frame();
        UserView.setUndecorated(true);
    }

    //Provjera kliknutih polja
    private void AddActionListener() {

        //Nogomet
        tableNogomet.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row

                //Get row to int
                rowClicked = tableNogomet.getValueAt(tableNogomet.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableNogomet.getSelectedColumn();
                UpisivanjeNaTicket(rowClickedInt, colClickedInt);
            }
        });

        //Tenis
        tableTenis.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row

                //Get row to int
                rowClicked = tableTenis.getValueAt(tableTenis.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableTenis.getSelectedColumn();
                UpisivanjeNaTicket(rowClickedInt, colClickedInt);
            }
        });

        //Košarka
        tableKosarka.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row

                //Get row to int
                rowClicked = tableKosarka.getValueAt(tableKosarka.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableKosarka.getSelectedColumn();
                UpisivanjeNaTicket(rowClickedInt, colClickedInt);
            }
        });

        //Hokej
        tableHokej.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row

                //Get row to int
                rowClicked = tableHokej.getValueAt(tableHokej.getSelectedRow(), 0).toString();
                rowClickedInt = Integer.parseInt(rowClicked);
                //Get col to int
                colClickedInt = tableHokej.getSelectedColumn();
                UpisivanjeNaTicket(rowClickedInt, colClickedInt);
            }
        });
    }

    private void UpisivanjeNaTicket(int row, int col) {
        //Row nam je ID para
        int myRowTemp = 0;
        //Col nam je ID kolone (1,x,2) <-> (3,4,5)

        //Provjera da li par na ticketu vec postoji
        //Ukoliko postoji replace ga ukoliko ima razlicitu Col ili brisemo ukoliko ima istu
        //Ukoliko ne postoji dodajemo ga
        //Upisujemo koeficijente i preglede
        //Refreshamo pregled listica i dobitka
        //ZNANI PROBLEMI - Forma za slanje mail-a se suzi, mail se ne salje, napraviti placanje, napravidi admin formu, oznaciti upisane parove u tablici, 
        //napraviti uplatu i isplatu, napraviti pregled mojih racuna, napraviti bonuse
        try {
            RS = CALIzb.main(Conn, "select F09IDT from Temp_Ticket where F09IDT = '"+row+"'");
            while (RS.next()) {
                myRowTemp = RS.getInt("F09IDT");
                
                if(myRowTemp!=0){
                    //Ako postoji
                    
                }
                else{
                    //Ako ne postoji
                     RS = CALIzb.main(Conn, "insert into Temp_Ticket values('1','2',(Select F07TM1 from parovi where F07IDP = '2'),(Select F07TM2 from parovi where F07IDP = '2'),'2',(SELECT F07KO2 FROM parovi where F07IDP = '2'),'1','3',getdate(),getdate());");
                }
                
                
            }
        } catch (Exception e) {

        }

        System.out.println("Row: " + row);
        System.out.println("Col: " + col);

        //Ako je col = 3
        RS = CALIzb.main(Conn, "Select F07KO1 from Parovi where F07IDP = '" + row + "'");

        //Ako je col = 4
        //Ako je col = 5
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
        bUplatanaRacun = new javax.swing.JButton();
        bIsplata = new javax.swing.JButton();
        bMojiTicketi = new javax.swing.JButton();
        bOdjava = new javax.swing.JButton();
        lbl_Tip = new javax.swing.JLabel();
        lbl_Balance1 = new javax.swing.JLabel();
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
        jScrollPane5 = new javax.swing.JScrollPane();
        tableTenis1 = new javax.swing.JTable();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        setName("UserView"); // NOI18N
        setUndecorated(true);
        setResizable(false);

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

        btnPotvrdi.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnPotvrdi.setText("Potvrdi");
        btnPotvrdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotvrdiActionPerformed(evt);
            }
        });

        bUplatanaRacun.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bUplatanaRacun.setText("Uplata");

        bIsplata.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bIsplata.setText("Isplata");

        bMojiTicketi.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bMojiTicketi.setText("Moji ticketi");

        bOdjava.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        bOdjava.setText("Odjava");
        bOdjava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOdjavaActionPerformed(evt);
            }
        });

        lbl_Tip.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        lbl_Tip.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Tip.setText("Preporuka: ");

        lbl_Balance1.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        lbl_Balance1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Balance1.setText("Uplatom četri para istog tipa sporta dobijate 10% više!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pickDate, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPotvrdi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(bUplatanaRacun, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bIsplata, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bMojiTicketi)
                .addGap(18, 18, 18)
                .addComponent(bOdjava)
                .addGap(170, 170, 170)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_UserName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_Balance)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(img_Logo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                        .addComponent(lbl_Tip)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Balance1)
                        .addGap(87, 87, 87))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Balance))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 61, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bUplatanaRacun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bIsplata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bMojiTicketi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bOdjava, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnPotvrdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pickDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(img_Logo)
                        .addContainerGap(26, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Tip, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Balance1))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        tableTenis1.setBackground(new java.awt.Color(204, 204, 204));
        tableTenis1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        tableTenis1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
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
        tableTenis1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableTenis1.setEnabled(false);
        jScrollPane5.setViewportView(tableTenis1);

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
        txtIznosUplate.setText("67");

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
        lblBonus.setText("2.342,48 kn");
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
                                .addGap(0, 76, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                    .addComponent(MyTennisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE))
                    .addComponent(jSeparator1)))
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

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MyBasketPanel;
    private javax.swing.JPanel MyHockeyPanel;
    private javax.swing.JPanel MySoccerPanel;
    private javax.swing.JPanel MyTennisPanel;
    private javax.swing.JButton bIsplata;
    private javax.swing.JButton bMojiTicketi;
    private javax.swing.JButton bOdjava;
    private javax.swing.JButton bUplatanaRacun;
    private javax.swing.JButton bUplati;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblBonus;
    private javax.swing.JLabel lblDobitak;
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
    private javax.swing.JLabel lbl_Balance;
    private javax.swing.JLabel lbl_Balance1;
    private javax.swing.JLabel lbl_Hokej;
    private javax.swing.JLabel lbl_Kosarka;
    private javax.swing.JLabel lbl_MojTicket;
    private javax.swing.JLabel lbl_Nogomet1;
    private javax.swing.JLabel lbl_Tenis;
    private javax.swing.JLabel lbl_Tip;
    private javax.swing.JLabel lbl_UserName;
    private com.toedter.calendar.JDateChooser pickDate;
    private javax.swing.JTable tableHokej;
    private javax.swing.JTable tableKosarka;
    private javax.swing.JTable tableNogomet;
    private javax.swing.JTable tableTenis;
    private javax.swing.JTable tableTenis1;
    private javax.swing.JTextField txtIznosUplate;
    // End of variables declaration//GEN-END:variables
}
