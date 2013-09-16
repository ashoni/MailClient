/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.daoImpl;

import demail.model.dao.LetterDAO;
import demail.model.entityImpl.LetterEntity;
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
@Repository("letterDAO")
public class LetterDAOImpl implements LetterDAO{
        
    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void create(LetterEntity persistent) throws DataAccessException {
        em.persist(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void update(LetterEntity persistent) throws DataAccessException {
        em.merge(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void delete(Long id) throws DataAccessException {
        LetterEntity login = em.find(LetterEntity.class, id);
        em.remove(login);
    }

    @Override
    public LetterEntity get(Long id) throws DataAccessException {
        return em.find(LetterEntity.class, id);
    }
}
