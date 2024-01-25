package dev.baaart.rtx.grpc.service.services;

import dev.baaart.rtx.grpc.models.bidirectionalstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.bidirectionalstreaming.HelloRequest;
import dev.baaart.rtx.grpc.service.configs.GrpcUnitTestConfiguration;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig(classes = { GrpcUnitTestConfiguration.class })
class BiDirectionalStreamingServiceTest {

    @Autowired
    private BiDirectionalStreamingService biDirectionalStreamingService;

    @Test
    void happySayHello() throws Exception {
        StreamRecorder<HelloReply> responseObserver = StreamRecorder.create();
        StreamObserver<HelloRequest> requestObserver = biDirectionalStreamingService.sayHello(responseObserver);

        requestObserver.onNext(HelloRequest.newBuilder().setName("Alice").build());
        requestObserver.onNext(HelloRequest.newBuilder().setName("Bob").build());
        requestObserver.onCompleted();

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        assertEquals(2, responseObserver.getValues().size());

        assertEquals("Hello ==> Alice", responseObserver.getValues().get(0).getMessage());
        assertEquals("Hello ==> Bob", responseObserver.getValues().get(1).getMessage());
    }
}