package com.PUB_Online.PUB.exceptions.GlobalExceptionHandler;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.PUB_Online.PUB.exceptions.ComandaException;
import com.PUB_Online.PUB.exceptions.DuplicatedValueException;
import com.PUB_Online.PUB.exceptions.HorarioException;
import com.PUB_Online.PUB.exceptions.InvalidCPFException;
import com.PUB_Online.PUB.exceptions.InvalidCredentialsException;
import com.PUB_Online.PUB.exceptions.InvalidEmailException;
import com.PUB_Online.PUB.exceptions.InvalidNumberException;
import com.PUB_Online.PUB.exceptions.InvalidPasswordException;
import com.PUB_Online.PUB.exceptions.InvalidTelefoneException;
import com.PUB_Online.PUB.exceptions.InvalidUsernameException;
import com.PUB_Online.PUB.exceptions.MenuException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.exceptions.PedidoException;
import com.PUB_Online.PUB.exceptions.PermissionException;
import com.PUB_Online.PUB.exceptions.ReservaException;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    @ExceptionHandler(ComandaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleComandaException(
        ComandaException comandaException,
            WebRequest request) {
        final String errorMessage = comandaException.getMessage();
        log.error(errorMessage, comandaException);
        return buildErrorResponse(
                comandaException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(DuplicatedValueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDuplicatedValueException(
            DuplicatedValueException duplicatedValueException,
            WebRequest request) {
        final String errorMessage = duplicatedValueException.getMessage();
        log.error(errorMessage, duplicatedValueException);
        return buildErrorResponse(
                duplicatedValueException,
                errorMessage,
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(HorarioException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ResponseEntity<Object> handleHorarioException(
            HorarioException horarioException,
            WebRequest request) {
        final String errorMessage = horarioException.getMessage();
        log.error(errorMessage, horarioException);
        return buildErrorResponse(
                horarioException,
                errorMessage,
                HttpStatus.LOCKED,
                request);
    }

    @ExceptionHandler(InvalidCPFException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidCPFException(
            InvalidCPFException invalidCPFException,
            WebRequest request) {
        final String errorMessage = invalidCPFException.getMessage();
        log.error(errorMessage, invalidCPFException);
        return buildErrorResponse(
                invalidCPFException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleInvalidCredentialsException(
            InvalidCredentialsException invalidCredentialsException,
            WebRequest request) {
        final String errorMessage = invalidCredentialsException.getMessage();
        log.error(errorMessage, invalidCredentialsException);
        return buildErrorResponse(
                invalidCredentialsException,
                errorMessage,
                HttpStatus.UNAUTHORIZED,
                request);
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidEmailException(
            InvalidEmailException invalidEmailException,
            WebRequest request) {
        final String errorMessage = invalidEmailException.getMessage();
        log.error(errorMessage, invalidEmailException);
        return buildErrorResponse(
                invalidEmailException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(InvalidNumberException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleInvalidNumberException(
            InvalidNumberException invalidNumberException,
            WebRequest request) {
        final String errorMessage = invalidNumberException.getMessage();
        log.error(errorMessage, invalidNumberException);
        return buildErrorResponse(
                invalidNumberException,
                errorMessage,
                HttpStatus.UNPROCESSABLE_ENTITY,
                request);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidPasswordException(
            InvalidPasswordException invalidPasswordException,
            WebRequest request) {
        final String errorMessage = invalidPasswordException.getMessage();
        log.error(errorMessage, invalidPasswordException);
        return buildErrorResponse(
                invalidPasswordException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(InvalidTelefoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidTelefoneException(
            InvalidTelefoneException invalidTelefoneException,
            WebRequest request) {
        final String errorMessage = invalidTelefoneException.getMessage();
        log.error(errorMessage, invalidTelefoneException);
        return buildErrorResponse(
                invalidTelefoneException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidUsernameException(
            InvalidUsernameException invalidUsernameException,
            WebRequest request) {
        final String errorMessage = invalidUsernameException.getMessage();
        log.error(errorMessage, invalidUsernameException);
        return buildErrorResponse(
                invalidUsernameException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(MenuException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMenuException(
            MenuException menuException,
            WebRequest request) {
        final String errorMessage = menuException.getMessage();
        log.error(errorMessage, menuException);
        return buildErrorResponse(
                menuException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleObjectNotFoundException(
            ObjectNotFoundException objectNotFoundException,
            WebRequest request) {
        final String errorMessage = objectNotFoundException.getMessage();
        log.error(errorMessage, objectNotFoundException);
        return buildErrorResponse(
                objectNotFoundException,
                errorMessage,
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(PedidoException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ResponseEntity<Object> handlePedidoException(
            PedidoException pedidoException,
            WebRequest request) {
        final String errorMessage = pedidoException.getMessage();
        log.error(errorMessage, pedidoException);
        return buildErrorResponse(
                pedidoException,
                errorMessage,
                HttpStatus.LOCKED,
                request);
    }

    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handlePermissionException(
            PermissionException permissionException,
            WebRequest request) {
        final String errorMessage = permissionException.getMessage();
        log.error(errorMessage, permissionException);
        return buildErrorResponse(
                permissionException,
                errorMessage,
                HttpStatus.FORBIDDEN,
                request);
    }

    @ExceptionHandler(ReservaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleReservaException(
            ReservaException reservaException,
            WebRequest request) {
        final String errorMessage = reservaException.getMessage();
        log.error(errorMessage, reservaException);
        return buildErrorResponse(
                reservaException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request);
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (this.printStackTrace) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
