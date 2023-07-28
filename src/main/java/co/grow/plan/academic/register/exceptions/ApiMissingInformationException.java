package co.grow.plan.academic.register.exceptions;

public class ApiMissingInformationException extends ApiException{
    public ApiMissingInformationException(ApiError apiError) {
        super(apiError);
    }
}
