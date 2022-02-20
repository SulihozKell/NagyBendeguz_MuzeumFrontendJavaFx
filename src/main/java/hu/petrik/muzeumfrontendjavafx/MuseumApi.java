package hu.petrik.muzeumfrontendjavafx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MuseumApi {
    private static final String BASE_URL = "http://127.0.0.1:8000";
    private static final String PAINTING_API_URL = BASE_URL + "/api/paintings";
    private static final String STATUE_API_URL = BASE_URL + "/api/statues";

    public static List<Painting> getPaintings() throws IOException {
        Response response = RequestHandler.get(PAINTING_API_URL);
        String json = response.getContent();
        Gson jsonConvert = new Gson();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        Type type = new TypeToken<List<Painting>>(){}.getType();
         return jsonConvert.fromJson(json, type);
    }

    public static List<Statue> getStatues() throws IOException {
        Response response = RequestHandler.get(STATUE_API_URL);
        String json = response.getContent();
        Gson jsonConvert = new Gson();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        Type type = new TypeToken<List<Statue>>(){}.getType();
        return jsonConvert.fromJson(json, type);
    }

    public static Painting addPainting(Painting newPainting) throws IOException {
        Gson jsonConvert = new Gson();
        String paintingJson = jsonConvert.toJson(newPainting);
        Response response = RequestHandler.post(PAINTING_API_URL, paintingJson);
        String json = response.getContent();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return jsonConvert.fromJson(json, Painting.class);
    }

    public static Statue addStatue(Statue newStatue) throws IOException {
        Gson jsonConvert = new Gson();
        String statueJson = jsonConvert.toJson(newStatue);
        Response response = RequestHandler.post(STATUE_API_URL, statueJson);
        String json = response.getContent();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return jsonConvert.fromJson(json, Statue.class);
    }

    public static Painting updatePainting(Painting updatePainting) throws IOException {
        Gson jsonConvert = new Gson();
        String paintingJson = jsonConvert.toJson(updatePainting);
        Response response = RequestHandler.put(PAINTING_API_URL + "/" + updatePainting.getId(), paintingJson);
        String json = response.getContent();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return jsonConvert.fromJson(json, Painting.class);
    }

    public static Statue updateStatue(Statue updateStatue) throws IOException {
        Gson jsonConvert = new Gson();
        String statueJson = jsonConvert.toJson(updateStatue);
        Response response = RequestHandler.put(STATUE_API_URL + "/" + updateStatue.getId(), statueJson);
        String json = response.getContent();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return jsonConvert.fromJson(json, Statue.class);
    }

    public static boolean deletePainting(int id) throws IOException {
        Gson jsonConvert = new Gson();
        Response response = RequestHandler.delete(PAINTING_API_URL + "/" +  id);
        String json = response.getContent();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return response.getResponseCode() == 204;
    }

    public static boolean deleteStatue(int id) throws IOException {
        Gson jsonConvert = new Gson();
        Response response = RequestHandler.delete(STATUE_API_URL + "/" +  id);
        String json = response.getContent();
        if (response.getResponseCode() >= 400) {
            System.out.println(json);
            String message = jsonConvert.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return response.getResponseCode() == 204;
    }
}
