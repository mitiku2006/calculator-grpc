package com.mit.calculator.client;

import com.mit.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

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
        System.out.println(sumRequest.getFirstNum() +
                " + " +
                sumRequest.getSecondNum() +
                " = " +
                response.getSumResult()
        );

        stub.primeNumberDecomposition(
                     PrimeNumberRequest.newBuilder()
                        .setNumber(120)
                        .build()
        ).forEachRemaining(System.out::println);

        channel.shutdown();
    }
}
