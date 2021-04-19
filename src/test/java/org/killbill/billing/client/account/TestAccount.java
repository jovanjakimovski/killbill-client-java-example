package org.killbill.billing.client.account;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.UUID;

import org.killbill.billing.ErrorCode;
import org.killbill.billing.client.KillBillClientException;
import org.killbill.billing.client.KillBillHttpClient;
import org.killbill.billing.client.RequestOptions;
import org.killbill.billing.client.api.gen.AccountApi;
import org.killbill.billing.client.model.gen.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertEquals;

public class TestAccount {

	private static final Logger logger = LoggerFactory.getLogger(TestAccount.class);

	@Test
	public void testGetPaymentMethods() throws KillBillClientException {
		
		int default_connection_timeout_sec = 10;
		int default_read_timeout_sec = 60;
		int default_request_timeout_sec = default_read_timeout_sec;
		String username = "admin";
		String password = "password";
		String apiKey = "bob";
		String apiSecret = "lazar";
		String serverHost = "localhost";
		int serverPort = 8080;
		String kbServerUrl = String.format("http://%s:%d", serverHost, serverPort);
		KillBillHttpClient killBillHttpClient = new KillBillHttpClient(kbServerUrl, username, password, apiKey,apiSecret, null, null, default_connection_timeout_sec * 1000, default_read_timeout_sec * 1000,default_request_timeout_sec * 1000);
		AccountApi accountApi = new AccountApi(killBillHttpClient);
		String createdBy = "Kill Bill Client Tutorial";
		String reason = "Demonstrating Kill Bill Client";
		String comment = "Demonstrating Kill Bill Client";
		RequestOptions requestOptions = RequestOptions.builder().withCreatedBy(createdBy).withReason(reason).withComment(comment).build();
		ImmutableMap<String, String> NULL_PLUGIN_PROPERTIES = null;
		UUID accountId = UUID.fromString("a21f1ca3-53ec-456b-8039-7170350c9c12");
		List<PaymentMethod> paymentMethods = accountApi.getPaymentMethodsForAccount(accountId, NULL_PLUGIN_PROPERTIES,requestOptions);
		logger.info("Payment methods=" + paymentMethods.size());
		assertEquals(paymentMethods.size(), 2);

	}
}