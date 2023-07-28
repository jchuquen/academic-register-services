package co.grow.plan.academic.register.exceptions;

public class ApiConflictException extends ApiException{
    public ApiConflictException(ApiError apiError) {
        super(apiError);
    }
}
