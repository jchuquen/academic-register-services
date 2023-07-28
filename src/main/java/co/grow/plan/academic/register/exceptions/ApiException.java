package co.grow.plan.academic.register.exceptions;

public abstract class ApiException extends RuntimeException{

    private final ApiError apiError;

    public ApiException(ApiError apiError) {
        super();
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
