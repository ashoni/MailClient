/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.entityImpl;

import demail.model.common.AddressInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
/**
 *
 * @author Анна
 */
@Entity
@Table(name="AddressInfo")
public class AddressInfoEntity implements AddressInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String mailAddress;
    
    private String reserveMailAddress;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date creationDate;

    @OneToMany(mappedBy="addressInfoEntity",cascade=CascadeType.REMOVE)
    private List<DirectoryEntity> dList = new ArrayList<DirectoryEntity>();
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable=false, updatable=false)
    private UserInfoEntity userInfoEntity;
    
    @NotNull
    private String password;
    
    private static final long serialVersionUID = 1L;

    public AddressInfoEntity(Long userId, String mailAddress, String reserveMailAddress, Date creationDate, String password) {
        this.userId = userId;
        this.mailAddress = mailAddress;
        this.reserveMailAddress = reserveMailAddress;
        this.creationDate = creationDate;
        this.password = password;
    }

    public AddressInfoEntity() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    
    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
