package temp.shared.exceptions;

public class ApiConflictException extends ApiException {
    public ApiConflictException(ApiError apiError) {
        super(apiError);
    }
}
