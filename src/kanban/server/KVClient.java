package kanban.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class KVClient {

    long apiToken;
    public static final int PORT = 8078;
    String URL;
    HttpClient client = HttpClient.newHttpClient();

    public KVClient(String URL) {
        this.URL = URL;
        try {
            apiToken = this.register();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long register() throws IOException, InterruptedException {
        URI uri = URI.create(URL + ":" + PORT + "/register");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        String body = new String(response.body().getBytes(), StandardCharsets.UTF_8);

        return Long.parseLong(body);
    }


    public void put(String key, String json) throws IOException, InterruptedException {
        URI uri = URI.create(URL + ":" + PORT + "/save/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest
                .BodyPublishers.ofString(json)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(URL + ":" + PORT + "/load/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());

        return response.body();
    }

}
