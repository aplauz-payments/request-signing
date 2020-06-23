package com.ownsolutions.merchant.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Response {

    /**
     * Error code.
     */
    private final String error;

    /**
     * Error message.
     */
    private final String message;

    /**
     * Payment id.
     */
    private final String id;

    /**
     * Timestamp (UTC) of payment.
     */
    private final String createdAt;

    /**
     * Currency code.
     */
    private final String currencyCode;

    /**
     * Amount.
     */
    private final BigDecimal amount;

    /**
     * Channel id.
     */
    private final String channelId;

    /**
     * Merchant user reference.
     */
    private final String merchantUserReference;

    /**
     * Merchant merchant transaction reference.
     */
    private final String merchantTransactionReference;

    /**
     * PSP transaction reference.
     */
    private final String pspTransactionReference;

    /**
     * Device reference.
     */
    private final String deviceReference;

    /**
     * Integrator transaction reference.
     */
    private final String integratorTransactionReference;

    @JsonCreator
    public Response(
        @JsonProperty("error") final String error,
        @JsonProperty("message") final String message,
        @JsonProperty("id") final String id,
        @JsonProperty("createdAt") final String createdAt,
        @JsonProperty("currencyCode") final String currencyCode,
        @JsonProperty("amount") final BigDecimal amount,
        @JsonProperty("channelId") final String channelId,
        @JsonProperty("merchantUserReference") final String merchantUserReference,
        @JsonProperty("merchantTransactionReference") final String merchantTransactionReference,
        @JsonProperty("pspTransactionReference") final String pspTransactionReference,
        @JsonProperty("deviceReference") final String deviceReference,
        @JsonProperty("integratorTransactionReference") final String integratorTransactionReference) {
        this.error = error;
        this.message = message;
        this.id = id;
        this.createdAt = createdAt;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.channelId = channelId;
        this.merchantUserReference = merchantUserReference;
        this.merchantTransactionReference = merchantTransactionReference;
        this.pspTransactionReference = pspTransactionReference;
        this.deviceReference = deviceReference;
        this.integratorTransactionReference = integratorTransactionReference;
    }

    private Response(Builder builder) {
        error = builder.error;
        message = builder.message;
        id = builder.id;
        createdAt = builder.createdAt;
        currencyCode = builder.currencyCode;
        amount = builder.amount;
        channelId = builder.channelId;
        merchantTransactionReference = builder.merchantTransactionReference;
        pspTransactionReference = builder.pspTransactionReference;
        deviceReference = builder.deviceReference;
        integratorTransactionReference = builder.integratorTransactionReference;
        merchantUserReference = builder.merchantUserReference;
    }

    public static Builder builder() {
        return new Builder();
    }

    /*** GETTERS. ***/

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
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

    public String getDeviceReference() {
        return deviceReference;
    }

    public String getIntegratorTransactionReference() {
        return integratorTransactionReference;
    }

    @Override
    public String toString() {
        return "Response{" +
            "error='" + error + '\'' +
            ", message='" + message + '\'' +
            ", id='" + id + '\'' +
            ", createdAt='" + createdAt + '\'' +
            ", currencyCode='" + currencyCode + '\'' +
            ", amount=" + amount +
            ", channelId='" + channelId + '\'' +
            ", merchantUserReference='" + merchantUserReference + '\'' +
            ", merchantTransactionReference='" + merchantTransactionReference + '\'' +
            ", pspTransactionReference='" + pspTransactionReference + '\'' +
            ", deviceReference='" + deviceReference + '\'' +
            ", integratorTransactionReference='" + integratorTransactionReference + '\'' +
            '}';
    }

    /*** BUILDER SUBCLASS. ***/

    public static final class Builder {
        private String error;
        private String message;
        private String id;
        private String createdAt;
        private String currencyCode;
        private BigDecimal amount;
        private String channelId;
        private String merchantUserReference;
        private String merchantTransactionReference;
        private String pspTransactionReference;
        private String deviceReference;
        private String integratorTransactionReference;

        private Builder() {
        }

        public static Builder aResponse() {
            return new Builder();
        }

        public Builder withError(String error) {
            this.error = error;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public Builder withDeviceReference(String deviceReference) {
            this.deviceReference = deviceReference;
            return this;
        }

        public Builder withIntegratorTransactionReference(String integratorTransactionReference) {
            this.integratorTransactionReference = integratorTransactionReference;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
