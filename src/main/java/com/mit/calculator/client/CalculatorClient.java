package com.mit.calculator.client;

import com.mit.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class CalculatorClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 45000)
                .usePlaintext()
                .build();
        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        SumRequest sumRequest = SumRequest.newBuilder()
                .setFirstNum(5)
                .setSecondNum(8)
                .build();
        SumResponse response = stub.sum(sumRequest);
        System.out.println(new StringBuilder()
                .append(sumRequest.getFirstNum())
                .append(" + ")
                .append(sumRequest.getSecondNum())
                .append(" = ")
                .append(response.getSumResult())
        );

        try {
            stub.primeNumberDecomposition(
                    PrimeNumberRequest.newBuilder()
                            .setNumber(2222980)
                            .build()
            ).forEachRemaining(System.out::println);
        } catch (StatusRuntimeException ex) {
            System.out.println("Error: " + ex.getStatus().getDescription());
        }
        channel.shutdown();
    }
}
