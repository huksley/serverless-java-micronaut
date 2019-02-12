package example.micronaut;

import java.io.Serializable;

public class VatValidationRequest implements Serializable {
    private String memberStateCode;
    private String vatNumber;

    public VatValidationRequest() {
    }

    public VatValidationRequest(String memberStateCode, String vatNumber) {
        this.memberStateCode = memberStateCode;
        this.vatNumber = vatNumber;
    }

    public String getMemberStateCode() {
        return memberStateCode;
    }

    public void setMemberStateCode(String memberStateCode) {
        this.memberStateCode = memberStateCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
}
