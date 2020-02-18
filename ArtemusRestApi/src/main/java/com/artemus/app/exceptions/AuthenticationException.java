/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artemus.app.exceptions;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -284410626462358806L;

    public AuthenticationException(String message) {
        super(message);
    }

}
