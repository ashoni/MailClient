/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.dao;

import demail.model.entityImpl.DirectoryEntity;
import java.util.List;

/**
 *
 * @author Анна
 */
public interface DirectoryDAO  extends DAO <Long, DirectoryEntity> {
    
    public DirectoryEntity findDirectoryByName(String name, Long loginId);

    public List<DirectoryEntity> getDirectoryList(Long loginId);
        
}
