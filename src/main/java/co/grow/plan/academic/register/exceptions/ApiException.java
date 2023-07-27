package co.grow.plan.academic.register.exceptions;

public class ApiException extends RuntimeException{

    private ApiError apiError;

    public ApiException(ApiError apiError) {
        super();
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
