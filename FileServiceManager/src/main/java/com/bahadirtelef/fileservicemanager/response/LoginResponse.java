package com.bahadirtelef.fileservicemanager.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;
}
