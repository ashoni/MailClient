/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.common;

import demail.model.entity.Persistent;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Анна
 */
public interface AddressInfo extends Persistent<Long>, Serializable  {
    
	public String getMailAddress();

	public void setMailAddress(String mailAddress);
        
        public String getReserveMailAddress();

	public void setReserveMailAddress(String mailAddress);
        
	public Long getUserId();

	public void setUserId(Long userId);
        
	public Date getCreationDate();

	public void setCreationDate(Date creationDate);
        
	public String getPassword();

	public void setPassword(String password);
}
