import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Translator class loads a dictionary file and uses it to translate text
 * input based on word-to-word mappings.
 */
public final class Translator {

    /**
     * Regular expression to split the input text by words while converting
     * punctuation, such as apostrophes.
     * 
     * @see <a href=
     *      "https://stackoverflow.com/questions/21672061/splitting-words-from-text-using-regex">StackOverflow</a>
     */
    private static final String WORD_SPLIT_REGEX = "(?:(?<![a-zA-Z])'|'(?![a-zA-Z])|[^a-zA-Z'])+";

    /**
     * Stores translations. Key is the word/phrase and value is its translation.
     */
    private final Map<String, String> dictionary = new HashMap<>();

    /**
     * Loads the dictionary file and initializes translations. Each line in the file
     * should contain a word and its translation separated by " | ".
     * 
     * @param filePath the path to the dictionary file.
     * @throws InvalidFileFormatException if the file format is invalid.
     * @throws FileReadException          if the file cannot be read.
     */
    public void loadDictionaryFromFile(String filePath) throws InvalidFileFormatException, FileReadException {
        try {
            for (final var line : Files.readAllLines(Paths.get(filePath))) {
                final var parts = line.split("\\|");
                if (parts.length != 2)
                    throw new InvalidFileFormatException(
                            "Invalid format in dictionary file: " + line + ". Expected 'word | translation'");

                dictionary.put(parts[0].trim().toLowerCase(), parts[1].trim());
            }
        } catch (IOException e) {
            throw new FileReadException("Unable to read the file: " + e.getMessage());
        }
    }

    /**
     * Attempts to find the longest matching phrase in the dictionary starting from
     * the given index in the array of words. The method constructs phrases by
     * combining consecutive words from the start index onwards, and checks for the
     * existence of each phrase in the dictionary.
     *
     * @param words the array of words split from the input text.
     * @param start the starting index in the array of words from which to begin
     *              phrase matching.
     * @return the longest matching phrase found in the dictionary, or {@code null}
     *         if no match is found.
     */
    private String findLongestMatchingPhrase(String[] words, int start) {
        final var phraseBuilder = new StringBuilder();
        String longestMatchingPhrase = null;

        for (var i = start; i < words.length; i++) {
            phraseBuilder.append(words[i].toLowerCase()).append(" ");

            // Update longestMatchingPhrase if current phrase is found in the dictionary and
            // break the loop if not.
            final var currentPhrase = phraseBuilder.toString().trim();
            if (dictionary.containsKey(currentPhrase))
                longestMatchingPhrase = currentPhrase;
            else
                break;
        }

        return longestMatchingPhrase;
    }

    /**
     * Translates the given input text based on the dictionary. The method splits
     * the text into words, attempts to find the longest matching phrases from the
     * dictionary, and replaces them with their translations. If no matching phrase
     * is found, the word is left untranslated.
     *
     * The method also handles case insensitivity and ignores punctuation while
     * splitting the text.
     *
     * @param inputText the input text to be translated.
     * @return the translated text, with words/phrases replaced according to the
     *         dictionary.
     */
    public String translate(String inputText) {
        final var words = inputText.split(WORD_SPLIT_REGEX);
        final var translationResult = new StringBuilder();

        int i = 0;
        while (i < words.length) {
            final var longestMatchingPhrase = findLongestMatchingPhrase(words, i);
            if (longestMatchingPhrase != null) {
                translationResult.append(dictionary.get(longestMatchingPhrase.toLowerCase())).append(" ");
                i += longestMatchingPhrase.split(" ").length; // Move the index PAST the matched phrase.
            } else {
                translationResult.append(words[i]).append(" ");
                i++;
            }
        }

        return translationResult.toString().trim();
    }

    public static void main(String[] args) {
        final var scanner = new Scanner(System.in);

        try (scanner) {
            System.out.print("Enter the path to the dictionary file: ");
            final var dictionaryPath = scanner.nextLine();
            final var translator = new Translator();
            translator.loadDictionaryFromFile(dictionaryPath);
            System.out.println("Dictionary loaded successfully!");

            // Keep translating until the user enters "exit" or an exception is thrown.
            while (true) {
                System.out.print("Enter text to translate (or type 'exit' to quit): ");
                final var inputText = scanner.nextLine();

                if (inputText.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                final var translation = translator.translate(inputText);
                System.out.println("Translated text: " + translation);
            }
        } catch (InvalidFileFormatException | FileReadException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
