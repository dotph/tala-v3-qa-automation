package contexts;

import com.microsoft.playwright.Page;
import utils.PlaywrightUtils;

public class TestContext {

    public Page page;

    public TestContext() {
        PlaywrightUtils.init();
        this.page = PlaywrightUtils.getPage();
    }
}
