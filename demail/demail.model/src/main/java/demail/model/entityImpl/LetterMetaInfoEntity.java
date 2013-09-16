/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.entityImpl;

import demail.model.common.PairImpl;
import demail.model.common.LetterMetaInfo;
import demail.model.common.LetterStates;
import javax.persistence.*;
import javax.validation.constraints.*;



/**
 *
 * @author Анна
 */

@Entity
@Table(name="LetterMetaInfo")
@IdClass(PairImpl.class)
public class LetterMetaInfoEntity implements LetterMetaInfo {

    public Long getLetterId() {
        return letterId;
    }

    public void setLetterId(Long letterId) {
        this.letterId = letterId;
    }

    @NotNull
    private Long dirId;

    @Id
    private Long loginId;
    
    @Id
    private Long letterId;

    private String partnerMailAddress;

    @NotNull
    private LetterStates letterState;

    @ManyToOne
    @JoinColumn(name = "dirId", insertable=false, updatable=false)
    private DirectoryEntity directoryEntity;

    @ManyToOne
    private LetterEntity letterEntity;

    private static final long serialVersionUID = 1L;

    public Long getDirId() {
        return dirId;
    }

    public void setDirId(Long dirId) {
        this.dirId = dirId;
        //this.dirMap = dirId;
    }

    public String getPartnerMailAddress() {
        return partnerMailAddress;
    }

    public void setPartnerMailAddress(String partnerMailAddress) {
        this.partnerMailAddress = partnerMailAddress;
    }

    public LetterStates getLetterState() {
        return letterState;
    }

    public void setLetterState(LetterStates letterState) {
        this.letterState = letterState;
    }

    public PairImpl getId() {
        return new PairImpl(loginId, letterId);
    }

    public void setId(PairImpl id) {
        loginId = id.getFirst();
        letterId = id.getSecond();
   //     letterIdMap = letterId;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
    
    public LetterMetaInfoEntity() {
    }

    public LetterMetaInfoEntity(Long letterId, Long dirId, Long loginId, String partnerMailAddress, LetterStates letterState) {
        this.letterId = letterId;
        this.dirId = dirId;
        this.loginId = loginId;
        this.partnerMailAddress = partnerMailAddress;
        this.letterState = letterState;
        //this.dirMap = dirId;
 //       this.letterIdMap = letterId;
    }

}
