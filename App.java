import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.swing.*;

public final class App {

    public static void main(String[] args) {
        final var frame = new JFrame("Course Work Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        // Create Tabs for each task
        final var tabbedPane = new JTabbedPane();

        // Create Panels for each task
        final var strategyPanel = createStrategyPanel();
        final var annotationPanel = createAnnotationPanel();
        final var translatorPanel = createTranslatorPanel();
        final var streamPanel = createStreamPanel();

        // Add panels to tabs
        tabbedPane.addTab("Strategy", strategyPanel);
        tabbedPane.addTab("Annotations", annotationPanel);
        tabbedPane.addTab("Translator", translatorPanel);
        tabbedPane.addTab("Stream", streamPanel);

        // Add tabbedPane to frame
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JPanel createStrategyPanel() {
        final var hero = new Hero(new WalkingStrategy());

        final var panel = new JPanel();
        final var outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        final var walkingRadio = new JRadioButton("Walking");
        final var horseRidingRadio = new JRadioButton("Horse riding");
        final var flyingRadio = new JRadioButton("Flying");

        final var group = new ButtonGroup();
        group.add(walkingRadio);
        group.add(horseRidingRadio);
        group.add(flyingRadio);

        // Action listeners to change strategy and update text area immediately
        walkingRadio.addActionListener(e -> {
            hero.setMovementStrategy(new WalkingStrategy());
            outputArea.setText("Current strategy: " + hero.move() + "\n");
        });

        horseRidingRadio.addActionListener(e -> {
            hero.setMovementStrategy(new HorseRidingStrategy());
            outputArea.setText("Current strategy: " + hero.move() + "\n");
        });

        flyingRadio.addActionListener(e -> {
            hero.setMovementStrategy(new FlyingStrategy());
            outputArea.setText("Current strategy: " + hero.move() + "\n");
        });

        panel.add(walkingRadio);
        panel.add(horseRidingRadio);
        panel.add(flyingRadio);
        panel.add(new JScrollPane(outputArea));

        return panel;
    }

    private static JPanel createAnnotationPanel() {
        final var panel = new JPanel();
        final var outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        final var executeButton = new JButton("Run Annotation");

        // Action listener to invoke annotated methods and display output
        executeButton.addActionListener(e -> {
            // Capture system output as it's the easiest way to avoid code duplication :)
            final var outputStream = new ByteArrayOutputStream();
            final var printStream = new PrintStream(outputStream);
            final var originalOut = System.out;

            // Redirect system output
            System.setOut(printStream);
            // Invoke annotated methods in TestClass
            AnnotationsDemo.invokeAnnotatedMethods(TestClass.class);
            // Restore original system output
            System.setOut(originalOut);
            // Display the captured output in JTextArea
            outputArea.setText(outputStream.toString());
        });

        panel.add(new JScrollPane(outputArea));
        panel.add(executeButton);
        return panel;
    }

    private static JPanel createTranslatorPanel() {
        final var panel = new JPanel();
        final var dictionaryStatusArea = new JTextArea(5, 30);
        final var inputArea = new JTextArea(5, 30);
        final var outputArea = new JTextArea(5, 30);

        dictionaryStatusArea.setEditable(false);
        outputArea.setEditable(false);

        final var translator = new Translator();
        final var chooseFileButton = new JButton("Choose Dictionary");

        chooseFileButton.addActionListener(e -> {
            final var fileChooser = new JFileChooser();
            final var result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File dictionaryFile = fileChooser.getSelectedFile();
                try {
                    translator.loadDictionaryFromFile(dictionaryFile.getAbsolutePath());
                    dictionaryStatusArea.setText("Dictionary loaded successfully from: " + dictionaryFile.getName());
                } catch (InvalidFileFormatException | FileReadException ex) {
                    dictionaryStatusArea.setText("Error: " + ex.getMessage());
                }
            }
        });

        final var translateButton = new JButton("Translate");
        translateButton.addActionListener(e -> {
            final var inputText = inputArea.getText();
            if (inputText.isEmpty()) {
                outputArea.setText("Please enter text to translate.");
            } else {
                final var translation = translator.translate(inputText);
                outputArea.setText(translation);
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Dictionary Status:"));
        panel.add(new JScrollPane(dictionaryStatusArea));
        panel.add(chooseFileButton);
        panel.add(new JLabel("Input Text:"));
        panel.add(new JScrollPane(inputArea));
        panel.add(translateButton);
        panel.add(new JLabel("Translated Output:"));
        panel.add(new JScrollPane(outputArea));

        return panel;
    }

    private static JPanel createStreamPanel() {
        final var panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Input fields for lists of numbers and strings
        final var numbersInput = new JTextField(30);
        final var stringsInput = new JTextField(30);
        final var numbersLabel = new JLabel("Enter a list of numbers (comma-separated): ");
        final var stringsLabel = new JLabel("Enter a list of strings (comma-separated): ");

        // Result display area
        final var resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        final var resultScrollPane = new JScrollPane(resultArea);

        // Panel to hold input fields
        final var inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1));
        inputPanel.add(numbersLabel);
        inputPanel.add(numbersInput);
        inputPanel.add(stringsLabel);
        inputPanel.add(stringsInput);

        // Buttons for each stream method
        final var averageButton = new JButton("Calculate Average");
        final var uniqueSquaresButton = new JButton("Calculate Unique Squares");
        final var sumOfEvensButton = new JButton("Sum of Even Numbers");
        final var convertButton = new JButton("Convert to Uppercase with Prefix");
        final var transformMapButton = new JButton("Transform to Map");
        final var lastElementButton = new JButton("Get Last Element");

        // Panel to hold buttons
        final var buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));
        buttonPanel.add(averageButton);
        buttonPanel.add(convertButton);
        buttonPanel.add(uniqueSquaresButton);
        buttonPanel.add(lastElementButton);
        buttonPanel.add(sumOfEvensButton);
        buttonPanel.add(transformMapButton);

        // Add input panel and button panel to the main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(resultScrollPane, BorderLayout.SOUTH);

        // Action listener for Calculate Average button
        averageButton.addActionListener(e -> {
            try {
                final var numbersText = numbersInput.getText().trim();
                if (numbersText.isEmpty()) {
                    resultArea.setText("Error: Number input is empty.");
                    return;
                }
                final var numbers = parseIntegerList(numbersText);
                final var average = StreamAPIExamples.averageValue(numbers);
                resultArea.setText("Average value: " + average);
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Invalid number format. " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        // Action listener for Convert to Uppercase with Prefix button
        convertButton.addActionListener(e -> {
            final var stringsText = stringsInput.getText().trim();
            if (stringsText.isEmpty()) {
                resultArea.setText("Error: String input is empty.");
                return;
            }
            final var strings = parseStringList(stringsText);
            final var transformedStrings = StreamAPIExamples.convertToUpperCaseWithPrefix(strings);
            resultArea.setText("Transformed strings: " + transformedStrings);
        });

        // Action listener for Calculate Unique Squares button
        uniqueSquaresButton.addActionListener(e -> {
            try {
                final var numbersText = numbersInput.getText().trim();
                if (numbersText.isEmpty()) {
                    resultArea.setText("Error: Number input is empty.");
                    return;
                }
                final var numbers = parseIntegerList(numbersText);
                final var uniqueSquares = StreamAPIExamples.uniqueSquares(numbers);
                resultArea.setText("Unique squares: " + uniqueSquares);
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Invalid number format. " + ex.getMessage());
            }
        });

        // Action listener for Get Last Element button
        lastElementButton.addActionListener(e -> {
            final var stringsText = stringsInput.getText().trim();
            if (stringsText.isEmpty()) {
                resultArea.setText("Error: String input is empty.");
                return;
            }
            final var strings = parseStringList(stringsText);
            try {
                final var lastElement = StreamAPIExamples.getLastElement(strings);
                resultArea.setText("Last element: " + lastElement);
            } catch (NoSuchElementException ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        // Action listener for Sum of Even Numbers button
        sumOfEvensButton.addActionListener(e -> {
            try {
                final var numbersText = numbersInput.getText().trim();
                if (numbersText.isEmpty()) {
                    resultArea.setText("Error: Number input is empty.");
                    return;
                }
                final var numbers = parseIntegerList(numbersText);
                final var sumOfEvens = StreamAPIExamples.sumOfEvens(numbers.stream().mapToInt(i -> i).toArray());
                resultArea.setText("Sum of even numbers: " + sumOfEvens);
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Invalid number format. " + ex.getMessage());
            }
        });

        // Action listener for Transform to Map button
        transformMapButton.addActionListener(e -> {
            final var stringsText = stringsInput.getText().trim();
            if (stringsText.isEmpty()) {
                resultArea.setText("Error: String input is empty.");
                return;
            }
            final var strings = parseStringList(stringsText);
            final var transformedMap = StreamAPIExamples.transformToMap(strings);
            resultArea.setText("Transformed map: " + transformedMap);
        });

        return panel;
    }

    // Helper method to parse comma-separated integers from input string.
    private static List<Integer> parseIntegerList(String input) throws NumberFormatException {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    // Helper method to parse comma-separated strings from input string.
    private static List<String> parseStringList(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

}
