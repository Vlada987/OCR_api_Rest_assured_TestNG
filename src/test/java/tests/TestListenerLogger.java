package tests;

import java.util.HashMap;
import java.util.Map;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TestListenerLogger implements ITestListener {

    public static ThreadLocal<Map<String, Object[]>> threadTestData = ThreadLocal.withInitial(HashMap::new);
    private static final Logger logger = LogManager.getLogger(TestListenerLogger.class);

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        
        if (threadTestData.get().isEmpty()) {
            logger.error("No test data found for failed test case.");
            return; 
        }
        Map<String, Object[]> testData = threadTestData.get();
        String testCaseId = testData.keySet().stream().findFirst().get(); 
        Object[] testCaseData = testData.get(testCaseId); 

        logger.info("********* Test case "+testCaseId+" *********");
        logger.info("Test results for: TestCase ID =" + testCaseId + " | Scenario: " + testCaseData[0]);
        logger.info("Received status code " + testCaseData[1]);
        logger.info("***  TEST PASSED ***");
        logger.info("********************************************");
        threadTestData.remove();
    }

    
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        
        if (threadTestData.get().isEmpty()) {
            logger.error("No test data found for failed test case.");
            return; 
        }
        Map<String, Object[]> testData = threadTestData.get();
        String testCaseId = testData.keySet().stream().findFirst().get(); 
        Object[] testCaseData = testData.get(testCaseId); 

        logger.info("********* Test case "+testCaseId+" *********");
        logger.info("Test Started: TestCase ID = " + testCaseId + " | Scenario: " + testCaseData[0]);
        logger.info("Received status code " + testCaseData[1]);
        logger.info("***  TEST FAILED ***");
        logger.error("Error: " + result.getThrowable().getMessage());
        logger.info("********************************************");
        threadTestData.remove();
    }
}
