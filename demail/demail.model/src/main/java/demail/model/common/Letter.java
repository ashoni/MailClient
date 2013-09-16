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
public interface Letter extends Persistent<Long>, Serializable  {

    public String getSubject();

    public void setSubject(String subject);

    public String getBody();

    public void setBody(String body);

    public Date getCreationDate();

    public void setCreationDate(Date creationDate);

    public Long getId();

    public void setId(Long id);    
}