/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.repository.query;

import java.sql.PreparedStatement;
import yazlab.repository.connection.Access;
import java.sql.SQLException;

/**
 *
 * @author kaan
 */
public abstract class BaseQuerier implements Querier , Transformer {

    private PreparedStatement preparedStatement;

    private Access access;

    public BaseQuerier() {
        access = Access.getInstance();
    }

    protected PreparedStatement getPreparedStatement(String queryCommand) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
            preparedStatement = null ;
            System.gc();
        }
        preparedStatement = access.createConnection().prepareStatement(queryCommand);
        return preparedStatement;
    }

}
