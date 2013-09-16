/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.entityImpl;

import demail.model.common.DirStates;
import demail.model.common.Directory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author Анна
 */
@Entity
@Table(name="Directory")
public class DirectoryEntity implements Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long loginId;

    @Pattern(regexp="[A-Za-z0-9_\\-\\s]+")
    private String name;
    
    @NotNull
    private DirStates dirState;

    @OneToMany(mappedBy="directoryEntity",cascade=CascadeType.REMOVE)
    private List<LetterMetaInfoEntity> lmieList = new ArrayList<LetterMetaInfoEntity>();

    @ManyToOne
    @JoinColumn(name = "loginId", insertable=false, updatable=false)
    private AddressInfoEntity addressInfoEntity;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DirStates getDirState() {
        return dirState;
    }

    public void setDirState(DirStates dirState) {
        this.dirState = dirState;
    }

    public DirectoryEntity(Long loginId, String name, DirStates dirState) {
        this.loginId = loginId;
        this.name = name;
        this.dirState = dirState;
    }

    public DirectoryEntity() {
    }
}
