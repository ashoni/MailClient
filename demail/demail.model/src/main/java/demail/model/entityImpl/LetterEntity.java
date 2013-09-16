/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.entityImpl;

import demail.model.common.Letter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


/**
 *
 * @author Анна
 */

@Entity
@Table(name="Letter")
public class LetterEntity implements Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    @Lob 
    @Column(name="BODY", length=1500)
    private String body;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy="letterEntity",cascade=CascadeType.REMOVE)
    private List<LetterMetaInfoEntity> lmieList = new ArrayList<LetterMetaInfoEntity>();
   
    private static final long serialVersionUID = 1L;
    
    public LetterEntity() {
    }

    public LetterEntity(String subject, String body, Date creationDate) {
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
    }    
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
