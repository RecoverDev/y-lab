import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import ru.list.ApplicationData;
import ru.list.Controller.PersonController;

public class ApplicationDataTest {

    @Test
    @DisplayName("Получение ссылки на PersonController")
    public void getPersonControllerTest() {

        ApplicationData applicationData = ApplicationData.getInstance();

        PersonController controller = applicationData.getPersonController();

        assertThat(controller).isNotNull();
    }

}
