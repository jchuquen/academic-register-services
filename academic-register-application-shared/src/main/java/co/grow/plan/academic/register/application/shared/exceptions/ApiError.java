package co.grow.plan.academic.register.application.shared.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class ApiError {

    private final String mainMessage;

    private List<String> detailedErrors = new ArrayList<>();

    public ApiError(String mainMessage, List<String> detailedErrors) {
        this.mainMessage = mainMessage;
    }

    public ApiError(String mainMessage) {
        this.mainMessage = mainMessage;
    }
}
