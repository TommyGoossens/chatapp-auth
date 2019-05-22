package com.tommy.authentication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterParticipant {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6,max = 256)
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;
}
