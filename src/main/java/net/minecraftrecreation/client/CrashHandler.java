package net.minecraftrecreation.client;

import ru.morozovit.logging.CrashReport;
import ru.morozovit.util.ExcParser;
import ru.morozovit.util.io.FileUtil;

import java.io.IOException;
import java.util.UUID;

import static net.minecraftrecreation.client.AppInfo.TITLE;
import static net.minecraftrecreation.client.Main.logger;
import static ru.morozovit.logging.Loglevel.*;

public final class CrashHandler {
    public static void crash() {
        crash("An unknown error has occurred.", new Exception("Unknown"));
    }

    public static void crash(Throwable e) {
        crash("An unexpected error has occurred.\n %s".formatted(new ExcParser(e).toString()), e);
    }

    public static void crash(String message, Throwable e) {
        logger.log(FATAL,message);
        UUID uuid = UUID.randomUUID();

        logger.log(INFO, "Preparing crash report with UUID %s".formatted(uuid.toString()));

        CrashReport crashReport = new CrashReport(TITLE,new String[]{"My bad.", "I'm so sorry :("},e);

        String report = crashReport.build(uuid);

        logger.log(FATAL, "\n%s".formatted(report));

        try {
            FileUtil.writeToFile(FileUtil.token.WRITE, "latest_crash.txt",report);
        } catch (IOException ex) {
            logger.log(WARN, "Failed to write crash report to latest_crash.txt");
        }
    }
}
