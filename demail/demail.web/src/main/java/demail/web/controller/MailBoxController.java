/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.web.controller;

import demail.model.common.Directory;
import demail.model.common.LetterMetaInfo;
import demail.service.entityServices.DirectoryService;
import demail.service.entityServices.LetterService;
import demail.service.session.SessionState;
import java.util.regex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Анна
 */
@Controller
@RequestMapping("/mailbox")
public class MailBoxController {
    
    @Autowired
    SessionState sessionState;
    
    @Autowired
    LetterService letterService;
    
    @Autowired
    DirectoryService directoryService;
    
    @RequestMapping(value = "/{dirName}", method = RequestMethod.GET)
    public String show(@PathVariable("dirName") String dirName, Model model) {
        System.out.println("--------------------------------------"+sessionState.getStr());
        if (sessionState.setDirectory(dirName, null) == true) {
            return "mailbox";
        } else {
            return "redirect:/mailbox/Inbox";
        }
    }
     
    @RequestMapping(value = "/{dirName}/{letterId}", method = RequestMethod.GET)
    public String showLetter(@PathVariable("dirName") String dirName, 
                       @PathVariable("letterId") Long letterId,
                       Model model) {
        System.out.println("--------------------------------------"+sessionState.getStr());
        if (sessionState.setDirectory(dirName, letterId) == false) {
            sessionState.setDirectory("Inbox", null);
        }
        return "mailbox";
    }
    
    @RequestMapping(value = "/moveletter", method = RequestMethod.POST)
    public String moveLetter(@RequestParam("letterId") Long letterId,
                             @RequestParam("toDirId") Long toDirId) {
        System.out.println("--------------------------------------"+sessionState.getStr());
        System.out.println("letterId="+letterId);
        System.out.println("DirId="+toDirId);
        Long fromDirectoryId = sessionState.getDirectory(sessionState.getCurrentDir()).getId();
        LetterMetaInfo letter = sessionState.getLetterMetaInfo(fromDirectoryId,
                                                        letterId);
        System.out.println(letter.getPartnerMailAddress());
        Directory dir = sessionState.getDirectory(toDirId);
        System.out.println(dir.getName());
        letterService.moveLetter(letter, dir);
        return "redirect:/mailbox/"+sessionState.getCurrentDir();
    }

    @RequestMapping(value = "/createdir", method = RequestMethod.POST)
    public String createDir(@RequestParam("dirName") String dirName) {
        directoryService.createDirectory(sessionState.getAddressInfo(), dirName);
        return "redirect:/mailbox/"+sessionState.getCurrentDir();
    }
    
    @RequestMapping(value = "/deletedir", method = RequestMethod.POST)
    public String deleteDir() {
        directoryService.deleteDirectory(sessionState.getAddressInfo(), sessionState.getCurDir());
        return "redirect:/mailbox/Inbox";
    }
    
    @RequestMapping(value = "/createletter", method = RequestMethod.POST)
    public String createLetter(@RequestParam("edit") String edit) {
        System.out.println("------------------IMPORTANT--------------------"+sessionState.getStr());
        System.out.println(edit);
        if (edit.equals("true")) {
            System.out.println("working on true");
            sessionState.setCurText(sessionState.getLetter().getBody());
            sessionState.setCurSubj(sessionState.getLetter().getSubject());
            sessionState.setCurTo(sessionState.getLetterMetaInfo(
                        sessionState.getDirectory("Drafts").getId(),
                        sessionState.getLetter().getId()
                    ).getPartnerMailAddress());
            sessionState.setUpdLetter(sessionState.getLetter().getId());
            sessionState.setDirectory("Drafts", null);
        } else {
            System.out.println("working on false");
            sessionState.setCurText(null);
            sessionState.setCurSubj(null);
            if (edit.equals("ans")) {
                System.out.println("-!!!!!!!!!!!!ans");
                sessionState.setCurTo(sessionState.getLetterMetaInfo(
                        sessionState.getCurDir().getId(),
                        sessionState.getLetter().getId()
                    ).getPartnerMailAddress());
            } else {
                sessionState.setCurTo(null);
            }
            sessionState.setDirectory("Drafts", null);
            sessionState.setUpdLetter(null);
        }
        return "createletter";
    }

    @RequestMapping(value = "/createletter", method = RequestMethod.GET)
    public String createLetter() {
        System.out.println("--------------------------------------"+sessionState.getStr());
            sessionState.setCurText(null);
            sessionState.setCurSubj(null);
            sessionState.setCurTo(null);
            sessionState.setDirectory("Drafts", null);
            sessionState.setUpdLetter(null);
        return "createletter";
    }    
    
    @RequestMapping(value = "/saveletter", method = RequestMethod.POST)
    public String saveLetter(@RequestParam("subject") String subject,
                             @RequestParam("to") String to,
                             @RequestParam("letterText") String letterText) {
        sessionState.setErrorText(null);
        sessionState.setCurText(letterText);
        sessionState.setCurSubj(subject);
        sessionState.setCurTo(to);
        System.out.println("enter saveletter");
        System.out.println(letterText+" "+to+" "+subject);
        if (sessionState.getUpdLetter() == null) {
            Long id = letterService.saveLetter(letterText, subject, to, sessionState.getDirectory("Drafts"));
            sessionState.setUpdLetter(id);
        } else {
            letterService.updateLetter(letterText, subject, to, 
                    sessionState.getAddressInfo().getId(), 
                    sessionState.getUpdLetter());
        }
        return "createletter";
    }
    
    @RequestMapping(value = "/sendletter", method = RequestMethod.POST)
    public String sendLetter(@RequestParam("subject") String subject,
                             @RequestParam("to") String to,
                             @RequestParam("letterText") String letterText,
                             Model model) {
        sessionState.setErrorText(null);
        sessionState.setCurText(letterText);
        sessionState.setCurSubj(subject);
        sessionState.setCurTo(to);
        Pattern pattern = Pattern.compile("(\\w+)@demail\\.com");
        Matcher matcher = pattern.matcher(to);
        String actualTo = null;
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            actualTo = matcher.group(1);
        } else {
            return "createletter";
        }
        
        if (sessionState.getUpdLetter() == null) {
            Long id = letterService.saveLetter(letterText, subject, to, sessionState.getDirectory("Drafts"));
            sessionState.setUpdLetter(id);
        } else {
            letterService.updateLetter(letterText, subject, to, 
                    sessionState.getAddressInfo().getId(), 
                    sessionState.getUpdLetter());
        }
        //
        //Long letterId = letterService.saveLetter(letterText, subject, to, sessionState.getDirectory("Drafts"));
        if (actualTo == null) {
            model.addAttribute("error", "true");
            sessionState.setErrorText("Wrong mail format");
            return "createletter";
        }
        if (actualTo.equals(sessionState.getAddressInfo().getMailAddress())) {
            model.addAttribute("error", "true");
            sessionState.setErrorText("You cannot send a letter to yourself");
            return "createletter";
        }
        if (letterService.sendLetter(sessionState.getUpdLetter(), sessionState.getAddressInfo(), actualTo)) {
            return "redirect:/mailbox/Sent%20Mail";
        } else {
            model.addAttribute("error", "true");
            sessionState.setErrorText(to+" doesn't exist. Letter was saved to Drafts");
            return "createletter";
        }
    }
    
    @RequestMapping(value = "/{dirName}/{letterId}/deleteletter", method = RequestMethod.POST)
    public String deleteLetter(@PathVariable("dirName") String dirName, 
                       @PathVariable("letterId") Long letterId) {
        System.out.println("Check: "+sessionState.getLetterMetaInfo(
                sessionState.getDirectory(dirName).getId(), 
                letterId).getPartnerMailAddress());
        letterService.deleteLetter(sessionState.getLetterMetaInfo(
                sessionState.getDirectory(dirName).getId(), 
                letterId));
        return "redirect:/mailbox/{dirName}";
    }    
}
