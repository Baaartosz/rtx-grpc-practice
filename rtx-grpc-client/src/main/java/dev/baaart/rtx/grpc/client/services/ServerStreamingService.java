package dev.baaart.rtx.grpc.client.services;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.models.serverstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.serverstreaming.HelloRequest;
import dev.baaart.rtx.grpc.models.serverstreaming.ServerStreamingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class ServerStreamingService {

    @GrpcClient("rtx-grpc-service-serverstreaming")
    ServerStreamingServiceGrpc.ServerStreamingServiceStub  asynchronousClient;

    @SneakyThrows
    public List<Map<Descriptors.FieldDescriptor, Object>> getName(String name){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();

        HelloRequest helloRequest = HelloRequest.newBuilder().setName(name).build();
        asynchronousClient.sayHello(helloRequest, new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                response.add(value.getAllFields());
            }

            @Override
            public void onError(Throwable t) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }

}
