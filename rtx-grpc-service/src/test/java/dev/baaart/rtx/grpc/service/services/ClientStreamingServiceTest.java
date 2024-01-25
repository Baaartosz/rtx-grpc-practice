package dev.baaart.rtx.grpc.service.services;

import dev.baaart.rtx.grpc.models.clientstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.clientstreaming.HelloRequest;
import dev.baaart.rtx.grpc.service.configs.GrpcUnitTestConfiguration;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig(classes = { GrpcUnitTestConfiguration.class } )
class ClientStreamingServiceTest {

    @Autowired
    private ClientStreamingService clientStreamingService;

    @SneakyThrows
    @Test
    void sayHello() {
        StreamRecorder<HelloReply> responseObserver = StreamRecorder.create();
        StreamObserver<HelloRequest> requestObserver = clientStreamingService.sayHello(responseObserver);

        requestObserver.onNext(HelloRequest.newBuilder().setName("Alice").build());
        requestObserver.onNext(HelloRequest.newBuilder().setName("Bob").build());
        requestObserver.onNext(HelloRequest.newBuilder().setName("Charlie").build());
        requestObserver.onCompleted();

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertEquals(1, responseObserver.getValues().size());

        HelloReply reply = responseObserver.getValues().get(0);
        assertEquals("Hello Alice, Bob, Charlie", reply.getMessage());

    }
}