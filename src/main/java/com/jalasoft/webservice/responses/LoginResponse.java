package com.jalasoft.webservice.responses;

public class LoginResponse extends Response{
    private String token;
    /**
     * Response Constructor
     *
     * @param name   Status in String Format.
     * @param status Status in Numeric Format.
     * @param detail Message String.
     */
    public LoginResponse(String name, Integer status, String detail, String token) {
        super(name, status, detail);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
