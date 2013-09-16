/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.service.login;

import demail.model.dao.AddressInfoDAO;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Анна
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
    private AddressInfoDAO dao;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException, DataAccessException {

        String password = dao.getByAddress(username).getPassword();
        if (password == null) {
          throw new UsernameNotFoundException("user not found");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
        UserDetails userDetails = new User(username, password, true, true, true, true, authorities);
        return userDetails;
    }
}
