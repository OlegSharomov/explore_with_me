package ru.practicum.comments.censorship;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import ru.practicum.exception.ValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Censorship {
    public Censorship() {
        this.stopWords = readStopWordsFromFile(FILE_WITH_STOP_WORDS);
    }

    private static final String FILE_WITH_STOP_WORDS = "BAD_WORDS";
    private String[] stopWords;

    // проверка текста на наличие стоп-слов
    public boolean isTextCorrect(String text) {
        for (String stopWord : stopWords) {
//          if (text.contains(stopWord)) {return false;}
            if (Pattern.compile(Pattern.quote(stopWord), Pattern.CASE_INSENSITIVE).matcher(text).find()) {
                return false;
            }
        }
        return true;
    }

    public void addStopWord(String newStopWord) {
        String readyStopWord = newStopWord.trim();
        if (Arrays.asList(stopWords).contains(readyStopWord)) {
            throw new ValidationException(String.format("Word %s already exists", newStopWord));
        }
        String[] updatedStopWords = new String[stopWords.length + 1];
        System.arraycopy(stopWords, 0, updatedStopWords, 0, stopWords.length);
        updatedStopWords[updatedStopWords.length - 1] = readyStopWord;

        stopWords = updatedStopWords;
        writeByteToFile(stopWords);
    }

    public void removeFromStopWords(String wordToRemove) {
        if (Boolean.FALSE.equals(Arrays.asList(stopWords).contains(wordToRemove))) {
            throw new ValidationException(String.format("Word %s not found", wordToRemove));
        }
        stopWords = Arrays.stream(stopWords).filter(e -> e.equals(wordToRemove)).toArray(String[]::new);
        writeByteToFile(stopWords);
    }


    private String[] readStopWordsFromFile(String fileName) {
        byte[] bytesEncoded;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            // Читаем значение байтов в кодировке Base64
            bytesEncoded = fis.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new ValidationException(String.format("File %s not found", FILE_WITH_STOP_WORDS));
        } catch (IOException e) {
            throw new ValidationException("Can't read from the file" + FILE_WITH_STOP_WORDS);
        }
        // Переводим байты в кодировке Base64 в стандартное значение
        byte[] valueDecoded = Base64.decodeBase64(bytesEncoded);
        // Переводим массив байтов в массив строк и возвращаем значение
        return new String(valueDecoded).split(",");
    }

    private void writeByteToFile(String[] arrayOfBadWords) {
        File csvOutputFile = new File("BAD_WORDS");
        try (FileOutputStream fos = new FileOutputStream(csvOutputFile, false)) {
            // Переводим массив слов в строку с учетом экранирования и специальных символов
            String readyString = convertToCSV(arrayOfBadWords);
            // перевод строки в байты, кодировки Base64
            byte[] bytesEncoded = Base64.encodeBase64(readyString.getBytes());

            // записываем массив байтов в файл BAD_WORDS
            fos.write(bytesEncoded, 0, bytesEncoded.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
