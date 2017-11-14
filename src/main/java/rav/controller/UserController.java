package rav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rav.service.UserService;
import rav.to.UserTO;
import rav.util.Constants;

import java.text.ParseException;

@Controller
@RequestMapping(Constants.USERS_PATH)
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody UserTO user) {
        try {
            userService.createUser(user);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserTO user) {
        try {
            userService.updateUser(user);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
    }
}
