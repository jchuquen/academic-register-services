package temp.shared.exceptions;

public class ApiBadInformationException extends ApiException {
    public ApiBadInformationException(ApiError apiError) {
        super(apiError);
    }
}
