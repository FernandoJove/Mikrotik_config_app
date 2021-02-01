package com.beta1.mikrotik;

public class User {
    private String nomuser;
    private String email;
    private String password;

    public User(){

    }
    public User(String nomuser, String email, String password) {
        this.nomuser = nomuser;
        this.email = email;
        this.password = password;
    }
}
