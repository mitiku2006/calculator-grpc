package com.mit.calculator.server;

import com.mit.calculator.*;
import io.grpc.Status;
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
        if (request.getNumber() < 2) {
             Status status = Status.FAILED_PRECONDITION.withDescription("Invalid number " + request.getNumber());
            responseStreamObserver.onError(status.asRuntimeException());
            return;
        }
        getPrimes(request.getNumber(), responseStreamObserver);
        responseStreamObserver.onCompleted();
    }


    private void getPrimes(int num, StreamObserver<PrimeNumberResponse> responseStreamObserver) {
        int k = 2;
//        List<Integer> result = new ArrayList();
        while (num > 1) {
            if (num % k == 0) {
                responseStreamObserver.onNext(PrimeNumberResponse.newBuilder()
                        .setPrimeNumber(k)
                        .build());
                num = num / k;
            } else {
                k = k + 1;
            }
        }
    }
}
