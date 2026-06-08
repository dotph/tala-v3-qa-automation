package tests;

import com.microsoft.playwright.Page;
import contexts.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.PlaywrightUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    private static final String SCREENSHOTS_DIR = "screenshots";
    private static final String TRACES_DIR      = "traces";
    private static final DateTimeFormatter TIMESTAMP_FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DATE_FOLDER_FMT = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before(order = 1000)
    public void setUp(Scenario scenario) {
        String source = scenario.getUri().toString();
        String featureFile = source.contains("/") ? source.substring(source.lastIndexOf("/") + 1) : source;
        log.info("Starting scenario: [{}] | Tags: {} | Source: {}",
                scenario.getName(), scenario.getSourceTagNames(), featureFile);
        PlaywrightUtils.startTracing();
    }

    @After
    public void tearDown(Scenario scenario) {
        // ── Trace ──────────────────────────────────────────────────────────
        try {
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
            String safeName  = scenario.getName().replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String traceFile = timestamp + "_" + safeName + ".zip";

            Path tracesDir = Paths.get(TRACES_DIR, LocalDateTime.now().format(DATE_FOLDER_FMT));
            Files.createDirectories(tracesDir);
            Path tracePath = tracesDir.resolve(traceFile);

            PlaywrightUtils.stopTracing(tracePath);

            byte[] traceBytes = Files.readAllBytes(tracePath);
            Allure.addAttachment(scenario.getName() + " trace", "application/zip",
                    new ByteArrayInputStream(traceBytes), ".zip");

            log.info("Trace saved: {}", tracePath.toAbsolutePath());
        } catch (Exception e) {
            log.error("Failed to save trace: {}", e.getMessage());
        }

        // ── Screenshot on failure ──────────────────────────────────────────
        try {
            if (scenario.isFailed()) {
                Page page = context.page;
                if (page != null && !page.isClosed()) {
                    String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
                    String safeName  = scenario.getName().replaceAll("[^a-zA-Z0-9_\\-]", "_");
                    String fileName  = timestamp + "_" + safeName + ".png";

                    Path dir      = Paths.get(SCREENSHOTS_DIR, LocalDateTime.now().format(DATE_FOLDER_FMT));
                    Files.createDirectories(dir);
                    Path filePath = dir.resolve(fileName);

                    byte[] screenshot = page.screenshot(
                            new Page.ScreenshotOptions().setPath(filePath).setFullPage(true));

                    Allure.addAttachment(scenario.getName(), "image/png",
                            new ByteArrayInputStream(screenshot), ".png");

                    log.error("Screenshot saved on failure: {}", filePath.toAbsolutePath());
                }
            }
        } catch (IOException e) {
            log.error("Failed to save screenshot: {}", e.getMessage());
        }
    }

    @AfterAll
    public static void closeBrowser() {
        LogManager.getLogger(Hooks.class).info("Closing browser session");
        PlaywrightUtils.close();
    }
}
