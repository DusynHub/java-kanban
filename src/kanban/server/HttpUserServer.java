package kanban.server;

import com.google.gson.Gson;
import com.sun.jdi.PathSearchingVirtualMachine;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import kanban.service.Managers;
import kanban.service.TaskManager;
import kanban.service.UserManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class HttpUserServer {

    public static final int PORT = 8080;

    private HttpServer server;
    private Gson gson;

    private TaskManager taskManager;
    private UserManager userManager;


    public HttpUserServer() throws IOException {
        this(Managers.getUserDefault());
    }


    public HttpUserServer(UserManager userManager) throws IOException {
        this.userManager = userManager;
        this.taskManager = userManager.getTaskManager();
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost",PORT), 0);
        server.createContext("/api/v1/users", this::handleUsers);
    }

    private void handleUsers(HttpExchange h){
        try{
            String path =  h.getRequestURI().getPath();
            String requestMethod = h.getRequestMethod();
            switch(requestMethod) {
                case "GET": {
                    if(Pattern.matches("^/api/v1/users$", path)){
                        String response = gson.toJson( userManager.getAll());
                        sendText(h, response);
                        break;
                    }

                    if(Pattern.matches("^/api/v1/users/\\d+$", path)) {
                        String pathId = path.replaceFirst("/api/v1/users/", "");
                        int id = parsePathId(pathId);
                        if(id != -1){
                            String response = gson.toJson(userManager.getById(id));
                            sendText(h, response);
                            break;
                        } else {
                            System.out.println("Получен некорректный id = " + pathId);
                            h.sendResponseHeaders(405, 0);
                            break;
                        }
                    }


                    if(Pattern.matches("^/api/v1/users/\\d+/tasks$", path)) {
                        String pathId = path.replaceFirst("/api/v1/users/", "")
                                            .replaceFirst("/tasks", "");
                        int id = parsePathId(pathId);
                        if(id != -1){
                            String response = gson.toJson(userManager.getUserTasks(id));
                            sendText(h, response);
                        } else {
                            System.out.println("Получен некорректный id = " + pathId);
                            h.sendResponseHeaders(405, 0);
                            break;
                        }
                    }

                }
                case "DELETE": {
                    if(Pattern.matches("^/api/v1/users/\\  d+$", path)) {
                        String pathId = path.replaceFirst("/api/v1/users", "");
                        int id = parsePathId(pathId);
                        if(id != -1){
                            userManager.delete(id);
                            System.out.println("Удалили пользователя id =" + id);
                            h.sendResponseHeaders(200, 0);
                            break;
                        } else {
                            System.out.println("Получен некорректный id = " + pathId);
                            h.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else {
                        h.sendResponseHeaders(405,0);
                        break;
                    }
                }
                default: {
                    System.out.println("Ждём GET или DELETE запрос, а получили - " + requestMethod);
                    h.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            h.close();
        }
    }

    private int parsePathId(String pathId){
        try{
            return Integer.parseInt(pathId);
        } catch(NumberFormatException e){
            return -1;
        }
    }

    public void start() {
        System.out.println("Started UserServer " + PORT);
        System.out.println("http://localhost:" + PORT + "/api/v1/users");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    private String readTExt(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(),  StandardCharsets.UTF_8);
    }

    private void sendText(HttpExchange h, String text) throws IOException {
        byte[] textResponse = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, textResponse.length);
        h.getResponseBody().write(textResponse);
    }

}
