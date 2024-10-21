package omc.test.sensor.sensor.api;

import omc.test.sensor.sensor.entites.Sensor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SensorApiService {
    private final WebClient webClient = WebClient.create("http://localhost:8018/sensor");

    public void addSensor(Sensor sensor) {
        post("/addSensor", sensor, String.class)
                .subscribe(response -> {
                    System.out.println("Sensor added successfully: " + response);
                }, error -> {
                    System.err.println("Error adding sensor: " + error.getMessage());
                });
    }

    public void removeSensor(Sensor sensor) {
        post("/removeSensor", sensor, String.class)
                .subscribe(response -> {
                    System.out.println("Sensor removed successfully: " + response);
                }, error -> {
                    System.err.println("Error removing sensor: " + error.getMessage());
                });
    }

    // Generic POST method
    public <T, R> Mono<R> post(String uri, T requestBody, Class<R> responseType) {
        return webClient.post()
                .uri(uri)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType);
    }

    // Generic GET method
    public <R> Mono<R> get(String uri, Class<R> responseType) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }
}