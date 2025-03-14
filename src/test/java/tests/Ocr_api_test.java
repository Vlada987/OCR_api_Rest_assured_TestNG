package tests;

import dataProvider.DataProviders;
import io.restassured.response.Response;
import rest.RestMethods;
import org.testng.Assert;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ dataInterceptor.DataInterceptor.class, TestListenerLogger.class,
		extentReporter.ExtentReportListener.class })
public class Ocr_api_test extends BaseTest {

	
	
	@Test(priority = 0, dataProvider = "ocr_data_provider", dataProviderClass = DataProviders.class, attributes = {
			@CustomAttribute(name = "test_type", values = { "authenticationfilter" }),
			@CustomAttribute(name = "data_editor", values = { "editor" }) })

	public void auth_test(String testCaseId, String description, String testType, String expectedStatusCode,
			String expectedResponseMessage, String apiKey, String imagePath) {

		TestListenerLogger.threadTestData.get().put(testCaseId, new Object[] { description, expectedStatusCode });
		Response response = RestMethods.POST(setTestData(apiKey, imagePath));

		Assert.assertEquals(Integer.valueOf(expectedStatusCode), response.statusCode());
		Assert.assertTrue(response.getBody().asString().contains(expectedResponseMessage));

	}

	
	
	@Test(priority = 1, dataProvider = "ocr_data_provider", dataProviderClass = DataProviders.class, attributes = {
			@CustomAttribute(name = "test_type", values = { "servicefilter" }) })

	public void service_test(String testCaseId, String description, String testType, String expectedStatusCode,
			String expectedResponseMessage, String apiKey, String imagePath) {

		TestListenerLogger.threadTestData.get().put(testCaseId, new Object[] { description, expectedStatusCode });
		Response response = RestMethods.POST(setTestData(apiKey, imagePath));

		Assert.assertEquals(Integer.valueOf(expectedStatusCode), response.statusCode());
		Assert.assertTrue(response.getBody().asString().contains(expectedResponseMessage));

	}

	
	
	@Test(priority = 2, dataProvider = "ocr_data_provider", dataProviderClass = DataProviders.class, attributes = {
			@CustomAttribute(name = "multiply_factor", values = { "3" }),
			@CustomAttribute(name = "wanted_data", values = { "onlyvalid" }) })
	public void parallel_test(String testCaseId, String description, String testType, String expectedStatusCode,
			String expectedResponseMessage, String apiKey, String imagePath) {

		TestListenerLogger.threadTestData.get().put(testCaseId, new Object[] { description, expectedStatusCode });
		Response response = RestMethods.POST(setTestData(apiKey, imagePath));

		Assert.assertEquals(Integer.valueOf(expectedStatusCode), response.statusCode());
		Assert.assertTrue(response.getBody().asString().contains(expectedResponseMessage));

	}

}
