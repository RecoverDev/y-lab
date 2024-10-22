import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import ru.list.logger.SaveLogToFile;

public class SaveToFileTest {

    @Test
    @DisplayName("Запись в файл коллекции строк")
    public void uotTest() throws IOException {
        List<String> collection = List.of(
            "Первая строка",
            "Вторая строка",
            "Третья строка"
        );

        SaveLogToFile logToFile = new SaveLogToFile();
        logToFile.out(collection);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm");
        Path path = Paths.get("log_" +LocalDateTime.now().format(formatter)+".log");
        assertThat(Files.exists(path)).isTrue();
        assertThat(Files.readAllLines(path).size()).isEqualTo(3);
    }

}
