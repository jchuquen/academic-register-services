package co.grow.plan.academic.register.shared.application.exceptions;

public final class ApiMissingInformationException extends ApiException {
    public ApiMissingInformationException(ApiError apiError) {
        super(apiError);
    }
}
