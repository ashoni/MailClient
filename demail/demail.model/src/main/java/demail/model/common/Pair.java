/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.common;

import java.io.Serializable;

/**
 *
 * @author Анна
 */
public interface Pair extends Serializable {
    
    public Long getFirst();
    
    public void setFirst(Long k);
    
    public Long getSecond();
    
    public void setSecond(Long t);
    
}
