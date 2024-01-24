package dev.baaart.rtx.grpc.service.services;

import dev.baaart.rtx.grpc.models.unary.HelloReply;
import dev.baaart.rtx.grpc.models.unary.HelloRequest;
import dev.baaart.rtx.grpc.service.configs.GrpcUnitTestConfiguration;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@SpringJUnitConfig(classes = { GrpcUnitTestConfiguration.class } )
class UnaryServiceTest {

    @Autowired
    private UnaryService unaryService;

    @SneakyThrows
    @Test
    void happySayHelloVersion1() {
        HelloRequest request = HelloRequest.newBuilder()
                .setName("Test")
                .build();

        StreamObserver<HelloReply> responseObserver = mock(StreamObserver.class);
        unaryService.sayHello(request, responseObserver);

        ArgumentCaptor<HelloReply> replyCaptor = ArgumentCaptor.forClass(HelloReply.class);
        verify(responseObserver).onNext(replyCaptor.capture());
        verify(responseObserver).onCompleted();
        verifyNoMoreInteractions(responseObserver);

        HelloReply reply = replyCaptor.getValue();
        assertEquals("Hello ==> Test", reply.getMessage());
    }

    @SneakyThrows
    @Test
    void happySayHelloVersion2() {
        HelloRequest request = HelloRequest.newBuilder()
                .setName("Test")
                .build();
        StreamRecorder<HelloReply> responseObserver = StreamRecorder.create();
        unaryService.sayHello(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        List<HelloReply> results = responseObserver.getValues();
        assertEquals(1, results.size());
        HelloReply response = results.get(0);
        assertEquals(HelloReply.newBuilder()
                .setMessage("Hello ==> Test")
                .build(), response);
    }
}