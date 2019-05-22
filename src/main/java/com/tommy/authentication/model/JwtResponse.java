package com.tommy.authentication.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    String jwt;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }
}
