/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.dao;

import demail.model.common.AddressInfo;
import demail.model.entityImpl.AddressInfoEntity;

/**
 *
 * @author Анна
 */
public interface AddressInfoDAO  extends DAO <Long, AddressInfoEntity> {
    public AddressInfo getByAddress(String mailAddress);
}
