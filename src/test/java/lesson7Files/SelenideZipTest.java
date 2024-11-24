package lesson7Files;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lesson7Files.dto.Glossary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SelenideZipTest {
    ClassLoader cl = SelenideFileTest.class.getClassLoader();

    @Test
    @DisplayName("Проверка Json в Zip-архиве")
    void zipFileShouldContainsValidJson() throws IOException {
        try (
                InputStream resource = cl.getResourceAsStream("resources.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            Boolean checked = false;
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                byte[] entryData = readZipEntryData(zis);
                if (ze.getName().toLowerCase().endsWith(".json")) {
                    checkJsonFileContent(new ByteArrayInputStream(entryData), Glossary.class, glossary -> {
                        assertThat(glossary.title).isEqualTo("example glossary");
                        assertThat(glossary.glossDiv.title).isEqualTo("S");
                    });
                    checked = true;
                }
            }
            assertThat(checked).isTrue();
        }
    }

    @Test
    @DisplayName("Проверка CSV в Zip-архиве")
    void zipFileShouldContainValidCsv() throws IOException, CsvException {
        try (
                InputStream resource = cl.getResourceAsStream("resources.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            Boolean checked = false;
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                byte[] entryData = readZipEntryData(zis);
                if (ze.getName().toLowerCase().endsWith(".csv")) {
                    checkCsvFileContent(new ByteArrayInputStream(entryData), content -> {
                        assertThat(content.get(0)[0]).isEqualTo("Column1");
                        assertThat(content.get(0)[1]).isEqualTo("Column2");
                    });
                    checked = true;
                }
            }
            assertThat(checked).isTrue();
        }
    }

    private byte[] readZipEntryData(InputStream zis) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = zis.read(buffer)) > 0) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }

    private void checkCsvFileContent(InputStream is, Consumer<List<String[]>> assertions) throws IOException, CsvException {
        try (
                InputStreamReader isr = new InputStreamReader(is);
                CSVReader csvReader = new CSVReader(isr);
        ) {
            List<String[]> content = csvReader.readAll();
            assertions.accept(content);
        }
    }

    private <T> void checkJsonFileContent(InputStream is, Class<T> type, Consumer<T> assertions) throws IOException {
        try (
                InputStreamReader isr = new InputStreamReader(is);
        ) {
            Gson gson = new Gson();
            T glossary = gson.fromJson(isr, type);
            assertions.accept(glossary);
        }
    }
}