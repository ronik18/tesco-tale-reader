package com.tesco.interview;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileParser {

    private FileInputStream fileInputStream = null;
    private Scanner scanner = null;
    private String pathToFile;

    public FileParser(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void parseLargeTaleFileWithOutStoringInMemory() throws IOException {
        try {
            fileInputStream = new FileInputStream(pathToFile);
            scanner = new Scanner(fileInputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                TaleProcessorSingleton taleProcessorSingleton = TaleProcessorSingleton.getInstance();
                taleProcessorSingleton.deriveWordSummaryFromALine(line);
            }
            if (scanner.ioException() != null) {
                LOGGER.log(Level.WARNING, "Scanner was suppressing the raised Exception. Throwing it forcefully.");
                throw scanner.ioException();
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Input tale-file was not found.");
            LOGGER.log(Level.SEVERE, e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while reading the input tale-file.");
            LOGGER.log(Level.SEVERE, e.getMessage());
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

}
