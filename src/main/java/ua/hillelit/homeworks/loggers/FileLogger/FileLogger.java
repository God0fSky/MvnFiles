package ua.hillelit.homeworks.loggers.FileLogger;

import ua.hillelit.homeworks.exceptions.FileMaxSizeReachedException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class FileLogger {
    public static void info(String message, FileLoggerConfiguration config) {
        fileSizeValidator(config);
        doLogg(config, message, config.getLoggingLevel());
    }

    public static void debug(String message, FileLoggerConfiguration config) {
        fileSizeValidator(config);

        doLogg(config, message, config.getLoggingLevel());
        info(message, config);
    }

    public static void doLogg(FileLoggerConfiguration config, String message, String ll) {
        try (FileOutputStream os = new FileOutputStream(config.getFileName(), true);){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            os.write(String.format(config.getFormat(), ZonedDateTime.now().
                    format(formatter), ll, message).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileSizeValidator(FileLoggerConfiguration config) {
        if(config.getMaxFileSize() <= config.getFileName().length()) {
            throw new FileMaxSizeReachedException("Файл переполнен!", config.getMaxFileSize(),
                    config.getFileName().length(), config.getFileName());
        }
    }
}
