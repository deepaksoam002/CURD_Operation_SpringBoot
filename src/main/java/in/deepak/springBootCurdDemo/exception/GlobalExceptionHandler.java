package in.deepak.springBootCurdDemo.exception;

import in.deepak.springBootCurdDemo.dto.ExceptionResponseDto;
import in.deepak.springBootCurdDemo.dto.ValidationExceptionResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest servletRequest){

        Map<String,String> validationErrorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> validationErrorMap.put(
                        error.getField(),error.getDefaultMessage()));

        ValidationExceptionResponseDto methodArgumentNotValidException = new ValidationExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Input field error",
                servletRequest.getRequestURI(),
                validationErrorMap
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(methodArgumentNotValidException);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest servletRequest){

        ExceptionResponseDto resourceNotFoundExceptionHandler = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                servletRequest.getRequestURI(),
                ex.getMessage()
        );

        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body(resourceNotFoundExceptionHandler);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionResponseDto> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest servletRequest){

        ExceptionResponseDto duplicateResourceExceptionHandler = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                servletRequest.getRequestURI(),
                ex.getMessage()
        );

        return ResponseEntity.status( HttpStatus.CONFLICT ).body(duplicateResourceExceptionHandler);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleGenricException(Exception ex, HttpServletRequest servletRequest){

        ExceptionResponseDto genricExceptionHandler = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                servletRequest.getRequestURI(),
                ex.getMessage()
        );

        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body(genricExceptionHandler);

    }
}
