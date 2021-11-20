package com.devsuperior.movieflix.services.exceptions;

public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = -7465573990079765793L;

    public ForbiddenException(String msg){
        super(msg);
    }
}
