package tests;

import java.io.File;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.xml.XmlTest;

import rest.RequestContext;
import utils.Cons;

public class BaseTest {

	
	
	
	
	public RequestContext setTestData(String apiKey,String imagePath) {
		
		RequestContext context=new RequestContext();
		context.requestHeaderParams.put("apikey", apiKey);
		context.multiParts.put("file", new File(imagePath));
		context.formParams.put("isOverlayRequired", "true");
		context.formParams.put("detectOrientation", "true");
		context.formParams.put("scale", "true");
		context.baseURL=Cons.OCR_API_URL;
		context.URI=Cons.OCR_ENDPOINT;
		return context;
	}

	
	
	

	
	
}
