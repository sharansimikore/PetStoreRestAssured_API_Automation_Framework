package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    public static ExtentReports extent;

    public static ExtentReports getReportInstance() {

        if (extent == null) {

            String timeStamp =
                    new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                            .format(new Date());

            String reportName =
                    "API-Test-Report-" + timeStamp + ".html";

            String reportPath =
                    System.getProperty("user.dir")
                            + "/Reports/"
                            + reportName;

            // Create Spark Reporter
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            // =========================
            // Report Configuration
            // =========================

            sparkReporter.config().setDocumentTitle("API Automation Report");

            sparkReporter.config().setReportName("Rest Assured Execution Report");

            // DARK THEME
            sparkReporter.config().setTheme(Theme.DARK);

            // Time format
            sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

            // =========================
            // Create Extent Report
            // =========================

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            // =========================
            // System Information
            // =========================

            extent.setSystemInfo("Project", "Rest Assured API Framework");
            extent.setSystemInfo("Tester", "Sharan");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extent;
    }
}