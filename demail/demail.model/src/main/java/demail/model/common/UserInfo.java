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
public interface UserInfo extends Persistent<Long>, Serializable {
    public String getName();

    public void setName(String name);

    public String getSurname();

    public void setSurname(String surname);

    public String getPhone();

    public void setPhone(String phone);

    public Date getBirthday();

    public void setBirthday(Date birthday);
}
