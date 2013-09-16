/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.daoImpl;

import demail.model.common.AddressInfo;
import demail.model.dao.AddressInfoDAO;
import demail.model.entityImpl.AddressInfoEntity;
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
@Repository("addressInfoDAO")
public class AddressInfoDAOImpl implements AddressInfoDAO {
        
    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void create(AddressInfoEntity persistent) throws DataAccessException {
        em.persist(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void update(AddressInfoEntity persistent) throws DataAccessException {
        em.merge(persistent);
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public void delete(Long id) throws DataAccessException {
        AddressInfoEntity login = em.find(AddressInfoEntity.class, id);
        em.remove(login);
    }

    @Override
    public AddressInfoEntity get(Long id) throws DataAccessException {
        return em.find(AddressInfoEntity.class, id);
    }

    @Override
    public AddressInfo getByAddress(String mailAddress) {
        System.out.println("dao with "+mailAddress);
        List<AddressInfoEntity> addrList = em.createQuery("from AddressInfoEntity where mailAddress = :mailAddress", 
                    AddressInfoEntity.class)
                    .setParameter("mailAddress", mailAddress)
                    .getResultList();
        System.out.println("addrList "+addrList);
        if (addrList == null) {
            System.out.println("equals null");
        } else if (addrList.isEmpty()) {
            System.out.println("empty");
        } else {
            System.out.println("not any");
        }
        return (addrList.isEmpty()? null : addrList.get(0));    
    }
}
