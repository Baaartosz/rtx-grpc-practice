package dev.baaart.rtx.grpc.service.app.services;

import dev.baaart.rtx.grpc.models.unary.HelloReply;
import dev.baaart.rtx.grpc.models.unary.HelloRequest;
import dev.baaart.rtx.grpc.models.unary.UnaryServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UnaryService extends UnaryServiceGrpc.UnaryServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String message = "Hello ==> " + request.getName();
        HelloReply reply = HelloReply.newBuilder()
                .setMessage(message)
                .build();
        responseObserver.onNext(reply);
        //Status codes are pre-defined for example 10 is Aborted
        //responseObserver.onError(Status.ABORTED.asException);
        responseObserver.onCompleted();
    }

}

