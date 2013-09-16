package demail.model.entityImpl;

import demail.model.common.UserInfo;
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
@Table(name="UserInfo")
public class UserInfoEntity implements UserInfo {
    
    @Id	
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp="[A-Za-z][a-z]+")
    private String name;

    @Pattern(regexp="[A-Za-z][a-z]+")
    private String surname;

    @Pattern(regexp="[0-9]+")
    private String phone;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthday;
    
    @OneToMany(mappedBy="userInfoEntity",cascade=CascadeType.REMOVE)
    private List<AddressInfoEntity> addressList = new ArrayList<AddressInfoEntity>();

    private static final long serialVersionUID = 1L;
        
    public UserInfoEntity(String name, String surname, String phone, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.birthday = birthday;
    }
 
    public UserInfoEntity() {
    }    

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
    
}
