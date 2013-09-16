/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.service.entityServices;

import demail.model.common.AddressInfo;
import demail.model.common.DirStates;
import demail.model.common.UserInfo;
import demail.model.dao.AddressInfoDAO;
import demail.model.dao.DirectoryDAO;
import demail.model.dao.UserInfoDAO;
import demail.model.entityImpl.AddressInfoEntity;
import demail.model.entityImpl.DirectoryEntity;
import demail.model.entityImpl.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Анна
 */

@Service
public class CreateMailService {

    @Autowired 
    private AddressInfoDAO addressInfoDAO;

    @Autowired 
    private UserInfoDAO userInfoDAO;

    @Autowired 
    private DirectoryDAO directoryDAO;

    @Transactional
    public CreationResult createMail(AddressInfo addr, UserInfo user) {
        if (addressInfoDAO.getByAddress(addr.getMailAddress()) != null) {
            return CreationResult.EXISTED;
        }
        Long userId = userInfoDAO.findUser(user.getPhone());
        if (userId == null) {
            UserInfoEntity userInfoEntity = new UserInfoEntity(
                      user.getName(), 
                      user.getSurname(),
                      user.getPhone(), 
                      user.getBirthday()
                    );
            userInfoDAO.create(userInfoEntity);
            userId = userInfoEntity.getId();
        } 

        AddressInfoEntity addrEntity = new AddressInfoEntity(
                  userId, 
                  addr.getMailAddress(), 
                  addr.getReserveMailAddress(), 
                  addr.getCreationDate(), 
                  addr.getPassword()
        );
        addressInfoDAO.create(addrEntity);

        Long loginId = addrEntity.getId();
        DirectoryEntity dirEntityIn = new DirectoryEntity(
                loginId, 
                "Inbox", 
                DirStates.SYSTEM);
        directoryDAO.create(dirEntityIn);

        DirectoryEntity dirEntityOut = new DirectoryEntity(
                loginId, 
                "Sent Mail", 
                DirStates.SYSTEM);
        directoryDAO.create(dirEntityOut);

        DirectoryEntity dirEntitySaved = new DirectoryEntity(
                loginId, 
                "Drafts", 
                DirStates.SYSTEM);
        directoryDAO.create(dirEntitySaved);
        return CreationResult.SUCCESS;
    }

    public AddressInfo getReserved(String mailAddress) {
        AddressInfo addr = addressInfoDAO.getByAddress(mailAddress);
        if (addr == null) {
            return null;
        }
        return addr;
    }
  
}
