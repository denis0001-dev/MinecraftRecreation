package net.minecraftrecreation.client;

import ru.morozovit.util.program.version.Version;

import static ru.morozovit.util.program.version.VersionType.PRE_ALPHA;

public final class AppInfo {
    public static final String TITLE = "MinecraftRecreation Project";
    public static final Version VERSION = new Version(PRE_ALPHA,1,0);
    public static final String WORLD_EXTENSION = "mcrworld";

    private AppInfo() {
        throw new UnsupportedOperationException();
    }
}
