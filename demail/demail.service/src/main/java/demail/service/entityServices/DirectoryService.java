/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.service.entityServices;

import demail.model.common.AddressInfo;
import demail.model.common.DirStates;
import demail.model.common.Directory;
import demail.model.dao.DirectoryDAO;
import demail.model.dao.LetterMetaInfoDAO;
import demail.model.entityImpl.DirectoryEntity;
import demail.model.entityImpl.LetterMetaInfoEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Анна
 */
@Service
public class DirectoryService {
  
    @Autowired 
    private DirectoryDAO directoryDAO;
    
    @Autowired 
    private LetterMetaInfoDAO letterMetaInfoDAO;
    
    @Transactional
    public boolean createDirectory(AddressInfo addr, String name) {
        Directory dir = directoryDAO.findDirectoryByName(name, addr.getId());
        if (dir == null) {
            DirectoryEntity dirEntity = new DirectoryEntity(
                        addr.getId(), 
                        name, 
                        DirStates.USER
                    );  
            directoryDAO.create(dirEntity);
            return true;
        } else {
            return false;
        }
    }
    
    @Transactional
    public Boolean deleteDirectory(AddressInfo addr, Directory dir) {
        DirectoryEntity dirEntity = directoryDAO.get(dir.getId());
        if (dirEntity.getDirState() == DirStates.SYSTEM) {
            return false;
        } else {
            directoryDAO.delete(dirEntity.getId());
            return true;
        }
    }    

    @Transactional
    public void renameDirectory(Directory dir, String newName) {
        DirectoryEntity dirEntity = new DirectoryEntity(
                    dir.getLoginId(), 
                    newName, 
                    dir.getDirState()
                );
        directoryDAO.update(dirEntity);
    }        
    
    public List<DirectoryEntity> getDirectoryList(AddressInfo addr) {
        System.out.println(addr.getId());
        return directoryDAO.getDirectoryList(addr.getId());
    }
    
    public List<LetterMetaInfoEntity> getDirectoryContent(Directory dir) {
        List<LetterMetaInfoEntity> lst = null;
        lst = letterMetaInfoDAO.getLettersFromDir(dir.getId());
        if (lst == null) {
            System.out.println("null");
            return null;
        }
        return letterMetaInfoDAO.getLettersFromDir(dir.getId());
    }
}
