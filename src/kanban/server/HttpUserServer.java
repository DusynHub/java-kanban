package kanban.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import kanban.service.Managers;
import kanban.service.TaskManager;
import kanban.service.UserManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HttpUserServer {

    public static final int PORT = 8080;

    private HttpServer server;
    private Gson gson;

    private TaskManager taskManager;
    private UserManager userManager;


    public HttpUserServer() {
        this(Managers.getUserDefault());
    }

    public HttpUserServer(UserManager userManager) {
        this.userManager = userManager;
        this.taskManager = userManager.getTaskManager();
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost",PORT), 0);
        server.createContext("/api/v1/users", this::handleUsers);
    }

    private void handleUsers(HttpExchange h){

    }

    public void start(){
        System.out.println("Started UserServer " + PORT);
        System.out.println("http://localhost:" + PORT + "/api/v1/users");
        server.start();
    }

    private String readTExt(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(),  StandardCharsets.UTF_8);
    }

    private void sendText(HttpExchange, String text) throws IOException

}
