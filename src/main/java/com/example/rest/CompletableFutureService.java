package com.example.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureService {

    public void start() {
        // Fetch a list of Employee objects asynchronously by calling the hiredEmployees().
        HttpRequest aRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/employees"))
                .timeout(Duration.ofMinutes(1)).header("Content-Type", "application/json").build();
        CompletableFuture<HttpResponse<String>> aCompletionStage = HttpClient.newBuilder()
                .build()
                .sendAsync(aRequest, HttpResponse.BodyHandlers.ofString());

        // Join another CompletionStage<List> that takes care of filling the salary of each hired employee,
        // by calling the getSalary(hiredEmployeeId) method which returns a CompletionStage that
        // asynchronously fetches the salary (again could be consuming a REST endpoint).
        HttpRequest bRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/salary"))
                .timeout(Duration.ofMinutes(1)).header("Content-Type", "application/json").build();
        CompletableFuture<HttpResponse<String>> bCompletionStage = HttpClient.newBuilder()
                .build()
                .sendAsync(bRequest, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<Void> combinedDataCompletionStage = CompletableFuture.allOf(
                aCompletionStage, bCompletionStage);

        // When all Employee objects are filled with their salaries, we end up with a List<CompletionStage>,
        // so we call <special operation on CF> to get a final stage that completes upon completion of all these stages.
        combinedDataCompletionStage.join();

        // Print hired Employees with their salaries via <special operation on CF> on the final stage.
        combinedDataCompletionStage.toCompletableFuture().thenAccept(s -> System.out.println("Computation returned: " + s));
    }
}
