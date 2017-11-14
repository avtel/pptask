package rav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rav.model.AccountDao;
import rav.model.entety.Account;
import rav.to.AccountDto;
import rav.util.Constants;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AccountManager {
    private final SimpleDateFormat formatter = new SimpleDateFormat(Constants.BIRTHDAY_FORMAT);
    private AccountDao accountDao;

    @Autowired
    public AccountManager(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDto getUser(String login) {
        Account account = accountDao.findByUsername(login);
        return account == null ? null : getDto(account);
    }

    public void createUser(AccountDto newUser) throws ParseException {
        Account account = accountDao.findByUsername(newUser.getLogin());
        if (account == null) {
            account = fillUserParams(new Account(), newUser);
            accountDao.save(account);
        } else {
            throw new RuntimeException();
        }
    }

    public void updateUser(AccountDto user) throws ParseException {
        Account existingAccount = accountDao.findByUsername(user.getLogin());
        if (existingAccount == null) {
            throw new RuntimeException();
        } else {
            accountDao.save(fillUserParams(existingAccount, user));
        }
    }

    public void deleteUser(String login) {
        Account account = accountDao.findByUsername(login);
        if (account != null) {
            accountDao.delete(account);
        }
    }

    @PostConstruct
    private void createAdminIfNotExist() {
        Account admin = accountDao.findByUsername(Constants.ADMIN_LOGIN);
        if (admin == null) {
            admin = new Account();
            admin.setUsername(Constants.ADMIN_LOGIN);
            admin.setPassword(Constants.ADMIN_DEFAULT_PASSWORD);
            accountDao.save(admin);
        }
    }

    private Account fillUserParams(Account account, AccountDto accountDto) throws ParseException {
        account.setUsername(accountDto.getLogin());
        account.setBirthday(birthdayToDate(accountDto.getBirthday()));
        account.setPassword(accountDto.getPassword());
        return account;
    }

    private AccountDto getDto(Account account) {
        return new AccountDto(
                account.getUsername(),
                birthdayToString(account.getBirthday()),
                "*****"
        );
    }

    private String birthdayToString(Date birthday) {
        return birthday == null ? "unknown" : formatter.format(birthday);
    }

    private Date birthdayToDate(String birthday) {
        try {
            return StringUtils.isEmpty(birthday) ? null : formatter.parse(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
