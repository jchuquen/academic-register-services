package co.grow.plan.academic.register.shared.exceptions;

import java.util.List;

public class ApiError {

    private String mainMessage;

    private List<String> detailedErrors;

    public ApiError(String mainMessage) {
        this.mainMessage = mainMessage;
    }

    public ApiError(String mainMessage, List<String> detailedErrors) {
        this.mainMessage = mainMessage;
        this.detailedErrors = detailedErrors;
    }

    public String getMainMessage() {
        return mainMessage;
    }

    public void setMainMessage(String mainMessage) {
        this.mainMessage = mainMessage;
    }

    public List<String> getDetailedErrors() {
        return detailedErrors;
    }

    public void setDetailedErrors(List<String> detailedErrors) {
        this.detailedErrors = detailedErrors;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "mainMessage='" + mainMessage + '\'' +
                ", detailedErrors=" + detailedErrors +
                '}';
    }
}
