/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.common;

import demail.model.common.LetterStates;
import demail.model.entity.Persistent;
import java.io.Serializable;

/**
 *
 * @author Анна
 */
public interface LetterMetaInfo  extends Persistent<PairImpl>, Serializable {
    
    public String getPartnerMailAddress();

    public void setPartnerMailAddress(String ownerMailAddress);
    
    public Long getDirId();

    public void setDirId(Long dirId);
    
    public LetterStates getLetterState();

    public void setLetterState(LetterStates letterState);
    
    public Long getLoginId();

    public void setLoginId(Long loginId);
    
    public Long getLetterId();

    public void setLetterId(Long loginId);    
}
