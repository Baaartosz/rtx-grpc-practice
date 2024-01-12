package dev.baaart.rtxgrpcserviceapp;


import dev.baaart.rtx.grpc.examples.lib.HelloReply;
import dev.baaart.rtx.grpc.examples.lib.HelloRequest;
import dev.baaart.rtx.grpc.examples.lib.MyServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MyServiceImpl extends MyServiceGrpc.MyServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello ==> " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}

