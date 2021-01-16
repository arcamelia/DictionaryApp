package ui;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static String BASE_URL = "https://wordsapiv1.p.rapidapi.com/words";

    public TextField textField;

    public void handleDefinitionButtonPressed(ActionEvent actionEvent) {
        String word = textField.getText();
        JSONObject response = getRequest(word, "definition");
        List<String> definitions = parseJsonArray(response.getJSONArray("definition"));
        System.out.println(viewStringList(definitions));

    }

    public void handleSynonymButtonPressed(ActionEvent actionEvent) {
        String word = textField.getText();
        JSONObject results = getRequest(word, "synonyms");
        List<String> synonyms = parseJsonArray(results.getJSONArray("synonyms"));
        System.out.println(viewStringList(synonyms));

    }

    public JSONObject getRequest(String word, String requestType) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + word + "/" + requestType))
                .header("x-rapidapi-key", "e49bb86f6dmsha3985f552b1baf4p1b9c78jsn611f339dacaa")
                .header("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new JSONObject(response.body());
    }

    public List<String> parseJsonArray(JSONArray array) {
        List<String> stringList = new ArrayList<>();
        for (Object s : array) {
            String string = (String) s;
            stringList.add(string);
        }
        return stringList;
    }

    public String viewStringList(List<String> stringList) {
        String printout = "";
        for (String s : stringList) {
            printout = printout.concat("\n" + s);
        }
        return printout;
    }

}
