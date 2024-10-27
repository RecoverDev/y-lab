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
import ru.list.Servlet.PersonServlet;

public class PersonServletTest {

    @Mock
    HttpServletRequest requestMockito;

    @Mock
    HttpServletResponse responseMockito;

    @Test
    @DisplayName("Получение списка пользователей")
    public void doGetPersonsTest() throws ServletException, IOException {
        requestMockito = Mockito.mock(HttpServletRequest.class);
        responseMockito = Mockito.mock(HttpServletResponse.class);
        
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(responseMockito.getWriter()).thenReturn(writer);

        PersonServlet personServlet = new PersonServlet();

        personServlet.doGet(requestMockito, responseMockito);

        assertThat(stringWriter.toString()).contains("[{\"id\":1,\"name\":\"First User\",\"email\":\"first@server.com\",\"role\":0,\"blocked\":true},{\"id\":2,\"name\":\"Second User\",\"email\":\"second@server.com\",\"role\":0,\"blocked\":true}]");
    }

    @Test
    @DisplayName("Добавление пользователя")
    public void doPostPersonTest() throws IOException, ServletException {

        String jsonPerson = """
                {
                    "id": 7,
                    "name": "Test User",
                    "email": "test@server.com",
                    "password": "1234",
                    "role": 0,
                    "blocked": true
                }
                """;

        MockHttpServletRequest requestMockito = new MockHttpServletRequest();
        requestMockito.setParameter("person", jsonPerson);
        MockHttpServletResponse responseMockito = new MockHttpServletResponse();

        PersonServlet personServlet = new PersonServlet();

        personServlet.doPost(requestMockito, responseMockito);

        assertThat(responseMockito.getStatus()).isEqualTo(201);
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void doDeletePersonTest() throws ServletException, IOException {
        MockHttpServletRequest requestMockito = new MockHttpServletRequest();
        requestMockito.setParameter("id", "2");
        MockHttpServletResponse responseMockito = new MockHttpServletResponse();

        PersonServlet personServlet = new PersonServlet();

        personServlet.doDelete(requestMockito, responseMockito);

        assertThat(responseMockito.getStatus()).isEqualTo(200);
    }

}
