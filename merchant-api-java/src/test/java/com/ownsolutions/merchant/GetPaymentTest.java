package com.ownsolutions.merchant;

import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.exception.MerchantApiException;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class GetPaymentTest {

    private static final String channelId = "46e63edd-a705-4d5a-a7bc-bf4f2e111f54";
    private static final String currencyCode = "CHF";

    private String apiKey;
    private String privateKeyFile;
    private String paymentId;
    private String baseUrl;
    private String pathUri;
    private MerchantClient merchantClient;

    @Before
    public void init() {
        apiKey = "d9e19f9cdb747fc1928850d62af2c4bb01856ba1564e3bd3e38d1c3beaf9ef6a";
        privateKeyFile = "private_key.pem";
        paymentId = "67ecc326-82b9-46a2-91c9-59a32f86d691";
        pathUri = "/merchant/v1/payments/%s";
        baseUrl = "https://integration-api.aplauz.com";

        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );
    }

    @Test
    public void givenValidApiKeyAnPaymentRequest_thenGetPaymentIsSuccessful() throws Exception {

        Response apiResponse = executeGetPayment();

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNull();
        assertThat(apiResponse.getMessage()).isNull();
        assertThat(apiResponse.getChannelId()).isEqualToIgnoringCase(channelId);
        assertThat(apiResponse.getCurrencyCode()).isEqualToIgnoringCase(currencyCode);
    }

    @Test(expected = MerchantApiException.class)
    public void givenInvalidApiKey_thenGetPaymentIsNotSuccessful() throws Exception {
        apiKey = "dummy";

        Response apiResponse = executeGetPayment();

        assertThat(apiResponse).isNull();
    }

    @Test(expected = MerchantApiException.class)
    public void givenInvalidPaymentId_thenGetPaymentIsNotSuccessful() throws Exception {
        paymentId = "1111111";

        Response apiResponse = executeGetPayment();

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getError()).isNotNull();
        assertThat(apiResponse.getError()).isEqualToIgnoringCase("NOT_FOUND");
        assertThat(apiResponse.getMessage()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualToIgnoringCase("Unknown payment id");
    }

    private Response executeGetPayment() {

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

        return merchantClient.get(String.format(pathUri, paymentId));
    }
}
