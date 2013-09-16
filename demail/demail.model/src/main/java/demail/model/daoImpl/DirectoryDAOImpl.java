/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.daoImpl;

import demail.model.common.Directory;
import demail.model.dao.DirectoryDAO;
import demail.model.entityImpl.DirectoryEntity;
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

@Repository("directoryDAO")
public class DirectoryDAOImpl implements DirectoryDAO{
        
    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void create(DirectoryEntity persistent) throws DataAccessException {
        em.persist(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void update(DirectoryEntity persistent) throws DataAccessException {
        em.merge(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void delete(Long id) throws DataAccessException {
        DirectoryEntity login = em.find(DirectoryEntity.class, id);
        em.remove(login);
    }

    @Override
    public DirectoryEntity get(Long id) throws DataAccessException {
        return em.find(DirectoryEntity.class, id);
    }

    @Override
    public DirectoryEntity findDirectoryByName(String name, Long loginId) {
        List<DirectoryEntity> dirList = em.createQuery("from DirectoryEntity where name = :name and loginId = :loginId", 
                DirectoryEntity.class)
                    .setParameter("name", name)
                    .setParameter("loginId", loginId)
                    .getResultList();
        return (dirList.isEmpty()? null : dirList.get(0));
    }

    @Override
    public List<DirectoryEntity> getDirectoryList(Long loginId) {
        List<DirectoryEntity> dirList = em.createQuery("from DirectoryEntity where loginId = :loginId", 
                DirectoryEntity.class)
                    .setParameter("loginId", loginId)
                    .getResultList();
        return (dirList.isEmpty()? null : dirList);
    }
}
