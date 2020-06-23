package com.ownsolutions.merchant;

import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthcheckTest {

    private String baseUrl;
    private MerchantClient merchantClient;

    @Before
    public void init() {
        baseUrl = "https://integration-api.aplauz.com";
    }

    @Test
    public void givenValidEndPoint_thenHealthcheckIsSuccessful() throws Exception {

        String pathUri = "/merchant/v1/healthcheck";
        int status = executeGetReponseStatus(pathUri);

        assertThat(status).isNotNull();
        assertThat(status).isEqualTo(HttpStatus.SC_OK);
    }

    @Test
    public void givenInvalidEndPoint_thenHealthcheckIsNotSuccessful() throws Exception {

        String pathUri = "/merchant/v1/healthcheck2";
        int status = executeGetReponseStatus(pathUri);

        assertThat(status).isNotNull();
        assertThat(status).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    private int executeGetReponseStatus(String path) {

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(null)
            .withPrivateKeyFile(null)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();

        return merchantClient.getResponseStatus(path);
    }
}
