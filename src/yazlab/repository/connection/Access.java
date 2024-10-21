/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.repository.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException ;

/**
 *
 * @author kaan
 */
public class Access {

    private static Access access;

    private Connection connection;

    private Access() {

    }

    public static Access getInstance() {
        if (access == null) {
            access = new Access();
        }
        return access;
    }

    public Connection createConnection() throws SQLException {
        if (connection == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("jdbc").append(":").append(DbValues.ConnectionValues.getRDMS_NAME()).append("://")
                    .append(DbValues.ConnectionValues.getHOST()).append(":")
                    .append(DbValues.ConnectionValues.getPORT()).append("/")
                    .append(DbValues.ConnectionValues.getDB_NAME());
            final String connectionUrl = sb.toString();
            connection = DriverManager.getConnection(connectionUrl, DbValues.ConnectionValues.getUSERNAME(), DbValues.ConnectionValues.getPASSWORD());
        }
        return connection;
    }

}
