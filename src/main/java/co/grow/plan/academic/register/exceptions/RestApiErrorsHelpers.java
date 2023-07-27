package co.grow.plan.academic.register.exceptions;

import org.springframework.http.HttpStatus;

public class RestApiErrorsHelpers {
    public static HttpStatus convertApiErrorToHttpStatus(ApiError apiError) {
        if (apiError.getErrorCode().getCode().startsWith("ERR-00")){
            return HttpStatus.BAD_REQUEST;
        } else if (apiError.getErrorCode().getCode().startsWith("ERR-01")) {
            return HttpStatus.CONFLICT;
        } else if (apiError.getErrorCode().getCode().startsWith("ERR-99")) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            throw new RuntimeException(
                    String.format("Error code %s no supported for conversion",
                            apiError.getErrorCode().getCode()));
        }
    }
}
