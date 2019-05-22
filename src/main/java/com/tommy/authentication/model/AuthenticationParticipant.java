package com.tommy.authentication.model;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationParticipant {

    @NotBlank
//    @ApiModelProperty(position = 0)
    private String email;

    @NotBlank
//    @ApiModelProperty(position = 1)
    @Size(min = 6, max = 256)
    private String password;

    public AuthenticationParticipant(@NotBlank String email, @NotBlank @Size(min = 6, max = 256) String password) {
        this.email = email;
        this.password = password;
    }
}
