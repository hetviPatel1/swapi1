package swapi.dev;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class test {
    public static void main(String[] args) throws Exception {
        // Generate random character IDs and starship ID
        Random rand = new Random();
        int charId1 = rand.nextInt(15) + 1;
        int charId2 = rand.nextInt(15) + 1;
        int starshipId = rand.nextInt(30) + 1;

        // Get character data from API
        String charData1 = getData("https://swapi.dev/api/people/" + charId1);
        String charData2 = getData("https://swapi.dev/api/people/" + charId2);

        // Get starship data from API
        String starshipData = getData("https://swapi.dev/api/starships/" + starshipId);

        // Extract character surnames
        String surname1 = extractSurname(charData1);
        String surname2 = extractSurname(charData2);

        // Determine character honourifics
        String honourific1 = getHonourific(charData1);
        String honourific2 = getHonourific(charData2);

        // Print sentence
        System.out.println(honourific1 + " " + surname1 + " and " + honourific2 + " " + surname2 +
                " cruising around in their " + extractStarshipName(starshipData));
    }

    // Returns the data from the specified URL as a String
    private static String getData(String url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    // Returns the character's surname
    private static String extractSurname(String data) {
        int index = data.indexOf("name");
        int start = data.indexOf(" ", index + 7) + 1;
        int end = data.indexOf("\"", start);
        return data.substring(start, end);
    }

    // Returns the character's honourific
    private static String getHonourific(String data) {
        int index = data.indexOf("gender");
        int start = data.indexOf(" ", index + 8) + 1;
        int end = data.indexOf("\"", start);
        String gender = data.substring(start, end);
        return gender.equals("male") ? "Mr." : "Ms.";
    }

    // Returns the starship's name
    private static String extractStarshipName(String data) {
        int index = data.indexOf("name");
        int start = data.indexOf(" ", index + 7) + 1;
        int end = data.indexOf("\"", start);
        return data.substring(start, end);
    }
}
