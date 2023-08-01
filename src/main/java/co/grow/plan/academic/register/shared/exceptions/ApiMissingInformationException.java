package co.grow.plan.academic.register.shared.exceptions;

public class ApiMissingInformationException extends ApiException{
    public ApiMissingInformationException(ApiError apiError) {
        super(apiError);
    }
}
