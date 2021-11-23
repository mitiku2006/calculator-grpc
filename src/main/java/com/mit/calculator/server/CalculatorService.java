package com.mit.calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorService {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(45000)
                .addService(new CalculatorServiceImpl())
                .build();
        server.start();
        System.out.println("Server started at port: 45000");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfuly stopped the server");
        }));

        server.awaitTermination();
    }
}
