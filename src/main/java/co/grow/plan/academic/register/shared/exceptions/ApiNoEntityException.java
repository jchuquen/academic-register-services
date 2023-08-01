package co.grow.plan.academic.register.shared.exceptions;

public class ApiNoEntityException extends ApiException{
    public ApiNoEntityException(ApiError apiError) {
        super(apiError);
    }
}
