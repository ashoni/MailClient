/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.daoImpl;

import demail.model.dao.UserInfoDAO;
import demail.model.entityImpl.UserInfoEntity;
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
@Repository("userInfoDAO")
public class UserInfoDAOImpl implements UserInfoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void create(UserInfoEntity persistent) throws DataAccessException {
        em.persist(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void update(UserInfoEntity persistent) throws DataAccessException {
        em.merge(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void delete(Long id) throws DataAccessException {
        UserInfoEntity login = em.find(UserInfoEntity.class, id);
        em.remove(login);
    }

    @Override
    public UserInfoEntity get(Long id) throws DataAccessException {
        return em.find(UserInfoEntity.class, id);
    }

    @Override
    public Long findUser(String phone) {
        List<UserInfoEntity> userList = em.createQuery("from UserInfoEntity where phone = :phone", UserInfoEntity.class)
                    .setParameter("phone", phone)
                    .getResultList();
        return (userList.isEmpty()? null : userList.get(0).getId());
    }

}
