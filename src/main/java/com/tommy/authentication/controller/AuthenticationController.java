package com.tommy.authentication.controller;


import com.tommy.authentication.model.AuthenticationParticipant;
import com.tommy.authentication.model.RegisterParticipant;
import com.tommy.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class AuthenticationController {

    private AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterParticipant participant) {
        return service.register(participant);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationParticipant participant) {
        return service.login(participant);
    }
}
