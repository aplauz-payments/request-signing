package com.ownsolutions.merchant.main;

import com.ownsolutions.merchant.api.model.PaymentRequest;
import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.exception.MerchantApiException;
import org.apache.http.impl.client.HttpClients;

import java.math.BigDecimal;
import java.util.Collections;

public class Payment {

    private static final String apiKey = "d9e19f9cdb747fc1928850d62af2c4bb01856ba1564e3bd3e38d1c3beaf9ef6a";
    private static final String privateKeyFile = "private_key.pem";
    private static final String baseUrl = "https://integration-api.aplauz.com";

    /**
     * Main method.
     *
     * @param args The input arguments
     */
    public static void main(String[] args) {

        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );

        PaymentRequest paymentRequest = new PaymentRequest.Builder()
            .withCodes(Collections.singletonList("9999999999999999"))
            .withCurrencyCode("CHF")
            .withAmount(new BigDecimal(48))
            .withChannelId("46e63edd-a705-4d5a-a7bc-bf4f2e111f54")
            .withMerchantUserReference("9e00d4dc-366a-43aa-931b-7bd11daca7b7")
            .withMerchantTransactionReference("279800ef09c6")
            .withPspTransactionReference("e5785e77e")
            .withIntegratorTransactionReference("7fd113c")
            .withDeviceReference("a1bb47607db244c396b04028b2aaf268")
            .build();

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(apiKey)
            .withPrivateKeyFile(privateKeyFile)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        MerchantClient merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();

        System.out.println("Payment base URL: " + baseUrl);
        System.out.println("Request Body: " + paymentRequest.toString());

        Response apiResponse = null;
        try {
            apiResponse = merchantClient.post(paymentRequest);
        } catch (MerchantApiException rae) {
            System.out.println("Payment failed with the following message: " + rae.getLocalizedMessage());
            System.exit(1);
        }

        System.out.println("Response Body: " + apiResponse.toString());
    }
}
