package co.grow.plan.academic.register.application.shared.exceptions;

public final class ApiBadInformationException extends ApiException {
    public ApiBadInformationException(ApiError apiError) {
        super(apiError);
    }
}
