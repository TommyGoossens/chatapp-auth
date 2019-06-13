package com.tommy.authentication.controller;


import com.tommy.authentication.model.AuthenticationParticipant;
import com.tommy.authentication.model.JwtResponse;
import com.tommy.authentication.model.RegisterParticipant;
import com.tommy.authentication.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(tags = "Authentication")
public class AuthenticationController {

    private AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    /**
     * Processes the sign in request and returns a JWT if successful
     * @param participant containing the credentials
     * @return JwtResponse object
     */
    @ApiOperation(value = "Sign in", tags = "Authentication")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "[User signed in]", response = JwtResponse.class),
            @ApiResponse(code = 404, message = "[Email does not exist in database]"),
            @ApiResponse(code = 422, message = "[Incorrect password]")
    })
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationParticipant participant) {
        return service.signIn(participant);
    }

    /**
     * Registers a new AuthenticationParticipant object to the database
     * @param participant containing al necessary information to signup
     * @return 200 when accepted
     */
    @ApiOperation(value = "Sign up", tags = "Authentication")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "[User is registered]"),
            @ApiResponse(code = 409, message = "[Duplicate entity]")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterParticipant participant) {
        return service.signUp(participant);
    }
}
