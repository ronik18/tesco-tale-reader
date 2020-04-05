package com.tesco.interview;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaleReaderOrchestrator {

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        readTale(args[0]);
    }

    public static void readTale(String pathToFile) {
        FileParser fileParser = new FileParser(pathToFile);
        try {
            fileParser.parseLargeTaleFileWithOutStoringInMemory();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while reading the input tale-file.");
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        try {
            TaleProcessorSingleton taleProcessorSingleton = TaleProcessorSingleton.getInstance();
            taleProcessorSingleton.squeezeWordSummaryBasedOnCaseInsensitivity();
            Integer countDistinctWord = taleProcessorSingleton.countAndPopulateDistinctMeaningfulWords();
            System.out.println("********* Tale Reader Output *********");
            System.out.println("The document contains " + countDistinctWord + " words.");
            taleProcessorSingleton.getDistinctMeaningfulWordMap().entrySet().stream().forEach(entry -> {
                System.out.println("\"" + entry.getKey() + "\"" + " has been found " + entry.getValue() + " times.");
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while reading the input tale-file.");
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
