package com.ownsolutions.merchant.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentRequest extends Request<Response>  {

    @JsonIgnore
    private final String path = "/merchant/v1/payments/";

    @JsonIgnore
    private final Class<Response> responseType = Response.class;

    /**
     * One or many pins/codes to be used for payment.
     */
   private final List<String> codes;

    /**
     * The three-letter ISO 4217 designator for the voucher currency.
     */
    private final String currencyCode;

    /**
     * The total amount to redeem from all vouchers.
     */
    private final BigDecimal amount;

    /**
     * The store where the transaction took place.
     */
    private final String channelId;

    /**
     * A unique identifier for the merchant user.
     */
    private final String merchantUserReference;

    /**
     * A merchant reference in APLAUZ system for this payment.
     */
    private final String merchantTransactionReference;

    /**
     * An optional reference to identify the payment for the PSP.
     */
    private final String pspTransactionReference;

    /**
     * An optional reference to identify the payment for the integrator.
     */
    private final String integratorTransactionReference;

    /**
     * An optional reference to identify the device that initiated the payment.
     */
    private final String deviceReference;

    @JsonCreator
    public PaymentRequest(
        @JsonProperty("codes") final List<String> codes,
        @JsonProperty("currencyCode") final String currencyCode,
        @JsonProperty("amount") final BigDecimal amount,
        @JsonProperty("channelId") final String channelId,
        @JsonProperty("merchantUserReference") final String merchantUserReference,
        @JsonProperty("merchantTransactionReference") final String merchantTransactionReference,
        @JsonProperty("pspTransactionReference") final String pspTransactionReference,
        @JsonProperty("integratorTransactionReference") final String integratorTransactionReference,
        @JsonProperty("deviceReference") final String deviceReference) {
        this.codes = codes;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.channelId = channelId;
        this.merchantUserReference = merchantUserReference;
        this.merchantTransactionReference = merchantTransactionReference;
        this.pspTransactionReference = pspTransactionReference;
        this.integratorTransactionReference = integratorTransactionReference;
        this.deviceReference = deviceReference;
    }

    private PaymentRequest(PaymentRequest.Builder builder) {
        codes = builder.codes;
        currencyCode = builder.currencyCode;
        amount = builder.amount;
        channelId = builder.channelId;
        merchantUserReference = builder.merchantUserReference;
        merchantTransactionReference = builder.merchantTransactionReference;
        pspTransactionReference = builder.pspTransactionReference;
        deviceReference = builder.deviceReference;
        integratorTransactionReference = builder.integratorTransactionReference;
    }

    public static PaymentRequest.Builder builder() {
        return new PaymentRequest.Builder();
    }

    /*** GETTERS. ***/
    public List<String> getCodes() {
        return codes;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getMerchantUserReference() {
        return merchantUserReference;
    }

    public String getMerchantTransactionReference() {
        return merchantTransactionReference;
    }

    public String getPspTransactionReference() {
        return pspTransactionReference;
    }

    public String getIntegratorTransactionReference() {
        return integratorTransactionReference;
    }

    public String getDeviceReference() {
        return deviceReference;
    }

    @Override
    @JsonIgnore
    public String getPath() {
        return this.path;
    }

    @Override
    @JsonIgnore
    public Class<Response> getResponseType() {
        return this.responseType;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
            "path='" + path + '\'' +
            ", responseType=" + responseType +
            ", codes=" + codes +
            ", currencyCode='" + currencyCode + '\'' +
            ", amount='" + amount + '\'' +
            ", channelId='" + channelId + '\'' +
            ", merchantUserReference='" + merchantUserReference + '\'' +
            ", merchantTransactionReference='" + merchantTransactionReference + '\'' +
            ", pspTransactionReference='" + pspTransactionReference + '\'' +
            ", integratorTransactionReference='" + integratorTransactionReference + '\'' +
            ", deviceReference='" + deviceReference + '\'' +
            '}';
    }

    /*** BUILDER SUBCLASS. ***/

    public static final class Builder {
        private List<String> codes;
        private String currencyCode;
        private BigDecimal amount;
        private String channelId;
        private String merchantUserReference;
        private String merchantTransactionReference;
        private String pspTransactionReference;
        private String integratorTransactionReference;
        private String deviceReference;

        public Builder() {
        }

        public static Builder aPaymentRequest() {
            return new Builder();
        }

        public Builder withCodes(List<String> codes) {
            this.codes = codes;
            return this;
        }

        public Builder withCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder withMerchantUserReference(String merchantUserReference) {
            this.merchantUserReference = merchantUserReference;
            return this;
        }

        public Builder withMerchantTransactionReference(String merchantTransactionReference) {
            this.merchantTransactionReference = merchantTransactionReference;
            return this;
        }

        public Builder withPspTransactionReference(String pspTransactionReference) {
            this.pspTransactionReference = pspTransactionReference;
            return this;
        }

        public Builder withIntegratorTransactionReference(String integratorTransactionReference) {
            this.integratorTransactionReference = integratorTransactionReference;
            return this;
        }

        public Builder withDeviceReference(String deviceReference) {
            this.deviceReference = deviceReference;
            return this;
        }

        public PaymentRequest build() {
            return new PaymentRequest(this);
        }
    }
}
