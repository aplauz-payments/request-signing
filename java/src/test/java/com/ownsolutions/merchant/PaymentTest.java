package com.ownsolutions.merchant;

import com.ownsolutions.merchant.api.model.PaymentRequest;
import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest {

    private static final String channelId = "46e63edd-a705-4d5a-a7bc-bf4f2e111f54";
    private static final String currencyCode = "CHF";
    private static final String merchantUserReference = "9e00d4dc-366a-43aa-931b-7bd11daca7b7";
    private static final String merchantTransactionReference = "123313211";
    private static final String deviceReference = "a1447701-1d70-4a4a-87fc-4d314dd61488";
    private static final String integratorReference = "123";
    private static final String userReference = "payer";
    private static final String merchantReference = "merchant";
    private static final BigDecimal CHF48 = new BigDecimal(48);

    private static final String validCode1 = "9999999999999999";
    private static final String validCode2 = "8888888888888888";

    private String apiKey;
    private String privateKeyFile;
    private String baseUrl;
    private MerchantClient merchantClient;

    @Before
    public void init() {
        apiKey = "d9e19f9cdb747fc1928850d62af2c4bb01856ba1564e3bd3e38d1c3beaf9ef6a";
        privateKeyFile = "private_key.pem";
        baseUrl = "https://integration-api.aplauz.com";

        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(apiKey)
            .withPrivateKeyFile(privateKeyFile)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();
    }

    @Test
    public void givenValidApiKeyAnPaymentRequest_thenPaymentIsSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode(currencyCode)
            .withAmount(CHF48)
            .withChannelId(channelId)
            .withMerchantUserReference(userReference)
            .withMerchantTransactionReference(merchantReference)
            .withIntegratorTransactionReference(integratorReference)
            .withDeviceReference(deviceReference)
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNull();
        assertThat(apiResponse.getMessage()).isNull();
        assertThat(apiResponse.getChannelId()).isEqualToIgnoringCase(channelId);
        assertThat(apiResponse.getCurrencyCode()).isEqualToIgnoringCase(currencyCode);
    }

    @Test
    public void givenValidApiKeyAnMultipleCodesPaymentRequest_thenPaymentIsSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Arrays.asList(validCode1, validCode2))
            .withAmount(CHF48)
            .withCurrencyCode(currencyCode)
            .withChannelId(channelId)
            .withMerchantUserReference(userReference)
            .withMerchantTransactionReference(merchantReference)
            .withIntegratorTransactionReference(integratorReference)
            .withDeviceReference(deviceReference)
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNull();
        assertThat(apiResponse.getMessage()).isNull();
        assertThat(apiResponse.getChannelId()).isEqualToIgnoringCase(channelId);
        assertThat(apiResponse.getCurrencyCode()).isEqualToIgnoringCase(currencyCode);
    }

    @Test
    public void givenInvalidApiKey_thenPaymentIsNotSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode(currencyCode)
            .withAmount(CHF48)
            .withChannelId(channelId)
            .withMerchantUserReference(merchantUserReference)
            .withMerchantTransactionReference(merchantTransactionReference)
            .build();
        apiKey = "dummy";

        Response apiResponse = executePayment(paymentRequest);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INVALID_SHARED_KEY");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("Incorrect shared-key");
    }

    @Test
    public void givenInvalidCode_thenPaymentIsNotSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList("1234567890"))
            .withCurrencyCode(currencyCode)
            .withAmount(CHF48)
            .withChannelId(channelId)
            .withMerchantUserReference(merchantUserReference)
            .withMerchantTransactionReference(merchantTransactionReference)
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INVALID_REQUEST");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("\"codes[0]\" with value \"1234567890\" fails to match the 16 digit string pattern");
    }

    @Test
    public void givenInvalidChannelId_thenPaymentIsNotSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode(currencyCode)
            .withAmount(CHF48)
            .withMerchantUserReference(merchantUserReference)
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INVALID_REQUEST");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("\"channelId\" is required");
    }

    @Test
    public void givenInvalidMerchantUserReference_thenPaymentIsNotSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode(currencyCode)
            .withAmount(CHF48)
            .withChannelId(channelId)
            .withMerchantUserReference("")
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INVALID_REQUEST");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("\"merchantUserReference\" is not allowed to be empty");
    }

    @Test
    public void givenInvalidIntegratorTransactionReference_thenPaymentIsNotSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode(currencyCode)
            .withAmount(CHF48)
            .withChannelId(channelId)
            .withMerchantUserReference(merchantUserReference)
            .withMerchantTransactionReference(merchantTransactionReference)
            .withIntegratorTransactionReference("")
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INVALID_REQUEST");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("\"integratorTransactionReference\" is not allowed to be empty");
    }

    @Test
    public void givenNoAmount_thenPaymentIsNotSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode(currencyCode)
            .withChannelId(channelId)
            .withMerchantUserReference(merchantUserReference)
            .withMerchantTransactionReference(merchantTransactionReference)
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INVALID_REQUEST");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("\"amount\" is required");
    }

    @Test
    public void givenInvalidCurrency_thenPaymentIsNotSuccessful() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode("EU")
            .withAmount(CHF48)
            .withChannelId(channelId)
            .withMerchantUserReference(merchantUserReference)
            .withMerchantTransactionReference(merchantTransactionReference)
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INVALID_REQUEST");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("\"currencyCode\" must be [CHF]");
    }

    @Test
    public void givenTooBigAmount_thenPaymentIsNotSuccessfulDueToInsufficientBalance() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList(validCode1))
            .withCurrencyCode(currencyCode)
            .withAmount(new BigDecimal(1048))
            .withChannelId(channelId)
            .withMerchantUserReference(merchantUserReference)
            .withMerchantTransactionReference(merchantTransactionReference)
            .build();

        Response apiResponse = executePayment(paymentRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("INSUFFICIENT_BALANCE");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("Insufficient balance");
    }

    private Response executePayment(PaymentRequest paymentRequest) {

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(apiKey)
            .withPrivateKeyFile(privateKeyFile)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();

        return merchantClient.post(paymentRequest);
    }

    private Response executePayment(PaymentRequest paymentRequest, MerchantClient merchantClient) {
        return merchantClient.post(paymentRequest);
    }
}