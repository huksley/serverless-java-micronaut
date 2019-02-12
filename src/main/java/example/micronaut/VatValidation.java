package example.micronaut;

import java.io.Serializable;
import java.util.Objects;

public class VatValidation extends VatValidationRequest implements Serializable {
    private Boolean valid;

    public VatValidation() {

    }

    public VatValidation(String memberStateCode, String vatNumber, Boolean valid) {
        super(memberStateCode, vatNumber);
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean isValid() {
        return valid;
    }
}
