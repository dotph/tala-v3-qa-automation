package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Path;
import java.util.Map;

public class PlaywrightUtils {
    private static final String PW_BROWSERS_PATH =
            System.getProperty("user.home") + "/.cache/pw-browsers-java";

    private static final ThreadLocal<Playwright>     TL_PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser>        TL_BROWSER    = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> TL_CONTEXT    = new ThreadLocal<>();
    private static final ThreadLocal<Page>           TL_PAGE       = new ThreadLocal<>();

    public static void init() {
        Playwright pw = Playwright.create(new Playwright.CreateOptions()
                .setEnv(Map.of("PLAYWRIGHT_BROWSERS_PATH", PW_BROWSERS_PATH)));
        Browser br = pw.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(System.getProperty("headless", "false"))));
        BrowserContext ctx = br.newContext();
        Page pg = ctx.newPage();

        TL_PLAYWRIGHT.set(pw);
        TL_BROWSER.set(br);
        TL_CONTEXT.set(ctx);
        TL_PAGE.set(pg);
    }

    public static Page getPage() {
        return TL_PAGE.get();
    }

    public static Browser getBrowser() {
        return TL_BROWSER.get();
    }

    public static void startTracing() {
        BrowserContext ctx = TL_CONTEXT.get();
        if (ctx != null) {
            ctx.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));
        }
    }

    public static void stopTracing(Path tracePath) {
        BrowserContext ctx = TL_CONTEXT.get();
        if (ctx != null) {
            ctx.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        }
    }

    public static void close() {
        try { BrowserContext ctx = TL_CONTEXT.get(); if (ctx != null) ctx.close(); } catch (Exception ignored) {}
        try { Browser br = TL_BROWSER.get();         if (br != null) br.close();  } catch (Exception ignored) {}
        try { Playwright pw = TL_PLAYWRIGHT.get();   if (pw != null) pw.close();  } catch (Exception ignored) {}
        TL_CONTEXT.remove();
        TL_BROWSER.remove();
        TL_PLAYWRIGHT.remove();
        TL_PAGE.remove();
    }

}
