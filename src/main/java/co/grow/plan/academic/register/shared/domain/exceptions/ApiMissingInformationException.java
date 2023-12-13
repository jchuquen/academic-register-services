package co.grow.plan.academic.register.shared.domain.exceptions;

import co.grow.plan.academic.register.shared.application.exceptions.ApiError;
import co.grow.plan.academic.register.shared.application.exceptions.ApiException;

public final class ApiMissingInformationException extends ApiException {
    public ApiMissingInformationException(ApiError apiError) {
        super(apiError);
    }
}
