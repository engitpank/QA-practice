package lesson7Files;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lesson7Files.dto.Glossary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
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
                if (ze.getName().toLowerCase().endsWith(".json")) {
                    Gson gson = new Gson();
                    Glossary glossary = gson.fromJson(new InputStreamReader(zis), Glossary.class);
                    checked = true;

                    assertThat(glossary.title).isEqualTo("example glossary");
                    assertThat(glossary.glossDiv.title).isEqualTo("S");
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
                if (ze.getName().toLowerCase().endsWith(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    checked = true;

                    assertThat(content.get(0)[0]).isEqualTo("Column1");
                    assertThat(content.get(0)[1]).isEqualTo("Column2");
                }
            }
            assertThat(checked).isTrue();
        }
    }
}