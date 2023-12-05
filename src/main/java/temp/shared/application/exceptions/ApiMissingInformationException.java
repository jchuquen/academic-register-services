package temp.shared.application.exceptions;

public class ApiMissingInformationException extends ApiException {
    public ApiMissingInformationException(ApiError apiError) {
        super(apiError);
    }
}
