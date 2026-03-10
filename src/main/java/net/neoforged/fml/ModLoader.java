package net.neoforged.fml;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Proxy: NeoForge FML's ModLoader — central mod loading coordinator.
 *
 * <p>NeoForge mods call static methods on this class to report loading issues
 * and post events. This stub provides the required API surface so that
 * mod code referencing ModLoader does not crash with ClassNotFoundException.</p>
 */
public final class ModLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final List<ModLoadingIssue> LOADING_ISSUES = new CopyOnWriteArrayList<>();

    private ModLoader() {}

    /**
     * Add a loading issue (warning or error) reported by a mod.
     */
    public static void addLoadingIssue(ModLoadingIssue issue) {
        LOADING_ISSUES.add(issue);
        LOGGER.warn("[ReForged] ModLoader.addLoadingIssue: {}", issue);
    }

    /**
     * Get all loading issues reported so far.
     */
    public static List<ModLoadingIssue> getLoadingIssues() {
        return Collections.unmodifiableList(LOADING_ISSUES);
    }

    /**
     * Check if any loading errors (not just warnings) have been reported.
     */
    public static boolean hasErrors() {
        return LOADING_ISSUES.stream()
                .anyMatch(i -> i.severity() == ModLoadingIssue.Severity.ERROR);
    }

    /**
     * Post an event to the mod event bus. No-op in ReForged bridge — events are
     * bridged through the NeoForge event adapter, not through this class.
     */
    public static void postEvent(Object event) {
        LOGGER.debug("[ReForged] ModLoader.postEvent (no-op): {}", event.getClass().getSimpleName());
    }

    /**
     * Post an event wrapping each mod container. No-op stub.
     */
    public static void postEventWrapContainerInModOrder(Object event) {
        LOGGER.debug("[ReForged] ModLoader.postEventWrapContainerInModOrder (no-op): {}", event.getClass().getSimpleName());
    }
}
