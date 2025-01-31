import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class WeatherApp {
    private static final String CITY = "Hyderabad";
    private static final String SEARCH_URL = "https://www.metaweather.com/api/location/search/?query=" + CITY;
    private static final String WEATHER_URL_TEMPLATE = "https://www.metaweather.com/api/location/";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        try {
            // Step 1: Get the WOEID for the city
            Request searchRequest = new Request.Builder()
                    .url(SEARCH_URL)
                    .build();

            try (Response searchResponse = client.newCall(searchRequest).execute()) {
                if (!searchResponse.isSuccessful()) throw new IOException("Unexpected code " + searchResponse);

                String searchResponseData = searchResponse.body().string();
                JsonArray searchJsonArray = JsonParser.parseString(searchResponseData).getAsJsonArray();

                if (searchJsonArray.size() == 0) {
                    System.out.println("City not found.");
                    return;
                }

                JsonObject cityJsonObject = searchJsonArray.get(0).getAsJsonObject();
                int woeid = cityJsonObject.get("woeid").getAsInt();

                // Step 2: Get the weather data using the WOEID
                String weatherUrl = WEATHER_URL_TEMPLATE + woeid + "/";
                Request weatherRequest = new Request.Builder()
                        .url(weatherUrl)
                        .build();

                try (Response weatherResponse = client.newCall(weatherRequest).execute()) {
                    if (!weatherResponse.isSuccessful()) throw new IOException("Unexpected code " + weatherResponse);

                    String weatherResponseData = weatherResponse.body().string();
                    JsonObject weatherJsonObject = JsonParser.parseString(weatherResponseData).getAsJsonObject();

                    JsonArray weatherArray = weatherJsonObject.get("consolidated_weather").getAsJsonArray();
                    JsonObject todayWeather = weatherArray.get(0).getAsJsonObject();

                    String cityName = weatherJsonObject.get("title").getAsString();
                    double temperature = todayWeather.get("the_temp").getAsDouble();
                    int humidity = todayWeather.get("humidity").getAsInt();
                    String weatherState = todayWeather.get("weather_state_name").getAsString();

                    System.out.println("City: " + cityName);
                    System.out.println("Temperature: " + temperature + "°C");
                    System.out.println("Humidity: " + humidity + "%");
                    System.out.println("Weather: " + weatherState);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
