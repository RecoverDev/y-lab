import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.list.ApplicationData;
import ru.list.Controller.AutorizationController;
import ru.list.Servlet.HabitServlet;

public class HabitServletTest {

    @Mock
    HttpServletRequest requestMockito;

    @Mock
    HttpServletResponse responseMockito;


    @Test
    @DisplayName("Получение списка привычек")
    public void doGetHabbitsTest() throws ServletException, IOException {
        requestMockito = Mockito.mock(HttpServletRequest.class);
        responseMockito = Mockito.mock(HttpServletResponse.class);
        
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMockito.getWriter()).thenReturn(writer);

        ApplicationData applicationData = ApplicationData.getInstance();
        AutorizationController autorizationController = applicationData.getAutorizationController();
        autorizationController.login("first@server.com", "111");

        HabitServlet servlet = new HabitServlet();
        servlet.doGet(requestMockito, responseMockito);

        String result = """
            [
                {
                    "id":1,
                    "name":"read book",
                    "description":"read book every day",
                    "person_id":1,
                    "period_id":0,
                    "registration":"2024-10-10"
                },
                {
                    "id":2,
                    "name":"morning fitness",
                    "description":"weekly fitness",
                    "person_id":1,
                    "period_id":1,
                    "registration":"2024-10-11"
                }
            ]
                """;

        assertThat(stringWriter.toString()).isEqualTo(result);

    }

    @Test
    @DisplayName("Добавление привычки")
    public void doPostHabitTest() throws ServletException, IOException {
        String jsonHabit = """
            {
                "id":7,
                "name":"test habit",
                "description":"test every day",
                "person_id":1,
                "period_id":1,
                "registration":"2024-10-11"
            }
            """;
        MockHttpServletRequest requestMockito = new MockHttpServletRequest();
        requestMockito.setParameter("habit", jsonHabit);
        MockHttpServletResponse responseMockito = new MockHttpServletResponse();

        HabitServlet servlet = new HabitServlet();
        servlet.doPost(requestMockito, responseMockito);

        assertThat(responseMockito.getStatus()).isEqualTo(201);
    }

    @Test
    @DisplayName("Удаление привычки")
    public void doDeleteHabitTest() throws ServletException, IOException {
        MockHttpServletRequest requestMockito = new MockHttpServletRequest();
        requestMockito.setParameter("id", "2");
        MockHttpServletResponse responseMockito = new MockHttpServletResponse();

        HabitServlet servlet = new HabitServlet();
        servlet.doDelete(requestMockito, responseMockito);

        assertThat(responseMockito.getStatus()).isEqualTo(200);

    }

}
