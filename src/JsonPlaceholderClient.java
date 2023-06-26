import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class JsonPlaceholderClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public String createUser(String userData) throws IOException {
        URL url = new URL(BASE_URL + "/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(userData.getBytes());
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error " + responseCode);
        }
    }

    public String updateUser(int userId, String userData) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(userData.getBytes());
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error " + responseCode);
        }
    }

    public boolean deleteUser(int userId) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        return responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE;
    }

    public String getAllUsers() throws IOException {
        URL url = new URL(BASE_URL + "/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error" + responseCode);
        }
    }

    public String getUserById(int userId) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error" + responseCode);
        }
    }

    public String getUserByUsername(String username) throws IOException {
        URL url = new URL(BASE_URL + "/users?username=" + username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error" + responseCode);
        }
    }
    public void getLastPostCommentsByUserId(int userId) throws IOException, ParseException {
        URL url = new URL(BASE_URL + "/users/" + userId + "/posts/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            org.json.simple.parser.JSONParser jp = new org.json.simple.parser.JSONParser();
            JSONArray ja = (JSONArray) jp.parse(response.toString());
            int maxPostId = Integer.MIN_VALUE;
            for (Object o : ja) {
                JSONObject jo = (JSONObject) o;
                if (Integer.parseInt(jo.get("id").toString()) > maxPostId) {
                    maxPostId = Integer.parseInt(jo.get("id").toString());
                }
            }

            url = new URL(BASE_URL + "/posts/" + maxPostId + "/comments/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                throw new IOException("Error" + responseCode);
            }

            File file = new File("user-" + userId + "-post-" + maxPostId + "-comments.json");
            try (FileWriter writer = new FileWriter(file))
            {
                writer.write(response.toString());
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new IOException("Error" + responseCode);
        }
    }

    public String getOpenToDos(int userId) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId + "/todos?completed=false");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("Error" + responseCode);
        }
    }
}
