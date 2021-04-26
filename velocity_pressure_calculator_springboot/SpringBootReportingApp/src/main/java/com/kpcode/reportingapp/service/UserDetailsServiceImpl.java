package com.kpcode.reportingapp.service;

/**
 * @Author kaveri
 * @create 11/04/21
 */
import com.kpcode.reportingapp.model.MyUserDetails;
import com.kpcode.reportingapp.model.User;
import com.kpcode.reportingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * match username and password to check user is already registered or not
     * also get authorities of user
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        if(user.isAdmin())
        {
            user.setRole("ADMIN");
        }else{
            user.setRole("VIEWER");
        }
        return new MyUserDetails(user);
    }

}
