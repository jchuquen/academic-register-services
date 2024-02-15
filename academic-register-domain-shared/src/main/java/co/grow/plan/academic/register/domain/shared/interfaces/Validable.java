package co.grow.plan.academic.register.domain.shared.interfaces;

public interface Validable {
    // Applying Tell Don´t Ask principle
    void validate() throws Exception;
}
