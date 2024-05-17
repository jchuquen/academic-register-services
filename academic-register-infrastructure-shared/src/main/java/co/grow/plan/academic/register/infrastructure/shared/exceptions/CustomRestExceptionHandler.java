package co.grow.plan.academic.register.infrastructure.shared.exceptions;

import co.grow.plan.academic.register.application.shared.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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
@Slf4j
@SuppressWarnings("unchecked")
public final class  CustomRestExceptionHandler extends ResponseEntityExceptionHandler {


    public static final String PARAMETER_IS_MISSING_TEMPLATE = "%s parameter is missing";
    public static final String TYPE_MISMATCH_TEMPLATE = "%s should be of type %s";
    public static final String ARGUMENT_TYPE_MISMATCH_ERROR = "Error handling MethodArgumentTypeMismatch. No requiredType";
    public static final String NO_HANDLER_FOUND_TEMPLATE = "No handler found for %s %s";
    public static final String METHOD_NOT_SUPPORTED_ERROR = " method is not supported for this request. Supported methods are ";
    public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR = "Error handling HttpRequestMethodNotSupported. No supportedHttpMethods";
    public static final String MEDIA_TYPE_NOT_SUPPORTED_ERROR = " media type is not supported. Supported media types are ";
    public static final String AN_UNEXPECTED_ERROR = "An unexpected error has occurred";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {

        log.debug("Handling Method Argument Not Valid");

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
        HttpStatusCode status, WebRequest request
    ) {

        log.debug("Handling Missing Servlet Request Parameter");

        String error =
            String.format(
                PARAMETER_IS_MISSING_TEMPLATE, ex.getParameterName()
            );

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
        MethodArgumentTypeMismatchException ex, WebRequest request
    ) {

        log.debug("Handling Method Argument Type MismatchException");

        Class<?> requiredType = ex.getRequiredType();
        if (requiredType != null) {
            String error =
                String.format(TYPE_MISMATCH_TEMPLATE,
                    ex.getName(),requiredType.getName()
                );

            ApiError apiError =
                new ApiError(ex.getLocalizedMessage(), List.of(error));

            return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
            new ApiError(ARGUMENT_TYPE_MISMATCH_ERROR),
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.debug("Handling No Handler Found Exception");

        String error =
            String.format(NO_HANDLER_FOUND_TEMPLATE,
                ex.getHttpMethod(), ex.getRequestURL());

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

        log.debug("Handling Http Request Method Not Supported");

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(METHOD_NOT_SUPPORTED_ERROR);

        Set<HttpMethod> supportedHttpMethods = ex.getSupportedHttpMethods();
        if(supportedHttpMethods != null) {
            ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

            ApiError apiError = new ApiError(ex.getLocalizedMessage(),
                List.of(builder.toString()));

            return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        return new ResponseEntity<>(
            new ApiError(HTTP_REQUEST_METHOD_NOT_SUPPORTED_ERROR),
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        log.debug("Handling Http Media Type Not Supported");

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(MEDIA_TYPE_NOT_SUPPORTED_ERROR);

        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        ApiError apiError = new ApiError(ex.getLocalizedMessage(),
            List.of(builder.substring(0, builder.length() - 2)));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({ ApiMissingInformationException.class })
    public ResponseEntity<ApiError> handleApiMissingInformationException(
        ApiMissingInformationException ex) {

        log.debug("Handling Api Missing Information Exception");

        return new ResponseEntity<>(ex.getApiError(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ApiBadInformationException.class })
    public ResponseEntity<ApiError> handleApiBadInformationException(
        ApiBadInformationException ex) {

        log.debug("Handling Api Bad Information Exception");

        return new ResponseEntity<>(ex.getApiError(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ApiConflictException.class })
    public ResponseEntity<ApiError> handleApiConflictException(
            ApiConflictException ex) {

        log.debug("Handling Api Conflict Exception");

        return new ResponseEntity<>(ex.getApiError(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ ApiNoEntityException.class })
    public ResponseEntity<ApiError> handleApiNoEntityException(
            ApiNoEntityException ex) {

        log.debug("Handling Api No Entity Exception");

        return new ResponseEntity<>(ex.getApiError(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiError> handleAll(Exception ex) {

        log.debug("Handling General Exception");

        ApiError apiError = new ApiError(ex.getLocalizedMessage(),
                List.of(AN_UNEXPECTED_ERROR));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
