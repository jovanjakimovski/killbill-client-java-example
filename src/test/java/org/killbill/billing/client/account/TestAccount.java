//package org.killbill.billing.client.account;
//
//import org.joda.time.LocalDate;
//import org.killbill.billing.catalog.api.*;
//import org.killbill.billing.catalog.api.PriceList;
//import org.killbill.billing.client.api.gen.*;
//import org.killbill.billing.client.model.AuditLogs;
//import org.killbill.billing.client.model.PaymentMethods;
//import org.killbill.billing.client.model.gen.*;
//import org.killbill.billing.util.PluginProperties;
//import org.killbill.billing.util.config.definition.CurrencyConfig;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import org.killbill.billing.client.KillBillClientException;
//import org.killbill.billing.client.KillBillHttpClient;
//import org.killbill.billing.client.RequestOptions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.testng.Assert.assertEquals;
//
//public class TestAccount {
//
//	private PaymentMethodApi paymentMethodApi;
//	private AccountApi accountApi;
//	private SubscriptionApi subscriptionApi;
//	private InvoiceApi invoiceApi;
//	private PaymentTransactionApi paymentTransactionApi;
//
//	// Audit logs
//
//	AuditLog auditLog = new AuditLog(null, null, null,
//			null, "demo", "New Subscription",
//			"Trigger by Sinatra", null, null);
//
//
//	private KillBillHttpClient killBillHttpClient;
//	@BeforeTest
//	public void createKillBillHttpClient(){
//		int default_connection_timeout_sec = 10;
//		int default_read_timeout_sec = 60;
//		String username = "admin";
//		String password = "password";
//		String apiKey = "bob";
//		String apiSecret = "lazar";
//		String serverHost = "localhost";
//		int serverPort = 8080;
//		String kbServerUrl = String.format("http://%s:%d", serverHost, serverPort);
//		killBillHttpClient = new KillBillHttpClient(kbServerUrl, username, password, apiKey, apiSecret, null, null, default_connection_timeout_sec * 1000, default_read_timeout_sec * 1000);
//		paymentMethodApi = new PaymentMethodApi(killBillHttpClient);
//		accountApi = new AccountApi(killBillHttpClient);
//		subscriptionApi = new SubscriptionApi(killBillHttpClient);
//		invoiceApi = new InvoiceApi(killBillHttpClient);
//		paymentTransactionApi = new PaymentTransactionApi(killBillHttpClient);
//	}
//
//	private static final Logger logger = LoggerFactory.getLogger(TestAccount.class);
//
//	@Test
//	public void testGetPaymentMethods() throws KillBillClientException {
//		String createdBy = "Kill Bill Client Tutorial";
//		String reason = "Demonstrating Kill Bill Client";
//		String comment = "Demonstrating Kill Bill Client";
//		RequestOptions requestOptions = RequestOptions.builder().withCreatedBy(createdBy).withReason(reason).withComment(comment).build();
//		Map<String, String> NULL_PLUGIN_PROPERTIES = null;
//		UUID accountId = UUID.fromString("1649dff5-d75a-42b8-a50e-049f5aa006b0"); //accountId whose payment methods are to be fetched, replace with appropriate accountId from your database
//		List<PaymentMethod> paymentMethods = accountApi.getPaymentMethodsForAccount(accountId, NULL_PLUGIN_PROPERTIES,requestOptions);
//		logger.info("Payment methods=" + paymentMethods.size());
//		assertEquals(paymentMethods.size(), 2);
//
//	}
//
//	@Test
//	public void testNewSubscriptionPayment() throws KillBillClientException {
//
//		// Request Options  # Multi-tenancy and RBAC credentials
//		String createdBy = "Kill Bill Client Tutorial";
//		String reason = "Demonstrating Kill Bill Client";
//		String comment = "Demonstrating Kill Bill Client";
//		RequestOptions requestOptions =  RequestOptions.builder().withCreatedBy(createdBy).
//				withReason(reason).withComment(comment).build();
//
//		Account account = create_kb_account( requestOptions, accountApi);
//		PaymentMethod paymentMethod = create_kb_payment_method(account, "", "", requestOptions);
//
//
//
//	}
//
//	Account create_kb_account(RequestOptions options, AccountApi accountApi) throws KillBillClientException {
//
//		Account newAcc = new Account();
//		newAcc.setName("John Doe 2");
//		newAcc.setCurrency(Currency.USD);
//		return accountApi.createAccount(newAcc, options);
//
//	}
//
//	PaymentMethod create_kb_payment_method(Account account, String session_id, String token, RequestOptions options)
//	{
//
//		PluginProperty pluginProperty = new PluginProperty();
//		if(token == "null"){
//			pluginProperty.setKey("sessionId");
//			pluginProperty.setValue(session_id);
//		}
//		else {
//			pluginProperty.setKey("token");
//			pluginProperty.setValue(token);
//		}
//		PaymentMethodPluginDetail pluginInfo = new PaymentMethodPluginDetail();
//		pluginInfo.setProperties(List.of(pluginProperty));
//
//		PaymentMethod pm =
//		new PaymentMethod(null, null,
//				account.getAccountId(), true,
//				"killbill-stripe", pluginInfo, List.of(auditLog));
////		paymentMethodApi.getPaymentMethods("killbill-stripe", null, options);
//		return pm;
//	}
//
//	Subscription create_subscription(Account account, RequestOptions options) throws KillBillClientException {
//		Subscription subscription = new Subscription();
//		subscription.setAccountId(account.getAccountId());
//		subscription.setProductName("Sports");
//		subscription.setProductCategory(ProductCategory.BASE);
//		subscription.setBillingPeriod(BillingPeriod.MONTHLY);
//		subscription.setPriceList(PriceListSet.DEFAULT_PRICELIST_NAME);
//		subscription.setPriceOverrides(List.of(new PhasePrice(null, null,
//				PhaseType.TRIAL.name(), BigDecimal.valueOf(0.5f), null, null)));
//		return subscriptionApi.createSubscription(subscription, (LocalDate) null, null, null, options);
//	}
//
//	// Creates the Stripe Session
//	void createSession(Account account, RequestOptions requestOptions){
//
//	}
//
//	void charge (UUID account_id, String session_id, String token, RequestOptions options) throws KillBillClientException {
//		Account account = accountApi.getAccount(account_id, options);
//
//		// Add a payment method associated with the Stripe token
//		create_kb_payment_method(account, session_id, token, options);
//
//		// Add a subscription
//		create_subscription(account, options);
//
//		// Retrieve the invoice
//		//invoice = account.invoices(options).first;
//		Invoice invoice = invoiceApi.getInvoice(account_id, options);
//
//		// And the Stripe authorization
////		paymentTransactionApi.getPaymentByTransactionExternalKey()
//	}
//}