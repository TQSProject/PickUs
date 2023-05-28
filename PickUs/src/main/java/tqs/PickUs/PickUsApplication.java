package tqs.PickUs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PickUsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickUsApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadInitialData() throws Exception {
		System.out.println("Loading initial data...");

		String apiUrl, requestBody;

		apiUrl = "http://localhost:8080/api/v1/acps";
		requestBody = "{\"name\":\"Worten Lisboa\", \"city\": \"Lisboa\"}";
		POST(apiUrl, requestBody);
		requestBody = "{\"name\":\"Fnac Aveiro\", \"city\": \"Aveiro\"}";
		POST(apiUrl, requestBody);
		requestBody = "{\"name\":\"Continente Glicinias\", \"city\": \"Aveiro\"}";
		POST(apiUrl, requestBody);
		requestBody = "{\"name\":\"Continente Porto\", \"city\": \"Porto\"}";
		POST(apiUrl, requestBody);

		apiUrl = "http://localhost:8080/api/v1/acps/1";
		requestBody = "{\"status\":\"APPROVED\"}";
		POST(apiUrl, requestBody);
		apiUrl = "http://localhost:8080/api/v1/acps/Fnac Aveiro";
		requestBody = "{\"status\":\"APPROVED\"}";
		POST(apiUrl, requestBody);
		apiUrl = "http://localhost:8080/api/v1/acps/Continente Glicinias";
		requestBody = "{\"status\":\"refused\"}";
		POST(apiUrl, requestBody);

		Gson gson = new Gson();

		apiUrl = "http://localhost:8080/api/v1/orders";
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("store", "eStore");
		data.put("buyer", "Ricardo");
		ArrayList<HashMap<String, Object>> products = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> product1 = new HashMap<String, Object>();
		HashMap<String, Object> product2 = new HashMap<String, Object>();
		HashMap<String, Object> product3 = new HashMap<String, Object>();
		product1.put("name", "Toothpaste Nax Pro");
		product1.put("count", 3);
		product2.put("name", "PS4");
		product3.put("name", "Samsung Galaxy S10");
		product3.put("count", 2);
		products.add(product1);
		products.add(product2);
		products.add(product3);
		data.put("products", products);
		data.put("acp", 1);
		requestBody = gson.toJson(data);
		POST(apiUrl, requestBody);

		data = new HashMap<String, Object>();
		data.put("store", "eStore");
		data.put("buyer", "Ricardo");
		products = new ArrayList<HashMap<String, Object>>();
		product1 = new HashMap<String, Object>();
		product1.put("name", "PS4");
		product1.put("count", 2);
		products.add(product1);
		data.put("products", products);
		data.put("acp", "Fnac Aveiro");
		requestBody = gson.toJson(data);
		POST(apiUrl, requestBody);

		data = new HashMap<String, Object>();
		data.put("store", "eStore");
		data.put("buyer", "Daniel");
		products = new ArrayList<HashMap<String, Object>>();
		product1 = new HashMap<String, Object>();
		product1.put("name", "Luso Water 1L");
		product1.put("count", 1);
		products.add(product1);
		data.put("products", products);
		data.put("acp", "Fnac Aveiro");
		requestBody = gson.toJson(data);
		POST(apiUrl, requestBody);

		TimeUnit.SECONDS.sleep(2);

		apiUrl = "http://localhost:8080/api/v1/orders/1";
		requestBody = "{\"status\":\"DELIVERING\"}";
		POST(apiUrl, requestBody);

		apiUrl = "http://localhost:8080/api/v1/orders/2";
		requestBody = "{\"status\":\"DELIVERING\"}";
		POST(apiUrl, requestBody);

		TimeUnit.SECONDS.sleep(2);
		requestBody = "{\"status\":\"delivered_and_waiting_for_pickup\"}";
		POST(apiUrl, requestBody);

		System.out.println("Initial data loaded!");
	}
	

    public void POST(String apiUrl, String requestBody) throws IOException {
		apiUrl = apiUrl.replaceAll(" ", "%20");
		System.out.println("POST");
		System.out.println(apiUrl);
		System.out.println(requestBody);

        // Create the URL object with the provided apiUrl
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(requestBody.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        //System.out.println(response.toString());
        connection.disconnect();
    }
}
