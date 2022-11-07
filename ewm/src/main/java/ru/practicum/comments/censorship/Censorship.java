package ru.practicum.comments.censorship;

import org.springframework.stereotype.Component;
import ru.practicum.exception.ValidationException;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Решил хранить СТОП-СЛОВА в файле BAD_WORDS в 16-ричном формате,
// чтобы не были видны критичные слова при сдаче проекта. И поиграться заодно.
@Component
public class Censorship {
    public Censorship() {
        this.stopWords = readStopWordsFromFile(FILE_WITH_STOP_WORDS);
    }

    private static final String FILE_WITH_STOP_WORDS = "ewm/BAD_WORDS";
    private String[] stopWords;

    // проверка текста на наличие стоп-слов
    public boolean isTextCorrect(String text) {
        for (String stopWord : stopWords) {
            String searchWord = "(^|[_ ])" + stopWord + "([ _]|$)";
            if (Pattern.compile(String.valueOf(Pattern.compile(searchWord)), Pattern.CASE_INSENSITIVE).matcher(text).find()) {
                return false;
            }
        }
        return true;
    }

    public String[] showAllStopWords() {
        return Arrays.copyOf(stopWords, stopWords.length);
    }

    public String[] addStopWord(String newStopWord) {
        String readyStopWord = newStopWord.trim();
        if (Arrays.asList(stopWords).contains(readyStopWord)) {
            throw new ValidationException(String.format("Word %s already exists", newStopWord));
        }
        String[] updatedStopWords = new String[stopWords.length + 1];
        System.arraycopy(stopWords, 0, updatedStopWords, 0, stopWords.length);
        updatedStopWords[updatedStopWords.length - 1] = readyStopWord;

        stopWords = updatedStopWords;
        writeStopWordsToFile(stopWords);
        return Arrays.copyOf(stopWords, stopWords.length);
    }

    public String[] removeFromStopWords(String wordToRemove) {
        if (Boolean.FALSE.equals(Arrays.asList(stopWords).contains(wordToRemove))) {
            throw new ValidationException(String.format("Word %s not found", wordToRemove));
        }
        stopWords = Arrays.stream(stopWords)
                .filter(e -> Boolean.FALSE.equals(e.equals(wordToRemove)))
                .toArray(String[]::new);
        writeStopWordsToFile(stopWords);
        return Arrays.copyOf(stopWords, stopWords.length);
    }

    private String[] readStopWordsFromFile(String fileName) {
        String hexBinaryString;
        try {
            // Считываем строку из файла в кодировке HexBinary
            BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
            hexBinaryString = reader.readLine();
            reader.close();
        } catch (FileNotFoundException e) {
            throw new ValidationException(String.format("File %s not found", FILE_WITH_STOP_WORDS));
        } catch (IOException e) {
            throw new ValidationException("Can't read from the file" + FILE_WITH_STOP_WORDS);
        }
        // Переводим строку из кодировки HexBinary в массив байтов стандартного значения
        byte[] valueDecoded = DatatypeConverter.parseHexBinary(hexBinaryString);
        // Переводим массив байтов в массив строк, разделенных запятой и возвращаем значение
        return new String(valueDecoded, StandardCharsets.UTF_8).split(",");
    }

    private void writeStopWordsToFile(String[] arrayOfBadWords) {
        File csvOutputFile = new File(FILE_WITH_STOP_WORDS);
        // Переводим массив стоп-слов в строку с учетом экранирования и специальных символов
        String readyString = convertToCSV(arrayOfBadWords);
        // кодируем строку, кодировка HexBinary
        String encodedString = DatatypeConverter.printHexBinary(readyString.getBytes(StandardCharsets.UTF_8));
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(csvOutputFile,
                    StandardCharsets.UTF_8, false));
            br.write(encodedString);
            br.close();
        } catch (FileNotFoundException e) {
            throw new ValidationException(String.format("File %s not found", FILE_WITH_STOP_WORDS));
        } catch (IOException e) {
            throw new ValidationException("Can't read from the file" + FILE_WITH_STOP_WORDS);
        }
    }

    // Метод для форматирования строки данных, представленной в виде массива String
    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    // Создание строки для файла с учетом экранирования и специальных символов
    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
