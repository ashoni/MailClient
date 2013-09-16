/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.web.modelImpl;

import demail.model.common.UserInfo;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Анна
 */
public class UserInfoImpl implements UserInfo {
    private Long id;
    
    private String name;

    private String surname;

    private String phone;

    private String dateOfBirth;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    @DateTimeFormat(pattern="dd.mm.yyyy")
    private Date birthday;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserInfoImpl(String name, String surname, String phone, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.birthday = birthday;
    }

    public UserInfoImpl() {
        this.birthday = new Date();
    }    
}
