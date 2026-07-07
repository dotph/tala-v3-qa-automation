package config;

/**
 * CSS-module class-name suffixes emitted by the shared PricingBlock component
 * on cPanel / SDH / MDH landing pages. The middle of each class is a per-build
 * hash (e.g. "PricingBlock-module__MHo-mG__inclusionIncluded"); the suffix is
 * stable across builds while the hash rotates.
 *
 * Centralized here so a semantic rename by the frontend (e.g.
 * inclusionExcluded → inclusionUnavailable) is a one-line update instead of
 * having to touch every page object that asserts on plan inclusion state.
 *
 * If/when the frontend adds ARIA labels or data-testid attributes on the
 * inclusion rows, prefer those over these class-substring lookups (see
 * TALA3-XX follow-up).
 */
public final class PricingBlockClasses {

    private PricingBlockClasses() {}

    public static final String INCLUDED_SUFFIX = "inclusionIncluded";
    public static final String EXCLUDED_SUFFIX = "inclusionExcluded";
    public static final String CHECK_ICON      = "checkIcon";
    public static final String EXCLUDED_ICON   = "xIcon";
}
