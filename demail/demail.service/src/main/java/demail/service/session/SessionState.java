/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.service.session;

import demail.model.common.AddressInfo;
import demail.model.common.Directory;
import demail.model.common.Letter;
import demail.model.common.LetterMetaInfo;
import demail.model.common.UserInfo;
import demail.model.entityImpl.DirectoryEntity;
import demail.model.entityImpl.LetterMetaInfoEntity;
import demail.service.entityServices.DirectoryService;
import demail.service.entityServices.LetterService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author Анна
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionState implements Serializable {
    @Autowired
    LetterService letterService;
    
    @Autowired
    DirectoryService directoryService;
    
    private static final long serialVersionUID = 1L;
    private AddressInfo addressInfo;
    private UserInfo userInfo;
    //private List<LetterMetaInfoEntity> lettersStructure;
    //private List<String> subjList;
    //private List<Date> dateList;
    private List<FullLetter> lettersList;
    private List<DirectoryEntity> dirList;
    private Letter letter;
    private String currentDir;
    private Directory curDir;
    private String errorText;
    private String curTo;
    private String curText;
    private String curSubj;
    private Long updLetter;
    
    
    public class FullLetterComparator implements Comparator<FullLetter>{

        @Override
        public int compare(FullLetter l1, FullLetter l2) {
            return (l1.getDate().after(l2.getDate()) ? -1 : (l1.getDate().equals(l2.getDate()) ? 0 : 1));
        }
    } 

    public Long getUpdLetter() {
        return updLetter;
    }

    public void setUpdLetter(Long updLetter) {
        this.updLetter = updLetter;
    }
    
    
    
    public String getCurTo() {
        return curTo;
    }

    public void setCurTo(String curTo) {
        this.curTo = curTo;
    }

    public String getCurText() {
        return curText;
    }

    public void setCurText(String curText) {
        this.curText = curText;
    }

    public String getCurSubj() {
        return curSubj;
    }

    public void setCurSubj(String curSubj) {
        this.curSubj = curSubj;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public Directory getCurDir() {
        return curDir;
    }

    public void setCurDir(Directory curDir) {
        this.curDir = curDir;
    }

    public String getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public List<DirectoryEntity> getDirList() {
        return dirList;
    }

    public void setDirList(List<DirectoryEntity> dirList) {
        this.dirList = dirList;
    }
    
    
    public boolean setDirectory(String dirName, Long letterId) {
        letter = null;
        currentDir = null;
        System.out.println(addressInfo.getPassword());
        dirList = directoryService.getDirectoryList(addressInfo);
        for (DirectoryEntity dir : dirList) {
            if (dir.getName().equals(dirName)) {
                System.out.println("dir found");
                currentDir = dirName;
                curDir = dir;
                /*lettersStructure = null;
                lettersStructure = directoryService.getDirectoryContent(dir);
                subjList = new ArrayList<String>();
                dateList = new ArrayList<Date>();*/

                lettersList = new ArrayList<FullLetter>();
                List<LetterMetaInfoEntity> lettersStruct = directoryService.getDirectoryContent(dir);
                
                if (lettersStruct != null) {
                for (LetterMetaInfoEntity letterMeta : lettersStruct) {
                    //System.out.println("letter id="+letterMeta.getId());
                    Letter temp = letterService.readLetter(letterMeta, 
                            letterMeta.getId().getSecond() == letterId);
                    //System.out.println("letter subj="+temp.getSubject()); 
                    //subjList.add(temp.getSubject());
                    //dateList.add(temp.getCreationDate());
                    lettersList.add(new FullLetter(letterMeta, temp.getCreationDate(), temp.getSubject()));
                    System.out.println("----");
                    if (temp.getId() == letterId) {
                        letter = temp;
                    }
                }
                Collections.sort(lettersList, new FullLetterComparator());
                }
                if (letterId == null || letter != null) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Directory getDirectory(String dirName) {
        for (DirectoryEntity dir : dirList) {
            if (dir.getName().equals(dirName)) {
                return dir;
            }
        }
        return null;
    }    
    
    public Directory getDirectory(Long dirId) {
        for (DirectoryEntity dir : dirList) {
            if (dir.getId() == dirId) {
                return dir;
            }
        }
        return null;
    }
    
    public LetterMetaInfo getLetterMetaInfo(Long dirId, Long letterId) {
        //for (LetterMetaInfoEntity letterMeta : lettersStructure) {
        for (FullLetter fullLetter : lettersList) {
            //if (letterMeta.getDirId() == dirId && letterMeta.getLetterId() == letterId) {
            if (fullLetter.getLetterMetaInfo().getDirId() == dirId && fullLetter.getLetterMetaInfo().getLetterId() == letterId) {
                return fullLetter.getLetterMetaInfo();
            }
        }
        return null;
    }

    public List<FullLetter> getLettersList() {
        return lettersList;
    }

    public void setLettersList(List<FullLetter> lettersList) {
        this.lettersList = lettersList;
    }
    
    /*public List<LetterMetaInfoEntity> getLettersStructure() {
        return lettersStructure;
    }

    public void setLettersStructure(List<LetterMetaInfoEntity> lettersStructure) {
        this.lettersStructure = lettersStructure;
    }

    public List<String> getSubjList() {
        return subjList;
    }

    public void setSubjList(List<String> subjList) {
        this.subjList = subjList;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }
*/
    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }
    
    @PostConstruct
    public void init() {
        //lettersStructure = new ArrayList<LetterMetaInfoEntity>();
        //subjList = new ArrayList<String>();
        //dateList = new ArrayList<Date>();
        lettersList = new ArrayList<FullLetter>();
        dirList = new ArrayList<DirectoryEntity>();
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
