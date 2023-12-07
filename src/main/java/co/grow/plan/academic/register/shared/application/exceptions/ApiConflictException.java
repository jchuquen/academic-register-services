package co.grow.plan.academic.register.shared.application.exceptions;

public final class ApiConflictException extends ApiException {
    public ApiConflictException(ApiError apiError) {
        super(apiError);
    }
}
