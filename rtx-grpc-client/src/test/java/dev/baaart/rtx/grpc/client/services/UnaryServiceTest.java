package dev.baaart.rtx.grpc.client.services;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.models.unary.HelloReply;
import dev.baaart.rtx.grpc.models.unary.HelloRequest;
import dev.baaart.rtx.grpc.models.unary.UnaryServiceGrpc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UnaryServiceTest {

    @InjectMocks
    private UnaryService myComponent;

    @Mock
    private UnaryServiceGrpc.UnaryServiceBlockingStub unaryService;

    @Test
    void getName() {
        // Arrange
        String name = "bart";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + name).build();

        when(unaryService.sayHello(request)).thenReturn(reply);

        // Act
        Map<Descriptors.FieldDescriptor, Object> response = myComponent.getName(name);

        // Assert
        assertThat(response).containsValue("Hello ==> bart");
    }
}

