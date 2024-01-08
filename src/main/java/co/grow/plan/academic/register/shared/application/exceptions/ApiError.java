package co.grow.plan.academic.register.shared.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public final class ApiError {

    private final String mainMessage;

    private List<String> detailedErrors;

    public ApiError(String mainMessage) {
        this.mainMessage = mainMessage;
    }
}
