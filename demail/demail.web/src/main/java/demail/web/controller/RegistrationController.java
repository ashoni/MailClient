package demail.web.controller;

import demail.web.modelImpl.AddressInfoImpl;
import demail.web.modelImpl.UserInfoImpl;
import demail.service.entityServices.CreateMailService;
import demail.service.entityServices.CreationResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Анна
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {
 
    @Autowired
    private CreateMailService mailService;
    
    @RequestMapping(method = RequestMethod.POST)
    public String createMailBox(@RequestParam("mailAddress") String mailAddress,
                                @RequestParam("password") String password,
                                @RequestParam("name") String name,
                                @RequestParam("surname") String surname,
                                @RequestParam("phone") String phone,
                                @RequestParam("reserveMailAddress") String reserveMailAddress,
                                @RequestParam("dateOfBirth") String dateOfBirth,
                                Model model) {
        System.out.println(model.asMap().keySet());
        AddressInfoImpl addr = new AddressInfoImpl(mailAddress, reserveMailAddress, password);
        UserInfoImpl user = new UserInfoImpl(name, surname, phone, new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            user.setBirthday(df.parse(dateOfBirth));
        } catch(Exception e) {
            user.setBirthday(new Date());
        }
        CreationResult result = mailService.createMail(addr, user);
        if (result == CreationResult.SUCCESS) {
            return "redirect:/login";
        } else if (result == CreationResult.EXISTED) {
            model.addAttribute("repeat", "true");
            return "/registration";
        } else {
            model.addAttribute("error", "true");
            return "/registration";
        }
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String showRegistration(Model model) {
        model.addAttribute("user", new UserInfoImpl());
        model.addAttribute("addr", new AddressInfoImpl());
        return "registration";
    }
}
