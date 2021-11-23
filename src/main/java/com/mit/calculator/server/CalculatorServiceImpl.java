package com.mit.calculator.server;

import com.mit.calculator.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        SumResponse sumResponse = SumResponse.newBuilder()
                .setSumResult(request.getFirstNum() + request.getSecondNum())
                .build();
        responseObserver.onNext(sumResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void primeNumberDecomposition(PrimeNumberRequest request,
                                         StreamObserver<PrimeNumberResponse> responseStreamObserver) {
        List<Integer> primes = getPrimes(request.getNumber());
            primes.forEach(e -> {
                PrimeNumberResponse response = PrimeNumberResponse.newBuilder()
                        .setPrimeNumber(e)
                        .build();
                responseStreamObserver.onNext(response);
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
            });
        responseStreamObserver.onCompleted();
    }


    private List<Integer> getPrimes(int num) {
        int k = 2;
        List<Integer> result = new ArrayList();
        while (num > 1) {
            if (num % k == 0) {
                result.add(k);
                num = num / k;
            } else {
                k = k + 1;
            }
        }
        return result;
    }
}
