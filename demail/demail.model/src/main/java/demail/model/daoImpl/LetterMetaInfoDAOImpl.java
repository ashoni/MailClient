/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.daoImpl;

import demail.model.dao.LetterMetaInfoDAO;
import demail.model.entityImpl.LetterMetaInfoEntity;
import demail.model.common.PairImpl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Анна
 */
@Repository("letterMetaInfoDAO")
public class LetterMetaInfoDAOImpl implements LetterMetaInfoDAO{
        
    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void create(LetterMetaInfoEntity persistent) throws DataAccessException {
        em.persist(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void update(LetterMetaInfoEntity persistent) throws DataAccessException {
        //System.out.println(get(new PairImpl(10L,1L)).getPartnerMailAddress());
        em.merge(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void delete(PairImpl id) throws DataAccessException {
        System.out.println("lettermeta delete start");
        System.out.println(id.getFirst()+" "+id.getSecond());
        LetterMetaInfoEntity letter = get(new PairImpl(id.getFirst(),id.getSecond()));
        System.out.println(letter.getPartnerMailAddress());
        em.remove(letter);
    }

    @Override
    public LetterMetaInfoEntity get(PairImpl id) throws DataAccessException {
        return em.find(LetterMetaInfoEntity.class, id);
    }
    
    @Override
    public List<LetterMetaInfoEntity> getLettersFromDir(Long dirId) {
        List<LetterMetaInfoEntity> letterMetaInfoList = 
                em.createQuery("from LetterMetaInfoEntity where dirId = :dirId", 
                LetterMetaInfoEntity.class)
            .setParameter("dirId", dirId)
            .getResultList();
        return (letterMetaInfoList.isEmpty()? null : letterMetaInfoList);
    }

}
