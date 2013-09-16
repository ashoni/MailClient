/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.web.modelImpl;

import demail.model.common.AddressInfo;
import java.util.Date;

/**
 *
 * @author Анна
 */
public class AddressInfoImpl implements AddressInfo {
    private Long id;

    private Long userId;

    private String mailAddress;
    
    private String reserveMailAddress;

    private Date creationDate;

    private String password;

    public AddressInfoImpl(String mailAddress, String reserveMailAddress, String password) {
        this.mailAddress = mailAddress;
        this.reserveMailAddress = reserveMailAddress;
        this.password = password;
        this.creationDate = new Date();
    }

    public AddressInfoImpl() {
        this.creationDate = new Date();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getReserveMailAddress() {
        return reserveMailAddress;
    }

    public void setReserveMailAddress(String reserveMailAddress) {
        this.reserveMailAddress = reserveMailAddress;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
