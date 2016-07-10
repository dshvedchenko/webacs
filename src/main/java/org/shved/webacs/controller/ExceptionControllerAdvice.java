package org.shved.webacs.controller;

import org.shved.webacs.dto.ValidationErrorDTO;
import org.shved.webacs.exception.*;
import org.shved.webacs.response.Error;
import org.shved.webacs.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * @author dshvedchenko on 6/21/16.
 */
@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAppException(AppException ex, WebRequest request, HttpServletRequest httpServletRequest) {
        return getDefaultErrorResponseEntity(ex, request, httpServletRequest);
    }

    @ExceptionHandler(TokenException.class)
    @ResponseBody
    public ResponseEntity<Object> handleTokenException(TokenException ex, WebRequest request, HttpServletRequest httpServletRequest) {
        logger.info("Token Not found occured: " + ex);
        return getDefaultErrorResponseEntity(ex, request, httpServletRequest);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleTokenException(NotFoundException ex, WebRequest request, HttpServletRequest httpServletRequest) {
        return getDefaultErrorResponseEntity(ex, request, httpServletRequest);
    }


    @ExceptionHandler(UserExistsException.class)
    @ResponseBody
    public ResponseEntity<Object> handleUserExistsException(UserExistsException ex, WebRequest request, HttpServletRequest httpServletRequest) {
        return getDefaultErrorResponseEntity(ex, request, httpServletRequest);
    }

    @ExceptionHandler(EmailExistsException.class)
    @ResponseBody
    public ResponseEntity<Object> handleEmailExistsException(EmailExistsException ex, WebRequest request, HttpServletRequest httpServletRequest) {
        return getDefaultErrorResponseEntity(ex, request, httpServletRequest);
    }

    @ExceptionHandler(OperationIsNotAllowed.class)
    @ResponseBody
    public ResponseEntity<Object> handleOperationIsNotAllowed(OperationIsNotAllowed ex, WebRequest request, HttpServletRequest httpServletRequest) {
        return getDefaultErrorResponseEntity(ex, request, httpServletRequest);
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

    private ResponseEntity<Object> getDefaultErrorResponseEntity(Exception ex, WebRequest request, HttpServletRequest httpServletRequest) {
        ResponseData rd = new ResponseData();
        Error err = new Error(ex, httpServletRequest);
        rd.setError(err);
        final HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, rd, headers, getHttpStatus(ex), request);
    }

    private HttpStatus getHttpStatus(Exception e) {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            return e.getClass().getAnnotation(ResponseStatus.class).value();

        } else {
            return HttpStatus.BAD_REQUEST;

        }
    }

}
