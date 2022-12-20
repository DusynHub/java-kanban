package kanban.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import kanban.module.RegularTask;
import kanban.module.StatusName;
import kanban.module.TaskType;
import kanban.service.Managers;
import kanban.service.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

public class HttpTaskServer {

    public static final int PORT = 8080;

    private final HttpServer server;

    private final TaskManager taskManager;

    private final Gson gson;

    public static void main(String[] args) throws IOException {
        HttpTaskServer t = new HttpTaskServer();
        TaskManager tm = t.getTaskManager();

        RegularTask task = new RegularTask( 0
                                            , "Test RegularTask"
                                            , "Some test"
                                            , StatusName.NEW
                                            , TaskType.REGULAR_TASK
                                            , Optional.of(Duration.ofMinutes(90))
                                            , Optional.of(ZonedDateTime.now()));
        tm.createRegularTask(task);
        t.start();
    }


    public HttpTaskServer() throws IOException {
        this(Managers.getDefault());
    }

    public HttpTaskServer(TaskManager defaultTaskManager) throws IOException {
        this.taskManager = defaultTaskManager;
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/api/v1/tasks/task", this::handleRegularTasks);
    }

    private void handleRegularTasks(HttpExchange httpExchange) {
        try{
            String requestPath = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            switch(requestMethod){
                case "GET": {
                    if(Pattern.matches("^/api/v1/tasks/task$", requestPath)){
                        String response = gson.toJson(taskManager.getAllRegularTasks());
                        sendText(httpExchange, response);
                        break;
                    } else {
                        System.out.println("Ждём api/v1/tasks/task, а получили - " + requestPath);
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                }
                default:{
                    System.out.println("Ждём GET, а получили - " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    public void start(){
        System.out.println("Started UserServer " + PORT);
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    private void sendText(HttpExchange httpExchange, String text) throws IOException {
        byte[] textInByte = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(200, textInByte.length);
        httpExchange.getResponseBody().write(textInByte);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
