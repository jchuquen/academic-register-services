package temp.shared.application.exceptions;

public class ApiBadInformationException extends ApiException {
    public ApiBadInformationException(ApiError apiError) {
        super(apiError);
    }
}
