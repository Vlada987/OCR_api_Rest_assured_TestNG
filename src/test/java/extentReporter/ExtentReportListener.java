package extentReporter;

import java.util.Map.Entry;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import tests.TestListenerLogger;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent; 
    private ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    @BeforeSuite
    public void onStart(ITestContext context) {
        if (extent == null) {
            String filePath = System.getProperty("user.dir") + "/myReports/" + ExtentReportSetup.getReportNameWithTimeStamp();
            extent = ExtentReportSetup.createInstance(filePath, "OCR-REPORT", "OCR-REPORT");
        }
    }

    @BeforeMethod
    public synchronized void onTestStart(ITestResult result) {
        
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThreadLocal.set(test); 
    }

    @AfterMethod
    public synchronized void onTestSuccess(ITestResult result) {
        
        ExtentTest test = testThreadLocal.get();
        
        if (test != null) {
            Entry<String, Object[]> testCase = TestListenerLogger.threadTestData.get().entrySet().stream().findFirst().get();
            String tcId = testCase.getKey();
            Object[] data = testCase.getValue();
            
            test.pass(MarkupHelper.createLabel(tcId+" ID Test Passed: " + result.getMethod().getMethodName(), ExtentColor.GREEN));
            test.info("Test Case ID: " + tcId + ", Description: " + data[0]);
        } else {
            System.out.println("Warning: Test not started properly for method " + result.getMethod().getMethodName());
        }
    }

    @AfterMethod
    public synchronized void onTestFailure(ITestResult result) {
        
        ExtentTest test = testThreadLocal.get();
        
        if (test != null) {
            Entry<String, Object[]> testCase = TestListenerLogger.threadTestData.get().entrySet().stream().findFirst().get();
            String tcId = testCase.getKey();
            Object[] data = testCase.getValue();
            test.fail(MarkupHelper.createLabel(tcId+" ID Test Failed: " + result.getMethod().getMethodName(), ExtentColor.RED));
            test.info("Test Case ID: " + tcId + ", Description: " + data[0]);
            test.fail(result.getThrowable());
        } else {
            System.out.println("Warning: Test not started properly for method " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        
        ExtentTest test = testThreadLocal.get();
        if (test != null) {
            test.skip("Test Skipped: " + result.getMethod().getMethodName());
        } else {
            System.out.println("Warning: Test skipped, but test was not properly started for method " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
       
    }

    @AfterSuite
    public void onFinish(ITestContext context) {        
        if (extent != null) {
            extent.flush();
        }
    }
}
