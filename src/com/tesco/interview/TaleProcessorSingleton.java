package com.tesco.interview;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TaleProcessorSingleton {

    // Concurrent HashMap is used to persist the word level counts from the tale input.
    // This is thread-safe.
    ConcurrentHashMap<String, Integer> wordSummaryForTale = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Integer> distinctMeaningfulWordMap = new ConcurrentHashMap<>();
    HashSet<String> wordsAlreadyProcessed = new HashSet<>();
    private static TaleProcessorSingleton taleProcessorSingletonInstance;

    // private constructor
    private TaleProcessorSingleton() {
    }

    // Synchronized method to implement InputProcessor as a Thread-Safe Double-Checking Singleton
    // Class, so that our resultant word-summary process becomes thread-safe.
    synchronized public static TaleProcessorSingleton getInstance() {
        if (taleProcessorSingletonInstance == null)
        {
            taleProcessorSingletonInstance = new TaleProcessorSingleton();
        }
        return taleProcessorSingletonInstance;
    }

    public void deriveWordSummaryFromALine (String line) {
        line = line.replaceAll("\"", " ");
        String [] wordsInTheLine = line.split ("\\s+");
        Arrays.stream(wordsInTheLine).forEach( word -> {
            if(wordSummaryForTale.containsKey(word)) {
                wordSummaryForTale.put(word, (wordSummaryForTale.get(word)+1));
            }
            else {
                wordSummaryForTale.put(word, 1);
            }
        });
    }

    public void squeezeWordSummaryBasedOnCaseInsensitivity () {
        wordSummaryForTale.entrySet().stream().forEach(wordEntry -> {
            if (wordEntry.getKey().isEmpty())
                wordSummaryForTale.remove(wordEntry.getKey());
            Integer existingCount = wordEntry.getValue();
            List<String> permutationsOfWord = permute(wordEntry.getKey());
            if (permutationsOfWord.isEmpty() == false) {
                permutationsOfWord.stream().forEach( word -> {
                    if (wordsAlreadyProcessed.contains(word) == false) {
                        Integer countForWord = wordSummaryForTale.get(word);
                        // Helps in garbage collection
                        wordSummaryForTale.remove(wordEntry.getKey());
                        wordSummaryForTale.remove(word);
                        Integer newCount = existingCount + countForWord;
                        wordSummaryForTale.put(wordEntry.getKey(), newCount);
                        wordsAlreadyProcessed.add(word);
                        wordsAlreadyProcessed.add(wordEntry.getKey());
                    }
                });
            }
        });
    }

    public List<String> permute (String input) {
        int n = input.length();
        List<String> listWithPermutations = new ArrayList<String>();

        // 1 << n - means that the value '1' is being shifted left by n bits.
        // In binary, if 1 is shifted left by n bits becomes 1 0000 0000 .... n times i.e. 2^n
        int totalNumberOfPermutations = 1 << n;

        // Converting string to lower case
        String inputLowerCase = input.toLowerCase();

        if (inputLowerCase.equals(input) == false && wordSummaryForTale.containsKey(inputLowerCase)) {
            listWithPermutations.add(inputLowerCase);
        }

        // Using all subsequences and permuting them
        for (int i = 0; i < totalNumberOfPermutations; i++) {
            char combination[] = inputLowerCase.toCharArray();
            // If j-th bit is set, we convert it to upper case
            for (int j = 0; j < n; j++) {
                if (((i >> j) & 1) == 1) {
                    combination[j] = (char) (combination[j] - 32);
                    if (String.copyValueOf(combination).equals(input) == false && wordSummaryForTale.containsKey(String.copyValueOf(combination)))
                        listWithPermutations.add(String.copyValueOf(combination));
                }
            }
        }
        return listWithPermutations;
    }

    public Integer countAndPopulateDistinctMeaningfulWords() {
        Set<String> wordKeySet = wordSummaryForTale.keySet();
        wordKeySet.stream().forEach(wordKey -> {
            final Integer[] countOfKey = {0};
            final String[] currentKey = new String[1];
            String wordWithoutTerminator = wordKey.replaceAll("[^a-zA-Z0-9'-]", "")
                                                    .toLowerCase();

            if (wordWithoutTerminator.isEmpty() == false &&
                    wordWithoutTerminator.matches(".*[a-zA-Z].*") == true &&
                    distinctMeaningfulWordMap.containsKey(wordWithoutTerminator.substring(0, 1).toUpperCase() + wordWithoutTerminator.substring(1)) == false) {
                wordKeySet.stream()
                        .forEach(word -> {
                            if (word.equals(wordKey) == false &&
                                    word.toLowerCase().contains(wordWithoutTerminator) &&
                                    word.length() > wordWithoutTerminator.length() &&
                                    Character.isLetter(word.charAt(wordWithoutTerminator.length())) == false ) {
                                countOfKey[0] += wordSummaryForTale.get(word);
                                currentKey[0] = wordWithoutTerminator;
                            }
                });
                if (currentKey[0] != null) {
                    distinctMeaningfulWordMap.put(currentKey[0].substring(0, 1).toUpperCase() + currentKey[0].substring(1), countOfKey[0]+wordSummaryForTale.get(wordKey));
                }
                else {
                    distinctMeaningfulWordMap.put(wordWithoutTerminator.substring(0, 1).toUpperCase() + wordWithoutTerminator.substring(1), wordSummaryForTale.get(wordKey));
                }
            }
        });
//        return distinctMeaningfulWordMap.size();
        return distinctMeaningfulWordMap.values().stream().reduce(0, Integer::sum);
    }

    public ConcurrentHashMap<String, Integer> getWordSummaryForTale() {
        return wordSummaryForTale;
    }

    public ConcurrentHashMap<String, Integer> getDistinctMeaningfulWordMap() {
        return distinctMeaningfulWordMap;
    }

}

