package kanban.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import kanban.module.*;
import kanban.service.HttpTaskManager;
import kanban.service.Managers;
import kanban.service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.regex.Pattern;


public class HttpTaskServer {

    public static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
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
        server.createContext("/api/v1/tasks/epic", this::handleEpicTasks);
        server.createContext("/api/v1/tasks/subtask", this::handleSubTasks);
        server.createContext("/api/v1/tasks/history", this::handleHistory);
        server.createContext("/api/v1/tasks/", this::handlePrioritizedTasks);
    }

    private void handlePrioritizedTasks(HttpExchange httpExchange) {
        try{
            String requestPath = httpExchange.getRequestURI().getPath();
            String requestQuery = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            switch(requestMethod){
                case "GET": {
                    if(Pattern.matches("^/api/v1/tasks/$", requestPath) && (requestQuery == null)){
                        String response = gson.toJson(taskManager.getPrioritizedTasks());
                        sendText(httpExchange, response, 200);
                    } else {
                        System.out.println("Ждём 'api/v1/tasks/' , а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                    break;
                }
                default:{
                    System.out.println("Ждём GET при запросе истории задач,  а получили - " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }

    }

    private void handleHistory(HttpExchange httpExchange) {


        try{
            String requestPath = httpExchange.getRequestURI().getPath();
            String requestQuery = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            switch(requestMethod){
                case "GET": {
                    if(Pattern.matches("^/api/v1/tasks/history$", requestPath) && (requestQuery == null)){
                        String response = gson.toJson(taskManager.getHistoryOfTasks());
                        sendText(httpExchange, response, 200);
                    } else {
                        System.out.println("Ждём 'api/v1/tasks/history' , а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                    break;
                }
                default:{
                    System.out.println("Ждём GET при запросе истории задач,  а получили - " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private void handleRegularTasks(HttpExchange httpExchange) {
        try{

            String requestPath = httpExchange.getRequestURI().getPath();
            String requestQuery = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            switch(requestMethod){
                case "GET": {
                    if(Pattern.matches("^/api/v1/tasks/task$", requestPath) && requestQuery == null){
                        String response = gson.toJson(taskManager.getAllRegularTasks());
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    if(Pattern.matches("^/api/v1/tasks/task$", requestPath)
                            && Pattern.matches("^id=\\d++$", requestQuery) ){
                        String idInString = requestQuery.replaceFirst("^id=", "");
                        int id = parsePathId(idInString);
                        if(id != -1){
                            String response = gson.toJson(taskManager.getRegularTask(id));
                            if(response.equals("null")){
                                System.out.println( "Получен id несуществующей обычной задачи. Получен id = " + idInString);
                                httpExchange.sendResponseHeaders(422, 0);
                            } else {
                                sendText(httpExchange, response, 200);
                            }
                            break;
                        } else {
                            System.out.println( "Получен некорректный id. Получен id = " + idInString);
                            httpExchange.sendResponseHeaders(422, 0);
                        }
                        break;
                    } else {
                        System.out.println("Ждём api/v1/tasks/task?id={id}, а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                        break;
                    }
                }
                case "POST": {
                    try{
                        RegularTask task = gson.fromJson(body, RegularTask.class);
                        String isTaskUpserted;
                        if(taskManager.getRegularTaskStorage().containsKey(task.getId())){
                            isTaskUpserted = taskManager.updateRegularTask(task);
                        } else{
                            isTaskUpserted= taskManager.createRegularTask(task);
                        }
                        int code;
                        if(isTaskUpserted.endsWith("создана.") || isTaskUpserted.endsWith("обновлена.") ){
                            code = 201;
                        } else {
                            code = 200;
                        }

                        sendText(httpExchange , isTaskUpserted, code);
                        break;
                    } catch(JsonSyntaxException e){
                        sendText(httpExchange
                                , "Получен некорректный JSON"
                                , 400);
                    }
                }
                case "DELETE":{
                    if(Pattern.matches("^/api/v1/tasks/task$", requestPath) && requestQuery == null){
                        String response = gson.toJson(taskManager.clearRegularTaskStorage());
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    if(Pattern.matches("^/api/v1/tasks/task$", requestPath)
                            && Pattern.matches("^id=\\d++$", requestQuery) ){
                        String idInString = requestQuery.replaceFirst("^id=", "");
                        int id = parsePathId(idInString);
                        String response;
                        if(id != -1){
                            response = taskManager.removeRegularTask(id);
                            if(response.endsWith("невозможно")){
                                System.out.println( "Получен id несуществующей обычной задачи. Получен id = " + idInString);
                                sendText(httpExchange, response, 422);
                            } else {
                                sendText(httpExchange, response, 200);
                            }
                            break;
                        } else {
                            System.out.println( "Получен некорректный id. Получен id = " + idInString);
                            httpExchange.sendResponseHeaders(422, 0);
                            break;
                        }
                    } else {
                        System.out.println("Ждём api/v1/tasks/task?id={id}, а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                        break;
                    }
                }
                default:{
                    System.out.println("Ждём GET, POST или DELETE, а получили - " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }


    private void handleEpicTasks(HttpExchange httpExchange) {
        try{

            String requestPath = httpExchange.getRequestURI().getPath();
            String requestQuery = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            switch(requestMethod){
                case "GET": {
                    if(Pattern.matches("^/api/v1/tasks/epic$", requestPath) && requestQuery == null){
                        String response = gson.toJson(taskManager.getAllEpicTasks());
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    if(Pattern.matches("^/api/v1/tasks/epic$", requestPath)
                            && Pattern.matches("^id=\\d++$", requestQuery) ){
                        String idInString = requestQuery.replaceFirst("^id=", "");
                        int id = parsePathId(idInString);
                        if(id != -1){
                            String response = gson.toJson(taskManager.getEpicTask(id));
                            if(response.equals("null")){
                                System.out.println( "Получен id несуществующей эпик задачи. Получен id = " + idInString);
                                httpExchange.sendResponseHeaders(422, 0);
                                break;
                            } else {
                                sendText(httpExchange, response, 200);
                            }
                            break;
                        } else {
                            System.out.println( "Получен некорректный id. Получен id = " + idInString);
                            httpExchange.sendResponseHeaders(422, 0);
                            break;
                        }
                    } else {
                        System.out.println("Ждём api/v1/tasks/epic?id={id}, а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                        break;
                    }
                }
                case "POST": {
                    try{
                        EpicTask task = gson.fromJson(body, EpicTask.class);
                        String isTaskUpserted;
                        if(taskManager.getEpicTaskStorage().containsKey(task.getId())){
                            isTaskUpserted = taskManager.updateEpicTask(task);
                        } else{
                            isTaskUpserted= taskManager.createEpicTask(task);
                        }
                        int code;
                        if(isTaskUpserted.endsWith("создана") || isTaskUpserted.endsWith("обновлена.") ){
                            code = 201;
                        } else {
                            code = 200;
                        }

                        sendText(httpExchange , isTaskUpserted, code);
                        break;
                    } catch(JsonSyntaxException e){
                        sendText(httpExchange
                                , "Получен некорректный JSON"
                                , 400);
                    }
                }
                case "DELETE":{
                    if(Pattern.matches("^/api/v1/tasks/epic$", requestPath) && requestQuery == null){
                        String response = gson.toJson(taskManager.clearEpicTaskStorage());
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    if(Pattern.matches("^/api/v1/tasks/epic$", requestPath)
                            && Pattern.matches("^id=\\d++$", requestQuery) ){
                        String idInString = requestQuery.replaceFirst("^id=", "");
                        int id = parsePathId(idInString);
                        String response;
                        if(id != -1){
                            response = taskManager.removeEpicTask(id);
                            if(response.endsWith("невозможно")){
                                System.out.println( "Получен id несуществующей эпик задачи. Получен id = " + idInString);
                                sendText(httpExchange, response, 422);
                            } else {
                                sendText(httpExchange, response, 200);
                            }
                            break;
                        } else {
                            System.out.println( "Получен некорректный id. Получен id = " + idInString);
                            httpExchange.sendResponseHeaders(422, 0);
                            break;
                        }
                    } else {
                        System.out.println("Ждём api/v1/tasks/epic?id={id}, а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                        break;
                    }
                }
                default:{
                    System.out.println("Ждём GET, POST или DELETE, а получили - " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private void handleSubTasks(HttpExchange httpExchange) {
        try{

            String requestPath = httpExchange.getRequestURI().getPath();
            String requestQuery = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            switch(requestMethod){
                case "GET": {
                    if(Pattern.matches("^/api/v1/tasks/subtask$", requestPath) && requestQuery == null){
                        String response = gson.toJson(taskManager.getAllSubTasks());
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    if(Pattern.matches("^/api/v1/tasks/subtask$", requestPath)
                            && Pattern.matches("^id=\\d++$", requestQuery) ){
                        String idInString = requestQuery.replaceFirst("^id=", "");
                        int id = parsePathId(idInString);
                        if(id != -1){
                            String response = gson.toJson(taskManager.getSubTask(id));
                            if(response.equals("null")){
                                System.out.println( "Получен id несуществующей  подзадачи. Получен id = " + idInString);
                                httpExchange.sendResponseHeaders(422, 0);
                                break;
                            } else {
                                sendText(httpExchange, response, 200);
                            }
                            break;
                        } else {
                            System.out.println( "Получен некорректный id. Получен id = " + idInString);
                            httpExchange.sendResponseHeaders(422, 0);
                            break;
                        }
                    }

                    if(Pattern.matches("^/api/v1/tasks/subtask/epic$", requestPath)
                            && Pattern.matches("^id=\\d++$", requestQuery) ){
                        String idInString = requestQuery.replaceFirst("^id=", "");
                        int id = parsePathId(idInString);
                        if(id != -1){
                            String response = gson.toJson(taskManager.getSubTaskStorageFromEpic(id));
                            if(response.equals("null")){
                                System.out.println( "Получен id несуществующей  подзадачи. Получен id = " + idInString);
                                httpExchange.sendResponseHeaders(422, 0);
                                break;
                            } else {
                                sendText(httpExchange, response, 200);
                            }
                            break;
                        } else {
                            System.out.println( "Получен некорректный id. Получен id = " + idInString);
                            httpExchange.sendResponseHeaders(422, 0);
                            break;
                        }
                    }

                    else {
                        System.out.println("Ждём api/v1/tasks/subtask?id={id}, а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                        break;
                    }
                }
                case "POST": {
                    try{
                        SubTask task = gson.fromJson(body, SubTask.class);
                        String isTaskUpserted= "";
                        if(taskManager.getSubTaskStorage().containsKey(task.getId())){
                            isTaskUpserted = taskManager.updateSubTask(task);
                        } else{
                            isTaskUpserted= taskManager.createSubTask(task);
                        }
                        int code = 0;
                        if(isTaskUpserted.endsWith("создана") || isTaskUpserted.endsWith("обновлена") ){
                            code = 201;
                        } else {
                            code = 200;
                        }

                        sendText(httpExchange , isTaskUpserted, code);
                        break;
                    } catch(JsonSyntaxException e){
                        sendText(httpExchange
                                , "Получен некорректный JSON"
                                , 400);
                    }
                }
                case "DELETE":{
                    if(Pattern.matches("^/api/v1/tasks/subtask$", requestPath) && requestQuery == null){
                        String response = gson.toJson(taskManager.clearSubTaskStorage());
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    if(Pattern.matches("^/api/v1/tasks/subtask$", requestPath)
                            && Pattern.matches("^id=\\d++$", requestQuery) ){
                        String idInString = requestQuery.replaceFirst("^id=", "");
                        int id = parsePathId(idInString);
                        String response = null;
                        if(id != -1){
                            response = taskManager.removeSubTask(id);
                            boolean aaa = response.endsWith("невозможно");
                            if(response.endsWith("невозможно")){
                                System.out.println( "Получен id несуществующей подзадачи. Получен id = " + idInString);
                                sendText(httpExchange, response, 422);
                            } else {
                                sendText(httpExchange, response, 200);
                            }
                            break;
                        } else {
                            System.out.println( "Получен некорректный id. Получен id = " + idInString);
                            httpExchange.sendResponseHeaders(422, 0);
                            break;
                        }
                    } else {
                        System.out.println("Ждём api/v1/tasks/subtask?id={id}, а получили - " + requestPath + requestQuery);
                        httpExchange.sendResponseHeaders(405, 0);
                        break;
                    }
                }
                default:{
                    System.out.println("Ждём GET, POST или DELETE, а получили - " + requestMethod);
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

    private void sendText(HttpExchange httpExchange, String text, int code) throws IOException {
        byte[] textInByte = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(code, textInByte.length);
        httpExchange.getResponseBody().write(textInByte);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    private int parsePathId(String pathId){
        try{
            return Integer.parseInt(pathId);
        } catch(NumberFormatException e){
            return -1;
        }
    }
}
