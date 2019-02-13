package example.micronaut;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VatValidationRequest implements Serializable {
    private String memberStateCode;
    private String vatNumber;
    private String requesterMemberStateCode;
    private String requesterVatNumber;

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

    public String setRequesterMemberStateCode() {
        return requesterMemberStateCode;
    }

    public void setRequesterMemberStateCode(String requesterMemberStateCode) {
        this.requesterMemberStateCode = requesterMemberStateCode;
    }

    public String getRequesterVatNumber() {
        return requesterVatNumber;
    }

    public void setRequesterVatNumber(String requesterVatNumber) {
        this.requesterVatNumber = requesterVatNumber;
    }
}
