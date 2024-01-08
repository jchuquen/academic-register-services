package co.grow.plan.academic.register.shared.application.exceptions;

public final class ApiBadInformationException extends ApiException {
    public ApiBadInformationException(ApiError apiError) {
        super(apiError);
    }
}
