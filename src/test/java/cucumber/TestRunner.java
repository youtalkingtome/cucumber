package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // path to your feature files
        glue = "com.example.stepdefinitions", // path to your step definitions
        plugin = {"pretty", "html:target/cucumber-report.html"} // plugins for output formatting
)
public class TestRunner {
    // No methods needed; this class is only a configuration holder
}
