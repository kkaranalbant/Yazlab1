/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package yazlab.repository.query;

import yazlab.model.BaseEntity;

import java.sql.ResultSet ;

import java.sql.SQLException ;
import java.util.List;

/**
 *
 * @author kaan
 */
public interface Transformer {
    
    public List<? extends BaseEntity> transform (ResultSet resultSet) throws SQLException ;
    
    
}
