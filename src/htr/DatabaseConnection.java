/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author josips
 */
public class DatabaseConnection {

//Definiranje parametara
    Statement stmt = null;
    String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    Connection conn = null;
    String DB_URL = null;
    String ConnectionDB = "brik"; //DESKTOP-LAGU3T1
    int portDB = 1433;
    String dataBaseName = "obuka"; //HTR
    
    public void main() {
        
       
        try {
            
            DB_URL = "jdbc:sqlserver://" + ConnectionDB + ":" + portDB + ";"
                    + "databaseName=" + dataBaseName + ";integratedSecurity=true;";
            // 2. Registriranje drivera
            Class.forName(JDBC_DRIVER);
            // 3. Otvaranje i testiranje konekcije
            conn = DriverManager.getConnection(DB_URL);     
            //4. Generiranje parova i zvanje log-in screen
            ResultSet RS = null;
            IzvrsavanjeSkriptiNaBazi CALIzb = new IzvrsavanjeSkriptiNaBazi();
            RS = CALIzb.main(conn, "EXEC Generiranje_Parova");
         
            LoginForm CALLogin = new LoginForm(conn);
            CALLogin.setLocationRelativeTo(null);
            CALLogin.setVisible(true);
        } catch (SQLException f) {
            //Podigni Error
            PopError CALError = new PopError();
            CALError.infoBox("Neuspješno spajanje na bazu", "Error DB01X");
            f.printStackTrace();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Zatvaranje resursa
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            
        }
        
    }
    
}
