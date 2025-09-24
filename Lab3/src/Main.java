import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dictionary dictionary = new Dictionary();

        try {
            dictionary.loadFromFile("src/dictionary.txt");
        } catch (InvalidFileFormatException | FileReadException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return;
        }

        Translator translator = new Translator(dictionary);

        System.out.println("Введите текст для перевода:");
        String inputText = scanner.nextLine();

        String translatedText = translator.translate(inputText);
        System.out.println("Перевод: " + translatedText);
    }
}

class Dictionary {
    private Map<String, String> translations;

    public Dictionary() {
        this.translations = new HashMap<>();
    }

    public void loadFromFile(String filePath) throws InvalidFileFormatException, FileReadException {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length != 2) {
                    throw new InvalidFileFormatException("Неверный формат файла: " + line);
                }
                translations.put(parts[0].trim().toLowerCase(), parts[1].trim());
            }
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public Map<String, String> getTranslations() {
        return this.translations;
    }
}

class Translator {
    private final Map<String, String> translations;

    public Translator(Dictionary dictionary) {
        translations = dictionary.getTranslations();
    }

    public String translate(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        StringBuilder translatedText = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String wordToTranslate = null;
            String maxLengthTranslation = words[i];

            for (int j = 1; j <= words.length - i; j++) {
                String phrase = String.join(" ", Arrays.copyOfRange(words, i, i + j));
                if (translations.containsKey(phrase)) {
                    wordToTranslate = translations.get(phrase);
                    maxLengthTranslation = phrase;
                }
            }

            translatedText.append(wordToTranslate != null ? wordToTranslate : maxLengthTranslation).append(" ");
            i += maxLengthTranslation.split("\\s+").length - 1;
        }
        return translatedText.toString().trim();
    }
}

class FileReadException extends RuntimeException {
    public FileReadException(String message) {
        super(message);
    }
}

class InvalidFileFormatException extends RuntimeException {
    public InvalidFileFormatException(String message) {
        super(message);
    }
}