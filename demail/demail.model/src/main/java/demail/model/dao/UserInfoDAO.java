/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.dao;


import demail.model.entityImpl.UserInfoEntity;

/**
 *
 * @author Анна
 */
public interface UserInfoDAO extends DAO <Long, UserInfoEntity> {
    
    public Long findUser(String phone);
    
}
