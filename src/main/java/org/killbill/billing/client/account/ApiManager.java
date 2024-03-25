package org.killbill.billing.client.account;

import lombok.Getter;
import org.joda.time.LocalDateTime;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.joda.time.DateTime;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


@Service
@Getter
public class ApiManager {

    @Value("${kill-bill.tenant.username}")
    private String username;
    @Value("${kill-bill.tenant.password}")
    private String password;
    @Value("${kill-bill.tenant.apiKey}")
    private String apiKey;
    @Value("${kill-bill.tenant.apiSecret}")
    private String apiSecret;
    @Value("${kill-bill.client.endPoint}")
    private String killBillClientEndpoint;

    KillBillHttpClient killBillHttpClient = new KillBillHttpClient(
            String.format("http://%s:%d", "127.0.0.1", 8080),
            username,
            password,
            apiKey,
            apiSecret,
            null,
            null,
            1000,
            5000);

    AccountApi accountApi = new AccountApi(killBillHttpClient);
    AdminApi adminApi = new AdminApi(killBillHttpClient);
    BundleApi bundleApi = new BundleApi(killBillHttpClient);
    CatalogApi catalogApi = new CatalogApi(killBillHttpClient);
    CreditApi creditApi = new CreditApi(killBillHttpClient);
    CustomFieldApi customFieldApi = new CustomFieldApi(killBillHttpClient);
    ExportApi exportApi = new ExportApi(killBillHttpClient);
    InvoiceApi invoiceApi = new InvoiceApi(killBillHttpClient);
    InvoiceItemApi invoiceItemApi = new InvoiceItemApi(killBillHttpClient);
    InvoicePaymentApi invoicePaymentApi = new InvoicePaymentApi(killBillHttpClient);
    NodesInfoApi nodesInfoApi = new NodesInfoApi(killBillHttpClient);
    OverdueApi overdueApi = new OverdueApi(killBillHttpClient);
    PaymentApi paymentApi = new PaymentApi(killBillHttpClient);
    PaymentGatewayApi paymentGatewayApi = new PaymentGatewayApi(killBillHttpClient);
    PaymentMethodApi paymentMethodApi = new PaymentMethodApi(killBillHttpClient);
    PaymentTransactionApi paymentTransactionApi = new PaymentTransactionApi(killBillHttpClient);
    PluginInfoApi pluginInfoApi = new PluginInfoApi(killBillHttpClient);
    SecurityApi securityApi = new SecurityApi(killBillHttpClient);
    SubscriptionApi subscriptionApi = new SubscriptionApi(killBillHttpClient);
    TagApi tagApi = new TagApi(killBillHttpClient);
    TagDefinitionApi tagDefinitionApi = new TagDefinitionApi(killBillHttpClient);
    TenantApi tenantApi = new TenantApi(killBillHttpClient);
    UsageApi usageApi = new UsageApi(killBillHttpClient);

    // Request Options example
    String createdBy = "me";
    String reason = "Going through my first tutorial";
    String comment = "I like it!";


    RequestOptions requestOptions = RequestOptions.builder()
            .withCreatedBy(createdBy)
            .withReason(reason)
            .withComment(comment)
//            .withQueryParams(queryParams)
            .withTenantApiKey(apiKey)
            .withTenantApiSecret(apiSecret)
            .build();

    void getCatalogXml() throws KillBillClientException, IOException {
        DateTime requestedDate = null;
        UUID accountId = null;

        String catalog = catalogApi.getCatalogXml(requestedDate,
                accountId,
                requestOptions);
        writeBytesToFile((new LocalDateTime()).toString(), catalog);

    }
    void writeBytesToFile(String fileName, String messageToWrite) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(fileName);
        byte[] strToBytes = messageToWrite.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }
}
