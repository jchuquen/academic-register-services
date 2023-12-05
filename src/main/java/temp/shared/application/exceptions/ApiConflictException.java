package temp.shared.application.exceptions;

public class ApiConflictException extends ApiException {
    public ApiConflictException(ApiError apiError) {
        super(apiError);
    }
}
