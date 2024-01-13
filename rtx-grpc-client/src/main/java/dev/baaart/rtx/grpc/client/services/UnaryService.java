package dev.baaart.rtx.grpc.client.services;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.models.unary.HelloReply;
import dev.baaart.rtx.grpc.models.unary.HelloRequest;
import dev.baaart.rtx.grpc.models.unary.UnaryServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UnaryService {

    @GrpcClient("rtx-grpc-service-unary")
    UnaryServiceGrpc.UnaryServiceBlockingStub synchronousClient;

    public Map<Descriptors.FieldDescriptor, Object> getName(String name){
        HelloRequest helloRequest = HelloRequest.newBuilder().setName(name).build();
        HelloReply helloReply = synchronousClient.sayHello(helloRequest);
        return helloReply.getAllFields();
    }
}
