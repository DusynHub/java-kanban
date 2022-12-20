package kanban.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kanban.module.RegularTask;
import kanban.module.StatusName;
import kanban.module.TaskType;
import kanban.module.user.User;
import kanban.service.Managers;
import kanban.service.TaskManager;
import kanban.service.UserManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HttpUserServerTest {

    private HttpUserServer userServer;

    private TaskManager taskManager;

    private UserManager userManager;

    private Gson gson = Managers.getGson();

    private RegularTask task;

    private User user;

    @BeforeEach
    void init() throws IOException {
        userManager = Managers.getUserDefault();
        taskManager = userManager.getTaskManager();
        userServer = new HttpUserServer(userManager);

        user = new User("Тестов Тест Тестович");
        userManager.add(user);
        task = new RegularTask( 0
                                , "Test RegularTask"
                                , "Some test"
                                , StatusName.NEW
                                , TaskType.REGULAR_TASK
                                , Optional.of(Duration.ofMinutes(90))
                                , Optional.of(ZonedDateTime.now())
                                , user);
        taskManager.createRegularTask(task);

        userServer.start();
    }

    @AfterEach
    void tearDown() {
        userServer.stop();
    }

    @Test
    void getUsers() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/api/v1/users");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        Type userType = new TypeToken<ArrayList<User>>(){}.getType();
        List<User> actual = gson.fromJson(response.body(), userType);

        assertNotNull(actual, "Пользователь не возвращается");
        assertEquals(1, actual.size(), "Не верное количество пользователей");
        assertEquals(user, actual.get(0));
    }

    @Test
    void getUsersById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/api/v1/users/1");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        Type userType = new TypeToken<User>() {}.getType();
        User actual = gson.fromJson(response.body(), User.class);

        assertEquals(200, response.statusCode());
        assertNotNull(actual, "Пользователь не возвращается");
        assertEquals(user, actual, "Пользователь не совпадает");
    }
}