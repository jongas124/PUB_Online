package com.PUB_Online.PUB.exceptions.GlobalExceptionHandler;

import java.net.http.HttpHeaders;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

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

public class GlobalExceptionHandler {
    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    // @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details.");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception exception,
            WebRequest request) {
        final String errorMessage = "Unknown error occurred";
        // log.error(errorMessage, exception);
        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(DuplicatedValueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDuplicatedValueException(
            DuplicatedValueException duplicatedValueException,
            WebRequest request) {
        String errorMessage = "Não são permitidos valores duplicados";
        // log.error(errorMessage, duplicatedValueException)
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
        String errorMessage = "Fora do horário de funcionamento";
        // log.error(errorMessage, horarioException)
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
        String errorMessage = "CPF inválido";
        // log.error(errorMessage, invalidCPFException)
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
        String errorMessage = "Credenciais inválidas";
        // log.error(errorMessage, invalidCredentialsException)
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
        String errorMessage = "E-mail inválido";
        // log.error(errorMessage, invalidEmailException)
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
        String errorMessage = "Número inválido";
        // log.error(errorMessage, invalidNumberException)
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
        String errorMessage = "Senha inválida";
        // log.error(errorMessage, invalidPasswordException)
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
        String errorMessage = "Telefone inválido";
        // log.error(errorMessage, invalidTelefoneException)
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
        String errorMessage = "Username inválido";
        // log.error(errorMessage, invalidUsernameException)
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
        String errorMessage = "Produto indisponível";
        // log.error(errorMessage, menuException)
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
        String errorMessage = "Não foi encontrado";
        // log.error(errorMessage, objectNotFoundException)
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
        String errorMessage = "Não é possível deletar um pedido sendo preparado ou concluido";
        // log.error(errorMessage, pedidoException)
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
        String errorMessage = "Sem permissão para executar esta ação";
        // log.error(errorMessage, permissionException)
        return buildErrorResponse(
                permissionException,
                errorMessage,
                HttpStatus.FORBIDDEN,
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
