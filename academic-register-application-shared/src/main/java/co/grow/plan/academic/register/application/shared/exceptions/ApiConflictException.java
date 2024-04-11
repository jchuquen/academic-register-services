package co.grow.plan.academic.register.application.shared.exceptions;

public final class ApiConflictException extends ApiException {
    public ApiConflictException(ApiError apiError) {
        super(apiError);
    }
}
