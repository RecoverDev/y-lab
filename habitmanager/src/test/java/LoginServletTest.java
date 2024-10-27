import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.ServletException;
import ru.list.Servlet.LoginServlet;

public class LoginServletTest {


    @Test
    @DisplayName("Авторизация пользователя")
    public void LoginTest() throws ServletException, IOException {
        MockHttpServletRequest requestMockito = new MockHttpServletRequest();
        requestMockito.setParameter("email", "first@server.com");
        requestMockito.setParameter("password", "111");
        MockHttpServletResponse responseMockito = new MockHttpServletResponse();

        LoginServlet servlet = new LoginServlet();

        servlet.doGet(requestMockito,responseMockito);

        assertThat(responseMockito.getStatus()).isEqualTo(200);
    }

}
