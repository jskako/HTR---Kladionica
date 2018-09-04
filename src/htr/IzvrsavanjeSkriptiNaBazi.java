/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jskako
 */
public class IzvrsavanjeSkriptiNaBazi {

    private Connection CONN;
    private String SQL;
    ResultSet rs;
    public  ResultSet main(Connection conn, String sql) {
        this.CONN = conn;
        this.SQL = sql;
        try {
            Statement stmt = CONN.createStatement();
            rs = stmt.executeQuery(SQL);
        } catch (SQLException e) {

        } 
        
        return rs;

    }
}
