package com.example.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CompletableFutureService {

    private static Logger logger = LoggerFactory.getLogger(CompletableFutureService.class);

    @SneakyThrows({InterruptedException.class, ExecutionException.class,
            JsonMappingException.class})
    public void start() {
        // Fetch a list of Employee objects asynchronously by calling the hiredEmployees().
        //{ [ { "id": "1", "name":"Bob"},{ "id": "2", "name":"Alice"} ] }
        HttpRequest aRequest = HttpRequest.newBuilder()
                                          .uri(URI.create("http://localhost:8080/employees"))
                                          .timeout(Duration.ofMinutes(1))
                                          .header("Content-Type", "application/json")
                                          .build();
        CompletableFuture<HttpResponse<String>> aCompletionStage = HttpClient.newBuilder().build().sendAsync(aRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(aCompletionStage.get().body());

//         Join another CompletionStage<List> that takes care of filling the salary of each hired employee,
//         by calling the getSalary(hiredEmployeeId) method which returns a CompletionStage that
//         asynchronously fetches the salary (again could be consuming a REST endpoint).
//        { "id": "1", "salary":"10_000"}
        CompletableFuture<HttpResponse<String>> bCompletionStage = aCompletionStage.thenCompose(s -> {
            ObjectMapper mapper = new ObjectMapper();
            List<Employee> employeeList = null;
            try {
                employeeList = mapper.readValue(s.body(), new TypeReference<List<Employee>>() {
                });
            } catch (JsonProcessingException e) {
                logger.error("Error during json parsing!");
            }
            List<Long> employeeIds = employeeList.stream().map(Employee::getId).collect(Collectors.toList());

            List<CompletableFuture<HttpResponse<String>>> list = employeeIds.stream().map(id -> {
                HttpRequest bRequest = HttpRequest.newBuilder()
                                                  .uri(URI.create("http://localhost:8080/salary/" + id))
                                                  .header("Content-Type", "application/json")
                                                  .build();
                return HttpClient.newBuilder().build().sendAsync(bRequest, HttpResponse.BodyHandlers.ofString());
            }).collect(Collectors.toList());

            return list.get(0);
        });

        System.out.println(bCompletionStage.get().body());
//
////         { "id": "1", "salary":"10_000", name "Bob" }
////         When all Employee objects are filled with their salaries, we end up with a List<CompletionStage>,
////         so we call <special operation on CF> to get a final stage that completes upon completion of all these stages.
        HttpRequest cRequest = HttpRequest.newBuilder()
                                          .uri(URI.create("http://localhost:8080/employees"))
                                          .timeout(Duration.ofMinutes(1))
                                          .header("Content-Type", "application/json")
                                          .build();
        CompletableFuture<HttpResponse<String>> cCompletionStage = HttpClient.newBuilder().build().sendAsync(cRequest, HttpResponse.BodyHandlers.ofString());

        CompletableFuture<HttpResponse<String>> combinedDataCompletionStage = CompletableFuture.supplyAsync(() -> aCompletionStage).thenCompose(s -> CompletableFuture.supplyAsync(() -> bCompletionStage)).thenCompose(s -> CompletableFuture.supplyAsync(() -> cCompletionStage)).join();
//
////         Print hired Employees with their salaries via <special operation on CF> on the final stage.
        combinedDataCompletionStage.toCompletableFuture().thenAccept(s -> System.out.println("Computation returned: " + s.body()));
    }
}
