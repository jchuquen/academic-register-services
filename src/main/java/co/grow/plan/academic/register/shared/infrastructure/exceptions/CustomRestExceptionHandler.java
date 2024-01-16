package co.grow.plan.academic.register.shared.infrastructure.exceptions;

import co.grow.plan.academic.register.shared.application.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiError;
import co.grow.plan.academic.register.shared.application.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiNoEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@SuppressWarnings("unchecked")
public final class  CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(ex.getLocalizedMessage(), errors);

        return handleExceptionInternal(
                ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        ApiError apiError =
                new ApiError(ex.getLocalizedMessage(), List.of(error));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //TODO: Check if this is useful or not.
    /*@ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {

        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiError apiError =
                new ApiError(ErrorCode.CONFLICT, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        Class<?> requiredType = ex.getRequiredType();
        if (requiredType != null) {
            String error =
                ex.getName() + " should be of type " + requiredType.getName();

            ApiError apiError =
                new ApiError(ex.getLocalizedMessage(), List.of(error));

            return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
            new ApiError("Error handling MethodArgumentTypeMismatch. No requiredType"),
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String error =
                "No handler found for " + ex.getHttpMethod() +
                    " " + ex.getRequestURL();

        ApiError apiError = new ApiError(ex.getLocalizedMessage(), List.of(error));

        return new ResponseEntity<>(apiError, new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");

        Set<HttpMethod> supportedHttpMethods = ex.getSupportedHttpMethods();
        if(supportedHttpMethods != null) {
            ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

            ApiError apiError = new ApiError(ex.getLocalizedMessage(),
                List.of(builder.toString()));

            return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        return new ResponseEntity<>(
            new ApiError("Error handling HttpRequestMethodNotSupported. No supportedHttpMethods"),
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");

        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        ApiError apiError = new ApiError(ex.getLocalizedMessage(),
            List.of(builder.substring(0, builder.length() - 2)));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({ ApiMissingInformationException.class })
    public ResponseEntity<ApiError> handleApiMissingInformationException(
        ApiMissingInformationException ex) {

        return new ResponseEntity<>(ex.getApiError(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ApiBadInformationException.class })
    public ResponseEntity<ApiError> handleApiBadInformationException(
        ApiBadInformationException ex) {

        return new ResponseEntity<>(ex.getApiError(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ApiConflictException.class })
    public ResponseEntity<ApiError> handleApiConflictException(
            ApiConflictException ex) {

        return new ResponseEntity<>(ex.getApiError(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ ApiNoEntityException.class })
    public ResponseEntity<ApiError> handleApiNoEntityException(
            ApiNoEntityException ex) {

        return new ResponseEntity<>(ex.getApiError(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiError> handleAll(Exception ex) {

        ApiError apiError = new ApiError(ex.getLocalizedMessage(),
                List.of("An unexpected error has occurred"));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
