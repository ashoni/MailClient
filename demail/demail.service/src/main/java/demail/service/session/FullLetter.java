/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.service.session;

import demail.model.common.LetterMetaInfo;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Анна
 */
public class FullLetter implements Serializable {
    private LetterMetaInfo letterMetaInfo;
    private Date date;
    private String subject;

    private static final long serialVersionUID = 1L;
    
    public FullLetter(LetterMetaInfo letterMetaInfo, Date date, String subject) {
        this.letterMetaInfo = letterMetaInfo;
        this.date = date;
        this.subject = subject;
    }
    
    public LetterMetaInfo getLetterMetaInfo() {
        return letterMetaInfo;
    }

    public void setLetterMetaInfo(LetterMetaInfo letterMetaInfo) {
        this.letterMetaInfo = letterMetaInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
}
