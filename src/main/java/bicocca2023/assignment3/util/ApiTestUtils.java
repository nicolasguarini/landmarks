package bicocca2023.assignment3.util;

import com.google.gson.Gson;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ApiTestUtils {
    public static TestResponse request(String method, String path, String requestBody) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body;

            try{ body = IOUtils.toString(connection.getInputStream()); }
            catch(IOException e) { body = ""; }

            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    public static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public HashMap json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }

    public static String generateUniqueUsername() {
        long timestamp = System.currentTimeMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestampString = dateFormat.format(new Date(timestamp));

        return "testjpa" + timestampString;
    }
}