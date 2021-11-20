package com.devsuperior.movieflix.services.exceptions;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 3810104724255910569L;

    public UnauthorizedException(String msg){
        super(msg);
    }
}
