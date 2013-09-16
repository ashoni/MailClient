/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.web.controller;

import demail.model.entityImpl.UserInfoEntity;
import demail.service.entityServices.InitService;
import demail.service.session.SessionState;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Анна
 */
@Controller
@RequestMapping("/getcontent")
public class ContentController {
    
    @Autowired InitService initService;
    
    @Autowired
    SessionState sessionState;
    
    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model, Principal principal) {
        String name = principal.getName();
        sessionState.setAddressInfo(initService.getAddressInfo(name));
        sessionState.setUserInfo((UserInfoEntity)initService.getUserInfo(sessionState.getAddressInfo().getUserId()));
        sessionState.setStr("lamp");
        sessionState.setDirList(initService.getDirectoryList(sessionState.getAddressInfo().getId()));
        System.out.println(sessionState.getStr()+" was init");
        return "redirect:/mailbox/Inbox";
    }
    
}
