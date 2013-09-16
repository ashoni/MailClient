/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.service.entityServices;

import demail.model.common.AddressInfo;
import demail.model.common.UserInfo;
import demail.model.dao.AddressInfoDAO;
import demail.model.dao.DirectoryDAO;
import demail.model.dao.UserInfoDAO;
import demail.model.entityImpl.DirectoryEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Анна
 */
@Service
public class InitService {
        
    @Autowired
    AddressInfoDAO addressInfoDAO;
    
    @Autowired
    UserInfoDAO userInfoDAO;
    
    @Autowired
    DirectoryDAO directoryDAO;
    
    public AddressInfo getAddressInfo(String mailAddress) {
        return addressInfoDAO.getByAddress(mailAddress);
    }
    
    public UserInfo getUserInfo(Long userId) {
        return userInfoDAO.get(userId);
    }
    
    public List<DirectoryEntity> getDirectoryList(Long loginId) {
        return directoryDAO.getDirectoryList(loginId);
    }
}
