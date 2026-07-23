package runner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/microsoft-365",
        glue = {"tests"},
        plugin = {"pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true
)
public class Microsoft365Runner extends BaseRunner {
}
