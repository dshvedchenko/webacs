package org.shved.webacs.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.shved.webacs.dto.ValidationErrorDTO;
import org.shved.webacs.exception.*;
import org.shved.webacs.response.Error;
import org.shved.webacs.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * @author dshvedchenko on 6/21/16.
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    private MessageSource messageSource;

    @Autowired
    public ExceptionController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ResponseEntity<Object> processValidationError( MethodArgumentNotValidException ex, WebRequest request) {
//        BindingResult result = ex.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return handleExceptionInternal(ex, processFieldErrors(fieldErrors) ,headers,HttpStatus.UNAUTHORIZED, request);
//    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "internal server error")
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseData handleAppException(AppException error, WebRequest request) {
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        rd.setError(err);
        return rd;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "token not found")
    @ExceptionHandler(TokenException.class)
    @ResponseBody
    public ResponseData handleTokenException(TokenException error) {
        logger.info("Token Not found occured: " + error);
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        err.setStatus(401);
        rd.setError(err);
        return rd;
    }


    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "information not found")
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseData handleTokenException(NotFoundException error, WebRequest request) {
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        rd.setError(err);
        return rd;

    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "user already registered")
    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    public ResponseData handleUserExistsException(UserExistsException error, WebRequest request) {
        ResponseData rd = new ResponseData();
        Error err = new Error();
        err.setMessage(error.getMessage());
        rd.setError(err);
        return rd;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "email already used")
    @ExceptionHandler(EmailExistsException.class)
    @ResponseBody
    public ResponseData handleEmailExistsException(EmailExistsException error, WebRequest request) {
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
