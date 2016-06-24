package org.shved.webacs.controller;

import org.shved.webacs.exception.AppException;
import org.shved.webacs.exception.EmailExistsException;
import org.shved.webacs.exception.TokenException;
import org.shved.webacs.exception.UserExistsException;
import org.shved.webacs.response.Error;
import org.shved.webacs.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/21/16.
 */
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "internal server error")
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseData handleAppException(AppException error) {
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        rd.setError(err);
        return rd;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "token unauthroized")
    @ExceptionHandler(TokenException.class)
    @ResponseBody
    public ResponseData handleTokenException(TokenException error) {
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        rd.setError(err);
        return rd;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "user already registered")
    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    public ResponseData handleUserExistsException(UserExistsException error) {
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        rd.setError(err);
        return rd;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "email already used")
    @ExceptionHandler(EmailExistsException.class)
    @ResponseBody
    public ResponseData handleEmailExistsException(EmailExistsException error) {
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        rd.setError(err);
        return rd;
    }

}
