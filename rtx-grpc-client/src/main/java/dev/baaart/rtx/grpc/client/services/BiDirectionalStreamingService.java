package dev.baaart.rtx.grpc.client.services;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.models.bidirectionalstreaming.BiDirectionalServiceGrpc;
import dev.baaart.rtx.grpc.models.bidirectionalstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.bidirectionalstreaming.HelloRequest;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class BiDirectionalStreamingService {

    @GrpcClient("rtx-grpc-service-bidirectionalstreaming")
    BiDirectionalServiceGrpc.BiDirectionalServiceStub asynchronousClient;


    @SneakyThrows
    public List<Map<Descriptors.FieldDescriptor, Object>> sayHello(List<String> names){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        StreamObserver<HelloRequest> requestStreamObserver = asynchronousClient.sayHello(new StreamObserver<HelloReply>() {
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

        names.forEach(name -> {
            requestStreamObserver.onNext(HelloRequest.newBuilder().setName(name).build());
        });
        requestStreamObserver.onCompleted();

        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }
}
