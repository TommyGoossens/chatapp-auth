package com.tommy.authentication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class MicroserviceRegisterParticipant {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    public MicroserviceRegisterParticipant userToMSRegisterParticipant(User user){
        MicroserviceRegisterParticipant newUser = new MicroserviceRegisterParticipant();
        newUser.firstName = user.getFirstName();
        newUser.lastName = user.getLastName();
        newUser.email = user.getEmail();
        return newUser;
    }


}
