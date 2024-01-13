package dev.baaart.rtx.grpc.service.services;

import dev.baaart.rtx.grpc.models.serverstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.serverstreaming.HelloRequest;
import dev.baaart.rtx.grpc.models.serverstreaming.ServerStreamingServiceGrpc;
import dev.baaart.rtx.grpc.service.models.Greeting;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ServerStreamingService extends ServerStreamingServiceGrpc.ServerStreamingServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        for (Greeting greeting : Greeting.values()) {
            HelloReply helloReply = HelloReply.newBuilder()
                    .setMessage(greeting.getGreeting() + " ==> " + request.getName()).build();
            responseObserver.onNext(helloReply);
        }
        responseObserver.onCompleted();
    }
}
