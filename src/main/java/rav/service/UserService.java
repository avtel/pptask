package rav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import rav.model.UserDao;
import rav.model.entety.User;
import rav.to.UserTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class UserService {
    private UserDao userDao;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
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

    private User fillUserParams(User user, UserTO userTO) throws ParseException {
        user.setLogin(userTO.getLogin());
        user.setBirthday(formatter.parse(userTO.getBirthday()));
        return user;
    }
}
