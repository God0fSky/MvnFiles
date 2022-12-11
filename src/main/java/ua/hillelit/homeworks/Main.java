package ua.hillelit.homeworks;

import ua.hillelit.homeworks.exceptions.FileMaxSizeReachedException;
import ua.hillelit.homeworks.loggers.FileLogger.FileLogger;
import ua.hillelit.homeworks.loggers.FileLogger.FileLoggerConfiguration;
import ua.hillelit.homeworks.loggers.FileLogger.FileLoggerConfigurationLoader;
import ua.hillelit.homeworks.loggers.FileLogger.LoggingLevel;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File file = new File("file.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.length());
        FileLoggerConfiguration config =
                new FileLoggerConfiguration(file.getName(), LoggingLevel.INFO.toString(),
                        300, "%1$s %2$s ��������� : %3$s\n");
        try {
            FileLogger.info("hello", config);
        } catch (FileMaxSizeReachedException e) {
            e.getMessage();
        }
        try {
            FileLogger.debug("hi", config);
        } catch (FileMaxSizeReachedException e) {
            System.out.println(e.getMessage() + " ������������ ������ �����: " + e.getMaxSize() +
                    ", ������� ������ �����: "  + e.getCurrentSize() + ", ���� � �����: " + e.getFilePath());
        }
        FileLoggerConfigurationLoader fileLoggerConfigurationLoader =
                new FileLoggerConfigurationLoader();
        fileLoggerConfigurationLoader.load("configurations.txt");
    }
}
