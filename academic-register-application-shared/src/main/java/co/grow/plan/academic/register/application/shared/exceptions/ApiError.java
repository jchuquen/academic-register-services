package co.grow.plan.academic.register.application.shared.exceptions;

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
