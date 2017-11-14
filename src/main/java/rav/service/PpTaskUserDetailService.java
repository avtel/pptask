package rav.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rav.model.UserDao;
import rav.model.entety.User;
import rav.util.Constants;

import java.util.Collection;
import java.util.Collections;

@Service
public class PpTaskUserDetailService implements UserDetailsService{
    private UserDao userDao;

    @Autowired
    public PpTaskUserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return convertToUserDetails(userDao.findByLogin(s));
    }

    private UserDetails convertToUserDetails(User user) {
        return user == null ? null : new UserWrapper(user);
    }

    private static class UserWrapper implements UserDetails{
        private static Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(Constants.SIMPLE_AUTHORITY));
        @Getter
        private String username;
        @Getter
        private String password;

        public UserWrapper(User user) {
            this.username = user.getLogin();
            this.password = user.getPassword();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
