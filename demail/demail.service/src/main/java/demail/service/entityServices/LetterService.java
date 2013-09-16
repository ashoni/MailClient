/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.service.entityServices;

import demail.model.common.AddressInfo;
import demail.model.common.Directory;
import demail.model.common.Letter;
import demail.model.common.LetterMetaInfo;
import demail.model.common.LetterStates;
import demail.model.common.PairImpl;
import demail.model.dao.AddressInfoDAO;
import demail.model.dao.DirectoryDAO;
import demail.model.dao.LetterDAO;
import demail.model.dao.LetterMetaInfoDAO;
import demail.model.entityImpl.LetterEntity;
import demail.model.entityImpl.LetterMetaInfoEntity;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Анна
 */
@Service
public class LetterService {
    
    @Autowired 
    LetterDAO letterDAO;
    
    @Autowired 
    LetterMetaInfoDAO letterMetaInfoDAO;
    
    @Autowired
    AddressInfoDAO addressInfoDAO;
    
    @Autowired
    DirectoryDAO directoryDAO;
    
    @Transactional
    public Boolean sendLetter(Long letterId, AddressInfo from, String to) {
        AddressInfo partnerAddress = addressInfoDAO.getByAddress(to);
        if (partnerAddress == null) {
            return false;
        }
        LetterMetaInfo letterMetaInfo = letterMetaInfoDAO.get(new PairImpl(from.getId(), letterId));
        if (letterMetaInfo.getLetterState() == LetterStates.SAVED) {
            letterMetaInfo.setLetterState(LetterStates.SENT);
            letterMetaInfo.setDirId(directoryDAO.findDirectoryByName("Sent Mail", 
                    letterMetaInfo.getLoginId()).getId());
        } else {
            return false;
        }
        System.out.println("changed?");
        LetterMetaInfoEntity toMetaInfo = new LetterMetaInfoEntity(
                    letterMetaInfo.getId().getSecond(),
                    directoryDAO.findDirectoryByName("Inbox", 
                        partnerAddress.getId()).getId(),
                    partnerAddress.getId(),
                    from.getMailAddress()+"@demail.com",
                    LetterStates.RECEIVED
                );
        letterMetaInfoDAO.create(toMetaInfo);
        return true;
    }
    
    @Transactional
    public void moveLetter(LetterMetaInfo letter, Directory dir) {
        System.out.println(letter.getId().getFirst());//login
        System.out.println(letter.getId().getSecond());//letter
        System.out.println(dir.getName());
        LetterMetaInfoEntity letterMetaInfoEntity = letterMetaInfoDAO.get(letter.getId());
/*                new LetterMetaInfoEntity(
                    letter.getId().getSecond(), 
                    dir.getId(), 
                    dir.getLoginId(),
                    letter.getPartnerMailAddress(), 
                    letter.getLetterState()
                );*/
        System.out.println(letterMetaInfoEntity.getPartnerMailAddress());
        letterMetaInfoEntity.setDirId(dir.getId());
        letterMetaInfoDAO.update(letterMetaInfoEntity);
    }
    
    @Transactional
    public Long saveLetter(String body, String subj, String to, Directory dir) {
        System.out.println("into saveletter service");
        LetterEntity letterEntity = new LetterEntity(
                    subj, 
                    body, 
                    new Date()
                );
        letterDAO.create(letterEntity);
        System.out.println("new Letter:"+letterEntity.getId());
        LetterMetaInfoEntity letterMetaInfoEntity = new LetterMetaInfoEntity(
            letterEntity.getId(), 
            dir.getId(), 
            dir.getLoginId(),
            to, 
            LetterStates.SAVED
        );
        System.out.println("new Letter Meta:"+letterMetaInfoEntity.getLetterId());
        letterMetaInfoDAO.create(letterMetaInfoEntity);
        return letterEntity.getId();
    }

    @Transactional
    public boolean updateLetter(String body, String subj, String to, Long loginId, Long letterId) {
        System.out.println("into updletter service");
        System.out.println(loginId+" "+letterId);
        LetterMetaInfoEntity letterMetaInfoEntity = letterMetaInfoDAO.get(new PairImpl(loginId, letterId));
        if (letterMetaInfoEntity == null) {
            System.out.println("not found");
            return false;
        }
        if (letterMetaInfoEntity.getLetterState() != LetterStates.SAVED) {
            System.out.println("not saved");
            return false;
        }        
        letterMetaInfoEntity.setPartnerMailAddress(to);
        letterMetaInfoDAO.update(letterMetaInfoEntity);
        LetterEntity letterEntity = letterDAO.get(letterId);
        letterEntity.setBody(body);
        letterEntity.setCreationDate(new Date());
        letterEntity.setSubject(subj);
        letterDAO.update(letterEntity);
        return true;
    }    
    
    @Transactional
    public void deleteLetter(LetterMetaInfo letter) {
        System.out.println(letter.getId().getFirst());//login
        System.out.println(letter.getId().getSecond());//letter
        LetterMetaInfoEntity letterMetaInfoEntity = letterMetaInfoDAO.get(letter.getId());
        System.out.println(letterMetaInfoEntity.getPartnerMailAddress());
        letterMetaInfoDAO.delete(letterMetaInfoEntity.getId());
    }
    
    @Transactional
    public Letter readLetter(LetterMetaInfo letterMetaInfo, boolean userRead) {
        LetterMetaInfo temp = letterMetaInfoDAO.get(letterMetaInfo.getId());
        if (temp.getLetterState() == LetterStates.RECEIVED && userRead) {
            temp.setLetterState(LetterStates.READ);
        }
        return letterDAO.get(letterMetaInfo.getId().getSecond());
    }
}
