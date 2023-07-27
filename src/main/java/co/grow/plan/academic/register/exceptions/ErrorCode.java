package co.grow.plan.academic.register.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    MISSING_INFORMATION("ERR-00-01"),
    WRONG_INFORMATION("ERR-00-02"),
    CONFLICT("ERR-01-00"),

    UNDEFINED("ERR-99-00");

    private final String code;

    private ErrorCode(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
