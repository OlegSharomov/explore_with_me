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

    private static final String FILE_WITH_STOP_WORDS = "ewm/BAD_WORDS";
    private String[] stopWords;

//    public static void main(String[] args) {
//        Censorship censorship = new Censorship();
//        censorship.readStopWordsFromFile(FILE_WITH_STOP_WORDS);
//        for(String s : censorship.stopWords){
//            System.out.println(s);
//        }
//    }

    // проверка текста на наличие стоп-слов
    public boolean isTextCorrect(String text) {
        for (String stopWord : stopWords) {
            String searchWord = "(^|[_ ])" + stopWord + "([ _]|$)";
//            if (Pattern.compile(Pattern.quote(stopWord), Pattern.CASE_INSENSITIVE).matcher(text).find()) {
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
        writeByteToFile(stopWords);
        return Arrays.copyOf(stopWords, stopWords.length);
    }

    public String[] removeFromStopWords(String wordToRemove) {
        if (Boolean.FALSE.equals(Arrays.asList(stopWords).contains(wordToRemove))) {
            throw new ValidationException(String.format("Word %s not found", wordToRemove));
        }
        stopWords = Arrays.stream(stopWords)
                .filter(e -> Boolean.FALSE.equals(e.equals(wordToRemove)))
                .toArray(String[]::new);
        writeByteToFile(stopWords);
        return Arrays.copyOf(stopWords, stopWords.length);
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
        File csvOutputFile = new File(FILE_WITH_STOP_WORDS);
        // Переводим массив слов в строку с учетом экранирования и специальных символов
        String readyString = convertToCSV(arrayOfBadWords);
        // перевод строки в байты, кодировки Base64
        byte[] bytesEncoded = Base64.encodeBase64(readyString.getBytes());
        try (FileOutputStream fos = new FileOutputStream(csvOutputFile, false)) {
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
