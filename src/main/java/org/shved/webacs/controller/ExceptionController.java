package org.shved.webacs.controller;

import org.shved.webacs.dto.ValidationErrorDTO;
import org.shved.webacs.exception.*;
import org.shved.webacs.response.Error;
import org.shved.webacs.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * @author dshvedchenko on 6/21/16.
 */
@ControllerAdvice
public class ExceptionController {

    private MessageSource messageSource;

    @Autowired
    public ExceptionController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

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

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "information not found")
    @ExceptionHandler({TokenException.class, NotFoundException.class})
    @ResponseBody
    public ResponseData handleTokenException(AppException error) {
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


    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }

}
