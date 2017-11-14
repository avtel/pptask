package rav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rav.model.UserDao;
import rav.model.entety.User;
import rav.to.UserTO;
import rav.util.Constants;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService {
    private final SimpleDateFormat formatter = new SimpleDateFormat(Constants.BIRTHDAY_FORMAT);
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserTO getUser(String login) {
        User user = userDao.findByLogin(login);
        return user == null ? null : getDto(user);
    }

    public void createUser(UserTO newUser) throws ParseException {
        User user = userDao.findByLogin(newUser.getLogin());
        if (user == null) {
            user = fillUserParams(new User(), newUser);
            userDao.save(user);
        } else {
            throw new RuntimeException();
        }
    }

    public void updateUser(UserTO user) throws ParseException {
        User existingUser = userDao.findByLogin(user.getLogin());
        if (existingUser == null) {
            throw new RuntimeException();
        } else {
            userDao.save(fillUserParams(existingUser, user));
        }
    }

    public void deleteUser(String login) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            userDao.delete(user);
        }
    }

    @PostConstruct
    private void createAdminIfNotExist() {
        User admin = userDao.findByLogin(Constants.ADMIN_LOGIN);
        if (admin == null) {
            admin = new User();
            admin.setLogin(Constants.ADMIN_LOGIN);
            admin.setPassword(Constants.ADMIN_DEFAULT_PASSWORD);
            userDao.save(admin);
        }
    }

    private User fillUserParams(User user, UserTO userTO) throws ParseException {
        user.setLogin(userTO.getLogin());
        user.setBirthday(birthdayToDate(userTO.getBirthday()));
        user.setPassword(userTO.getPassword());
        return user;
    }

    private UserTO getDto(User user) {
        return new UserTO(
                user.getLogin(),
                birthdayToString(user.getBirthday()),
                "*****"
        );
    }

    private String birthdayToString(Date birthday) {
        return birthday == null ? null : formatter.format(birthday);
    }

    private Date birthdayToDate(String birthday) {
        try {
            return StringUtils.isEmpty(birthday) ? null : formatter.parse(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
