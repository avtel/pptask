package rav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rav.service.AccountManager;
import rav.to.AccountDto;
import rav.util.Constants;

import java.text.ParseException;

@Controller
@RequestMapping(Constants.USERS_PATH)
public class AccountController {
    private AccountManager accountManager;

    @Autowired
    public AccountController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @GetMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountDto getUser(@PathVariable String login) {
        return accountManager.getUser(login);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody AccountDto user) {
        try {
            accountManager.createUser(user);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody AccountDto user) {
        try {
            accountManager.updateUser(user);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String login) {
        accountManager.deleteUser(login);
    }
}
