package com.ownsolutions.merchant.main;

import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.exception.MerchantApiException;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class GetPayment {

    private static final String apiKey = "d9e19f9cdb747fc1928850d62af2c4bb01856ba1564e3bd3e38d1c3beaf9ef6a";
    private static final String privateKeyFile = "private_key.pem";
    private static final String paymentId = "67ecc326-82b9-46a2-91c9-59a32f86d691";
    private static final String baseUrl = "https://integration-api.aplauz.com";
    private static final String pathUri = "/merchant/v1/payments/%s";

    /**
     * Main method.
     *
     * @param args The input arguments
     */
    public static void main(String[] args) throws IOException {

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

        MerchantClient merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();

        System.out.println("Get Payment base URL: " + baseUrl);

        Response apiResponse = null;
        try {
            apiResponse = merchantClient.get(String.format(pathUri, paymentId));
        } catch (MerchantApiException rae) {
            System.out.println("Get payment failed with following message: " + rae.getLocalizedMessage());
            System.exit(1);
        }

        System.out.println("Response Body: " + apiResponse.toString());
    }
}
