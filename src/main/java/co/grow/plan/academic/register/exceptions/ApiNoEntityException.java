package co.grow.plan.academic.register.exceptions;

public class ApiNoEntityException extends ApiException{
    public ApiNoEntityException(ApiError apiError) {
        super(apiError);
    }
}
