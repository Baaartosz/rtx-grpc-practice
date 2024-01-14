package dev.baaart.rtx.grpc.client.services;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.models.clientstreaming.ClientStreamingServiceGrpc;
import dev.baaart.rtx.grpc.models.clientstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.clientstreaming.HelloRequest;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class ClientStreamingService {

    @GrpcClient("rtx-grpc-service-clientstreaming")
    ClientStreamingServiceGrpc.ClientStreamingServiceStub asynchronousClient;

    @SneakyThrows
    public Map<String,Map<Descriptors.FieldDescriptor, Object>> sayHelloToAll(List<String> names){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Map<String, Map<Descriptors.FieldDescriptor, Object>> response = new HashMap<>();
        StreamObserver<HelloRequest> requestStreamObserver = asynchronousClient.sayHello(new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                response.put("helloAll",value.getAllFields());
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

        names.forEach(s -> {
            HelloRequest helloRequest = HelloRequest.newBuilder().setName(s).build();
            requestStreamObserver.onNext(helloRequest);
        });
        requestStreamObserver.onCompleted();
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyMap();
    }


}
