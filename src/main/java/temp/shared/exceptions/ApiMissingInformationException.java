package temp.shared.exceptions;

public class ApiMissingInformationException extends ApiException {
    public ApiMissingInformationException(ApiError apiError) {
        super(apiError);
    }
}
