package co.grow.plan.academic.register.exceptions;

import java.util.List;

public class ApiError {
    private ErrorCode errorCode;
    private String mainMessage;

    private List<String> detailedErrors;

    public ApiError(ErrorCode errorCode, String mainMessage) {
        this.errorCode = errorCode;
        this.mainMessage = mainMessage;
    }

    public ApiError(ErrorCode errorCode, String mainMessage, List<String> detailedErrors) {
        this.errorCode = errorCode;
        this.mainMessage = mainMessage;
        this.detailedErrors = detailedErrors;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
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
                "errorCode=" + errorCode +
                ", mainMessage='" + mainMessage + '\'' +
                ", detailedErrors=" + detailedErrors +
                '}';
    }
}
