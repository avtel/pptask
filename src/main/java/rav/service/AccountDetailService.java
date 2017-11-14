package rav.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rav.model.AccountDao;
import rav.model.entety.Account;
import rav.util.Constants;

import java.util.Collection;
import java.util.Collections;

@Service
public class AccountDetailService implements UserDetailsService {
    private AccountDao accountDao;

    @Autowired
    public AccountDetailService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return convertToUserDetails(accountDao.findByUsername(s));
    }

    private UserDetails convertToUserDetails(Account account) {
        return account == null ? null : new UserWrapper(account);
    }


    private static class UserWrapper implements UserDetails {
        // hardcod is enough for now. Should be replaced by
        private static Collection<? extends GrantedAuthority> simpleAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(Constants.SIMPLE_AUTHORITY));
        private static Collection<? extends GrantedAuthority> adminAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(Constants.ADMIN_AUTHORITY));
        @Getter
        private String username;
        @Getter
        private String password;

        public UserWrapper(Account account) {
            this.username = account.getUsername();
            this.password = account.getPassword();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return username.equals(Constants.ADMIN_LOGIN) ? adminAuthorities : simpleAuthorities;
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
