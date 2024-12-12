package com.oop.cli.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class Client {
    private final String baseUrl;

    private final HttpClient client;

    private final Gson gson;

    public Client(final String baseUrl) {
        this.baseUrl = baseUrl;

        this.client = HttpClient.newHttpClient();

        this.gson = new Gson();
    }

    public <T> T get(final String endpoint, final Class<T> clazz) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T post(final String endpoint, final Object body, final Class<T> clazz) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T put(final String endpoint, final Object body, final Class<T> clazz) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endpoint))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
                    .build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}