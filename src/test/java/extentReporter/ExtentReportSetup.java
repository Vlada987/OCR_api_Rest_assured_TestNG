package extentReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportSetup {

	public static ExtentReports extentReports;

	public static ExtentReports createInstance(String fileName, String reportName, String documentTitle) {

		ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
		extentSparkReporter.config().setReportName(reportName);
		extentSparkReporter.config().setDocumentTitle(documentTitle);
		extentSparkReporter.config().setTheme(Theme.STANDARD);
		extentSparkReporter.config().setEncoding("utf-8");
		extentReports = new ExtentReports();
		extentReports.setSystemInfo("Author", "Vlada987");
		extentReports.setSystemInfo("Suite", "OCR, Automation, Test Report");
		extentReports.setSystemInfo("OS", "Windows 10");
		extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
		extentReports.attachReporter(extentSparkReporter);
		return extentReports;
	}

	public static String getReportNameWithTimeStamp() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime ldt = LocalDateTime.now();
		String formattedTime = dtf.format(ldt);
		String reportName = "TestReport" + formattedTime + ".html";
		return reportName;
	}

}