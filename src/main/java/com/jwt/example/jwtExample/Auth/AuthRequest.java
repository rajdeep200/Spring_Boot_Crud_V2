package com.jwt.example.jwtExample.Auth;

//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;

//@Data
//@RequiredArgsConstructor
//@NoArgsConstructor
public class AuthRequest {

    private String email;
    private String password;

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
