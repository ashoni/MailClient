/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.web.controller;

import demail.model.common.AddressInfo;
import demail.service.entityServices.CreateMailService;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Анна
 */
@Controller
public class GmailController {

    @Autowired
    CreateMailService mailService;
    
    @RequestMapping(value="/sendpass", method = RequestMethod.GET)
    public String show(ModelMap model) {
        return "sendpass";
    }
    
    @RequestMapping(value="/sendpass", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        Model model) {
        String SMTP_AUTH_USER = "luminescenta@gmail.com"; 
        String SMTP_AUTH_PWD = "ltdjxrjlheu";
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_AUTH_USER);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.sendpartial", "true");
        
        AddressInfo addr = mailService.getReserved(username);
        if (addr == null) {
            model.addAttribute("error","true");
            return "sendpass";
        }
        
        try {
        Session session = Session.getInstance(props);
        session.setDebug(true);
        Transport transport = session.getTransport();
        transport.connect("smtp.gmail.com", 465, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        MimeMessage message = new MimeMessage(session);
        message.setSubject("DeMail password");
        message.setText(addr.getPassword());
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(addr.getReserveMailAddress()));
        message.setSentDate(new Date());
        message.setFrom(new InternetAddress("Anything"));

        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        model.addAttribute("success", "true");
        } catch (Exception e) {
            model.addAttribute("error", "true");
        }
        return "sendpass";
    }
}
