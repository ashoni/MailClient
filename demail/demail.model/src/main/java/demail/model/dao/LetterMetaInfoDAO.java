/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.dao;


import demail.model.entityImpl.LetterMetaInfoEntity;
import demail.model.common.PairImpl;
import java.util.List;

/**
 *
 * @author Анна
 */
public interface LetterMetaInfoDAO extends DAO <PairImpl, LetterMetaInfoEntity> {
    public List<LetterMetaInfoEntity> getLettersFromDir(Long dirId);
}
