package rav.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rav.util.Constants;

@Controller
@RequestMapping(Constants.HELLO_PATH)
public class HelloController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String sayHello(@AuthenticationPrincipal final UserDetails user) {
        return "Hello, " + user.getUsername() + "!";
    }
}
