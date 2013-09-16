/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.web.modelImpl;

import demail.model.common.Letter;
import java.util.Date;

/**
 *
 * @author Анна
 */
public class LetterImpl implements Letter {
    private Long id;
    private String subj;
    private String letterText;
    private String recipient;
    private Date creationDate;

    public LetterImpl(String subj, String letterText) {
        this.subj = subj;
        this.letterText = letterText;
    }
    
    
    
    public String getSubject() {
        return subj; 
    }

    public void setSubject(String subject) {
        subj = subject;
    }

    public String getBody() {
        return letterText;
    }

    public void setBody(String body) {
        letterText = body;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
