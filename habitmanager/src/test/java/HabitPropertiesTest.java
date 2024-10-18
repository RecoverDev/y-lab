import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

import ru.list.HabitProperties;

public class HabitPropertiesTest {

    @Test
    @DisplayName("Загрузка файла")
    public void habitPropertiesOpenFile() {
        String nameFile = "application.properties";
        HabitProperties properties = new HabitProperties(nameFile);

        boolean result = properties.load();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Получение значений")
    public void habitPropertiesGetValue() {
        String nameFile = "application.properties";
        HabitProperties properties = new HabitProperties(nameFile);
        properties.load();

        assertThat("postgres").hasToString(properties.getUser());

    }



}
