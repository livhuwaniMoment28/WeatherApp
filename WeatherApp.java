import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.regex.*;

public class WeatherApp {

    private static final String API_KEY = "1516fe41c920442babd110027200105";
    private static final String API_BASE_URL = "http://api.weatherapi.com/v1/current.json";

    public static void showWeather(String location) {
        try {
            URL url = new URL(API_BASE_URL + "?key=" + API_KEY + "&q=" + location);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String json = reader.readLine();

            Map<String, String> weatherData = extractWeatherData(json);

            for (Entry<String, String> entry : weatherData.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> extractWeatherData(String json) {
        Map<String, String> weatherData = new HashMap<>();

        String[] data = json.split(",");
        for (String input : data) {
            addWeatherData(weatherData, input);
        }

        return weatherData;
    }

    private static void addWeatherData(Map<String, String> weatherData, String input) {
        if (input.contains("Provincen")) {
            weatherData.put("Province", getValueFromDatum(input));
        } else if (input.contains("country")) {
            weatherData.put("Country", getValueFromDatum(input));
        } else if (input.contains("localtime")) {
            weatherData.put("Localtime", getValueFromDatum(input));
        } else if (input.contains("temp_c")) {
            weatherData.put("Temperature(Celsius)", getValueFromDatum(input));
        } else if (input.contains("is_day")) {
            weatherData.put("Day/Night", input.charAt(input.length() - 1) == '1' ? "Day" : "Night");
        } else if (input.contains("text")) {
            weatherData.put("Condition", getValueFromDatum(input));
        }
    }

    private static String getValueFromDatum(String input) {
        return input.substring(input.lastIndexOf(":") + 1).replaceAll("[^a-zA-Z0-9 ]", "").trim();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a city name: ");
        String location = scanner.nextLine();

        System.out.println("\n\n\t\t\t\t=======================");
        System.out.println("\t\t\t\t|| Livs Weather Info ||");
        System.out.println("\t\t\t\t=======================\n\n");

        System.out.println("Given location: " + location + "\n\n");
        showWeather(location);
    }
}
