package temp.shared.application.exceptions;

public class ApiNoEntityException extends ApiException {
    public ApiNoEntityException(ApiError apiError) {
        super(apiError);
    }
}
