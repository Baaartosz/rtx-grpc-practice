package dev.baaart.rtx.grpc.service.services;

import dev.baaart.rtx.grpc.models.serverstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.serverstreaming.HelloRequest;
import dev.baaart.rtx.grpc.service.configs.GrpcUnitTestConfiguration;
import dev.baaart.rtx.grpc.service.models.Greeting;
import io.grpc.internal.testing.StreamRecorder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig(classes = { GrpcUnitTestConfiguration.class } )
class ServerStreamingServiceTest {

    @Autowired
    private ServerStreamingService serverStreamingService;

    @SneakyThrows
    @Test
    void happySayHello() {
        String name = "Test";
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();

        StreamRecorder<HelloReply> responseObserver = StreamRecorder.create();
        serverStreamingService.sayHello(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        List<HelloReply> results = responseObserver.getValues();
        assertEquals(10, results.size());


        List<HelloReply> expectedResults = generateExpectedGreetingsReply(name);
        for (int i = 0; i < results.size(); i++) {
            assertEquals(expectedResults.get(i), results.get(i));
        }
    }

    private List<HelloReply> generateExpectedGreetingsReply(String name){
        return Arrays.stream(Greeting.values())
                .map(greeting -> HelloReply.newBuilder()
                        .setMessage(greeting.getGreeting() + " ==> " + name)
                        .build())
                .collect(Collectors.toList());
    }
}