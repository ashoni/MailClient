/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.common;

/**
 *
 * @author Анна
 */
public class PairImpl implements Pair {
    private Long loginId;
    private Long letterId;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.loginId != null ? this.loginId.hashCode() : 0);
        hash = 59 * hash + (this.letterId != null ? this.letterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PairImpl other = (PairImpl) obj;
        if (this.loginId != other.loginId && (this.loginId == null || !this.loginId.equals(other.loginId))) {
            return false;
        }
        if (this.letterId != other.letterId && (this.letterId == null || !this.letterId.equals(other.letterId))) {
            return false;
        }
        return true;
    }
    
    private static final long serialVersionUID = 1L;

    public PairImpl(Long loginId, Long letterId) {
        this.loginId = loginId;
        this.letterId = letterId;
    }

    public PairImpl() {
    }

    public Long getFirst() {
        return loginId;
    }

    public void setFirst(Long k) {
        loginId = k;
    }

    public Long getSecond() {
        return letterId; 
    }

    public void setSecond(Long t) {
        letterId = t; 
    }
}
