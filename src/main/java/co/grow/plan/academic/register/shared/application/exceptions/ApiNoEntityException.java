package co.grow.plan.academic.register.shared.application.exceptions;

public final class ApiNoEntityException extends ApiException {
    public ApiNoEntityException(ApiError apiError) {
        super(apiError);
    }
}
