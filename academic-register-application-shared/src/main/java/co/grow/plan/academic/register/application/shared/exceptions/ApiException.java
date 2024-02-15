package co.grow.plan.academic.register.application.shared.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class ApiException extends RuntimeException{

    private final ApiError apiError;

}
