package co.grow.plan.academic.register.application.shared.exceptions;

public final class ApiNoEntityException extends ApiException {
    public ApiNoEntityException(ApiError apiError) {
        super(apiError);
    }
}
