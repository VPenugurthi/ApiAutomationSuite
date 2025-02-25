 package runner;

import io.cucumber.testng.CucumberOptions;

import java.io.File;

import org.testng.annotations.BeforeSuite;

import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
    features = "src/test/resources/features", // Path to your feature files
    glue = "stepDefinitions",
    monochrome=true,
//    tags="@AddPlace",
    plugin = {"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", 
    		  "html:target/cucumber-reports.html", 
    		  "json:target/cucumber.json",
    		  "rerun:target/failed_scenarios.txt"
    } // Reporting options
)
public class TestRunner extends AbstractTestNGCucumberTests {
	
	@BeforeSuite
	public void clearAllureResults() {
		File allureResultsDir = new File("allure-results");
        if (allureResultsDir.exists()) {
            for (File file : allureResultsDir.listFiles()) {
                file.delete();
            }
        }
//        Del
		File allureReportDir = new File("allure-report");
        if (allureReportDir.exists()) {
            for (File file : allureReportDir.listFiles()) {
                file.delete();
            }
        }
		
	}	
}


 




