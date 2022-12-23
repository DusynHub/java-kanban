package kanban.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kanban.module.*;
import kanban.service.Managers;
import kanban.service.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    public static final int PORT = 8080;
    static final ZoneId zone = ZoneId.of("Europe/Moscow");
    private HttpTaskServer taskServer;
    private TaskManager taskManager;
    private Gson gson;
    static RegularTask rt0;
    static RegularTask rt1;
    static RegularTask rt2;
    static RegularTask rt3;
    static RegularTask rt4;
    static RegularTask rt5;
    static RegularTask rt6;
    static RegularTask rt7;
    static RegularTask rt8;
    static RegularTask rt01;
    static RegularTask rtAnyId;
    static SubTask st1;
    static SubTask st2;
    static SubTask st3;
    static SubTask st5;
    static SubTask st02SameStartTimeAsRt0;
    static EpicTask et0;
    static EpicTask et1;
    static EpicTask et2;
    static EpicTask et3;
    static EpicTask et4;

    @BeforeAll
    public static void beforeAll(){
        rt0 = new RegularTask(
                0
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 22, 0)
                , zone))
        );
        rt1 = new RegularTask(
                1
                , "1 RegularTask"
                , "RegularTask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(120))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 9, 9, 0)
                , zone))
        );
        rt2 = new RegularTask(
                2
                , "2 RegularTask"
                , "RegularTask with id 2"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(240))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 7, 0)
                , zone))
        );

        rt3 = new RegularTask(
                3
                , "3 RegularTask"
                , "RegularTask with id 3"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(90))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 18, 11, 0)
                , zone))
        );

        rt4 = new RegularTask(
                4
                , "4 RegularTask"
                , "RegularTask with id 4"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(120))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 19, 13, 0)
                , zone))
        );

        rt5 = new RegularTask(
                5
                , "5 RegularTask"
                , "RegularTask with id 5"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(30))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 20, 8, 0)
                , zone))
        );

        rt6 = new RegularTask(
                6
                , "6 RegularTask"
                , "RegularTask with id 6"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(45))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 21, 15, 0)
                , zone))
        );

        rt7 = new RegularTask(
                7
                , "7 RegularTask"
                , "RegularTask with id 7"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(240))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 22, 16, 0)
                , zone))
        );

        rt8 = new RegularTask(
                8
                , "8 RegularTask"
                , "RegularTask with id 8"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(45))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 11, 23, 8, 0)
                , zone))
        );

        rt01 = new RegularTask(
                11
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 22, 1)
                , zone))
        );

        rtAnyId = new RegularTask(
                0
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
        );


        et0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        et1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );

        et2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );

        et3 = new EpicTask(
                3
                , "3 epic"
                , "epic with id 3"
                , TaskType.EPIC_TASK
        );

        et4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        st1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 9, 0)
                , zone))
                , 0
        );
        st2 = new SubTask(
                2
                , "2 subtask"
                , "subtask with id 2"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 25, 9, 0)
                , zone))
                , 0
        );
        st3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 12, 0)
                , zone))
                , 2
        );
        st5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 13, 13, 0)
                , zone))
                , 4
        );

        st02SameStartTimeAsRt0 = new SubTask(
                2
                , "2 subtask"
                , "subtask with id 2"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 21, 59)
                , zone))
                , 1
        );
    }

    @BeforeEach
    void init() throws IOException {
        taskServer = new HttpTaskServer();
        taskManager = taskServer.getTaskManager();
        gson = Managers.getGson();
    }

// Тесты эндпоинтов RegularTask
    @Test
    void ShouldReturnResponseCode405ForWrongRequestMethodWithRegularTask() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createRegularTask(rt2);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task");
        String requestBody  = "test body";
        HttpRequest request = HttpRequest.newBuilder().uri(uri).PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(405, response.statusCode());
    }


    @Test
    void ShouldReturn404ForWrongRequest() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createRegularTask(rt2);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/apai/v1/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }

    @Test
    void ShouldReturnAllRegularTasks() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createRegularTask(rt2);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();

        List<Task> expectedList = new ArrayList<>();
        expectedList.add(rt0);
        expectedList.add(rt1);
        expectedList.add(rt2);
        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> actual = gson.fromJson(response.body(), listType);

        assertNotNull(result, "Список обычных задач не возвращается");

        assertEquals(expectedList, actual);
    }

    @Test
    void ShouldReturnAllRegularTaskWithId0() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();
        Task actual = gson.fromJson(response.body(), Task.class);

        assertNotNull(result, "Список обычных задач не возвращается");
        assertEquals(rt0, actual, "Ожидаемый список и полученный не равны");
    }

    @Test
    void ShouldReturnResponseCodeTaskWithIdInRequestDoesNotExist422() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=100");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(422, response.statusCode(), "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

    @Test
    void ShouldReturnResponseCodeTaskWithIncorrectIdFormat() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=ccdc");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(405, response.statusCode(), "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

    @Test
    void ShouldReturnResponseCodeTaskWithIncorrectId422() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=000001");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(422, response.statusCode(), "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

    @Test
    void ShouldCreateRegularTaskWithId0() throws IOException, InterruptedException {
        taskServer.start();
        String requestBody = gson.toJson(rt0);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(201, response.statusCode(), "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

    @Test
    void ShouldUpdateRegularTaskWithId0() throws IOException, InterruptedException {

        taskServer.start();
        String requestBody = gson.toJson(rt0);
        String requestBody2 = gson.toJson(rt0);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody2)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 =  client.send(request2, HttpResponse.BodyHandlers.ofString());

        System.out.println(response2.body());
        assertEquals(200, response2.statusCode(), "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

    @Test
    void ShouldDeleteAllRegularTasks() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response1 =  client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response1.body());
        assertTrue(taskManager.getRegularTaskStorage().isEmpty(), "Хранилище обычных задач после удаления не пустое");
    }


    @Test
    void ShouldDeleteRegularTaskWithId0() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response1 =  client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response1.body());
        assertEquals(200, response1.statusCode(), "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

    @Test
    void ShouldNotDeleteRegularTaskWithId0() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/task?id=111");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response1 =  client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response1.body());
        assertEquals(422, response1.statusCode(), "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

// Тесты эндпонитов Эпик задач


    @Test
    void ShouldReturnResponseCode405ForWrongRequestMethodWithEpic() throws IOException, InterruptedException {
        taskServer.start();
        String requestBody = gson.toJson(et0);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(405,
                response.statusCode(),
                "Получен не тот ответ сервера при запросе эпик задачи с некорректным id");
    }

    @Test
    void ShouldReturnAllEpicTasks() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskManager.createEpicTask(et1);
        taskManager.createEpicTask(et2);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();

        List<Task> expectedList = new ArrayList<>();
        expectedList.add(et0);
        expectedList.add(et1);
        expectedList.add(et2);
        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> actual = gson.fromJson(response.body(), listType);

        assertNotNull(result, "Список эпик задач не возвращается");

        assertEquals(expectedList, actual);
    }

    @Test
    void ShouldReturnEpicTaskWithId0() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();
        Task actual = gson.fromJson(response.body(), Task.class);

        assertNotNull(result, "Список эпик задач не возвращается");
        assertEquals(et0, actual, "Ожидаемый список эпик задач и полученный не равны");
    }

    @Test
    void ShouldCreateEpicTaskWithId0() throws IOException, InterruptedException {
        taskServer.start();
        String requestBody = gson.toJson(et0);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(201,
                    response.statusCode(),
                    "Получен не тот ответ сервера при запросе эпик задачи с некорректным id");
    }

    @Test
    void ShouldUpdateEpicTaskWithId0() throws IOException, InterruptedException {

        taskServer.start();
        String requestBody = gson.toJson(et0);
        String requestBody2 = gson.toJson(et0);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody2)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 =  client.send(request2, HttpResponse.BodyHandlers.ofString());

        System.out.println(response2.body());
        assertEquals(200,
                        response2.statusCode(),
                        "Получен не тот ответ сервера при запросе эпик задачи с некорректным id");
    }


    @Test
    void ShouldDeleteAllEpicTasks() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response1 =  client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response1.body());
        assertTrue(taskManager.getEpicTaskStorage().isEmpty(),
                                         "Хранилище эпик задач после удаления не пустое");
        assertTrue(taskManager.getSubTaskStorage().isEmpty(),
                    "Хранилище подзадач задач после удаления всех эпик задач не пустое");
    }

    @Test
    void ShouldDeleteEpicTaskWithId0() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response1 =  client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response1.body());
        assertEquals(200,
                    response1.statusCode(),
                    "Получен не тот ответ сервера при запросе эпик задачи с некорректным id");
        }

    @Test
    void ShouldNotDeleteEpicTaskWithId0() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/epic?id=111");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response1 =  client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response1.body());
        assertEquals(422,
                    response1.statusCode(),
                    "Получен не тот ответ сервера при запросе эпик задачи с некорректным id");
    }

    // Тесты эндпоинтов подзадач



    @Test
    void ShouldReturnResponseCode405ForWrongRequestMethodWithSubTasks() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskServer.start();
        String requestBody = gson.toJson(st1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/subtask?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(405,
                response.statusCode(),
                "Получен не 405 ответ при попытке создать подзадачу с неправильным метдом PUT");
    }


    @Test
    void ShouldReturnAllSubTasks() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();

        List<Task> expectedList = new ArrayList<>();
        expectedList.add(st1);
        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> actual = gson.fromJson(response.body(), listType);

        assertNotNull(result, "Список поздач задач не возвращается");

        assertEquals(expectedList, actual);
    }

    @Test
    void ShouldReturnSubTaskWithId1() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/subtask?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();
        Task actual = gson.fromJson(response.body(), Task.class);

        assertNotNull(result, "Список эпик задач не возвращается");
        assertEquals(st1, actual, "Ожидаемый список эпик задач и полученный не равны");
    }

    @Test
    void ShouldCreateSubTaskWithId0() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskServer.start();
        String requestBody = gson.toJson(st1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/subtask?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(1, taskManager.getSubTaskStorage().size());
        assertEquals(201,
                response.statusCode(),
                "Получен не тот ответ сервера создании или обновбении подзадачи с некорректным id");
    }

    @Test
    void ShouldUpdateSubTaskWithId0() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskServer.start();
        String requestBody = gson.toJson(st1);
        String requestBody2 = gson.toJson(st1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/subtask?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(requestBody2)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 =  client.send(request2, HttpResponse.BodyHandlers.ofString());

        System.out.println(response2.body());
        assertEquals(1, taskManager.getSubTaskStorage().size());
        assertEquals(201,
                response2.statusCode(),
                "Получен не тот ответ сервера при запросе задачи с некорректным id");
    }

    @Test
    void ShouldReturnEmptyHistory() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskManager.createRegularTask(rt0);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();



        List<Task> ExpectedTaskHistory = new ArrayList<>();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> actualTaskHistory = gson.fromJson(response.body(), listType);

        assertEquals(ExpectedTaskHistory,
                actualTaskHistory,
                "Получен не тот ответ сервера при запросе пустой истории вызовов задач");
    }

    @Test
    void ShouldReturnHistoryWithTwoTasks() throws IOException, InterruptedException {
        taskManager.createEpicTask(et0);
        taskManager.createRegularTask(rt1);
        taskManager.getEpicTask(0);
        taskManager.getRegularTask(1);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        List<Task> ExpectedTaskHistory = new ArrayList<>();
        ExpectedTaskHistory.add(et0);
        ExpectedTaskHistory.add(rt1);

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> actualTaskHistory = gson.fromJson(response.body(), listType);

        assertEquals(ExpectedTaskHistory,
                actualTaskHistory,
                "Получен не тот ответ сервера при запросе истории вызовов задач c двумя задачами");
    }


    @Test
    void ShouldReturnPrioritizedTasksListWithTwoTasks() throws IOException, InterruptedException {
        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskServer.start();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/api/v1/tasks/");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        List<Task> ExpectedTaskHistory = new ArrayList<>();
        ExpectedTaskHistory.add(rt0);
        ExpectedTaskHistory.add(rt1);

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> actualTaskHistory = gson.fromJson(response.body(), listType);

        assertEquals(ExpectedTaskHistory,
                actualTaskHistory,
                "Получен не тот ответ сервера при запросе истории вызовов задач c двумя задачами");
    }

    @AfterEach
    void stopServer() {
        taskServer.stop();
    }
}