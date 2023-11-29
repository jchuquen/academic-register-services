package temp.shared.exceptions;

public class ApiNoEntityException extends ApiException {
    public ApiNoEntityException(ApiError apiError) {
        super(apiError);
    }
}
