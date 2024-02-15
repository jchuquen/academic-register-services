package co.grow.plan.academic.register.application.shared.exceptions;

public final class ApiMissingInformationException extends ApiException {
    public ApiMissingInformationException(ApiError apiError) {
        super(apiError);
    }
}
