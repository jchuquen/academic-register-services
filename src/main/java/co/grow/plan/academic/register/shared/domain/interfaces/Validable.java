package co.grow.plan.academic.register.shared.domain.interfaces;

public interface Validable {
    // Applying Tell Don´t Ask principle
    void validate() throws Exception;
}
