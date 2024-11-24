package lesson7Files;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lesson7Files.dto.Glossary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFileTest {
    ClassLoader cl = SelenideFileTest.class.getClassLoader();

    @Test
    @DisplayName("Простой пример текст текстового файла")
    void selenideDownloadTest() throws IOException {
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadedFile = $("[data-testid=raw-button]").download();
        try (InputStream is = new FileInputStream(downloadedFile)) {
            byte[] bytes = is.readAllBytes();
            String textContent = new String(bytes, StandardCharsets.UTF_8);
            assertThat(textContent).contains("This repository is the home of _JUnit 5_.");
        }
    }

    @Test
    @DisplayName("Простой пример текст pdf-файла")
    void testPdf() throws IOException {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloadedPdf = Selenide.$(Selectors.byText("PDF download")).download();
        PDF content = new PDF(downloadedPdf);
        assertThat(content.author).contains("Sam Brannen");
    }

    @Test
    @DisplayName("Простой пример текст csv-файла")
    void testCsv() throws IOException, CsvException {
        try (
                InputStream resource = cl.getResourceAsStream("qa.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(resource))
        ) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(0)[1]).isEqualTo("Column2");
        }
    }

    @Test
    @DisplayName("Простой пример текст zip-архива")
    void testZip() throws IOException {
        try (
                InputStream resource = cl.getResourceAsStream("qa.zip");
                ZipInputStream zis = new ZipInputStream(resource);
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).isEqualTo("qa.csv");
            }
        }
    }

    @Test
    @DisplayName("Простой пример текст json-файла")
    void testJson() throws IOException {
        try (
                InputStream resource = cl.getResourceAsStream("example.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            Gson gson = new Gson();
            JsonObject object = gson.fromJson(reader, JsonObject.class);
            assertThat(object.get("title").getAsString()).isEqualTo("example glossary");
            assertThat(object
                    .get("GlossDiv").getAsJsonObject()
                    .get("GlossList").getAsJsonObject()
                    .get("GlossEntry").getAsJsonObject()
                    .get("GlossTerm").getAsString()
            ).isEqualTo("Standard Generalized Markup Language");
        }
    }

    @Test
    @DisplayName("Простой пример текст json-файла c десериализацией из POJO")
    void testImprovedJson() throws IOException {
        try (
                InputStream resource = cl.getResourceAsStream("example.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            Gson gson = new Gson();
            Glossary glossary = gson.fromJson(reader, Glossary.class);
            assertThat(glossary.title).isEqualTo("example glossary");
            assertThat(glossary.glossDiv.title).isEqualTo("S");
        }
    }
}
