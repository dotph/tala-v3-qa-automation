package runner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/ssl",
        glue = {"tests"},
        plugin = {"pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true
)
public class SslRunner extends BaseRunner {
}
