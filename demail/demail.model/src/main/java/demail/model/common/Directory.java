/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.common;

import demail.model.entity.Persistent;
import java.io.Serializable;

/**
 *
 * @author Анна
 */
public interface Directory extends Persistent<Long>, Serializable {

	public Long getLoginId();

	public void setLoginId(Long userId);
        
	public String getName();

	public void setName(String name);
        
	public DirStates getDirState();

	public void setDirState(DirStates dirState);
}
