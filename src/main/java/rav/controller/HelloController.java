package rav.controller;

import io.swagger.annotations.Authorization;
import io.swagger.annotations.BasicAuthDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rav.util.Constants;

@Controller
@RequestMapping(Constants.HELLO_PATH)
public class HelloController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String sayHello() {
        return "Hello!";
    }
}
