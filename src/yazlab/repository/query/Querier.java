/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package yazlab.repository.query;

import java.sql.SQLException ;
import java.sql.ResultSet ;

/**
 *
 * @author kaan
 */
public interface Querier {
    
    public ResultSet makeGettingQueryById (Long id)  throws SQLException ;
        
    public void makeDeletingQueryById (Long id) throws SQLException ;
        
}
