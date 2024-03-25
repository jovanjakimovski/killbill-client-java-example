package org.killbill.billing.client.account;

import org.junit.jupiter.api.Test;
import org.killbill.billing.client.KillBillClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest

public class Endpoint {

    @Autowired
    private ApiManager apiManager;

    @Test
    void getXmlCatalog() throws KillBillClientException, IOException {
        apiManager.getCatalogXml();
    }

}
