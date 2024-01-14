package dev.baaart.rtx.grpc.service.services;

import dev.baaart.rtx.grpc.models.clientstreaming.ClientStreamingServiceGrpc;
import dev.baaart.rtx.grpc.models.clientstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.clientstreaming.HelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ClientStreamingService extends ClientStreamingServiceGrpc.ClientStreamingServiceImplBase {

    @Override
    public StreamObserver<HelloRequest> sayHello(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            final StringBuilder helloStringBuilder = new StringBuilder("Hello");
            @Override
            public void onNext(HelloRequest value) {
                helloStringBuilder
                        .append(" ")
                        .append(value.getName())
                        .append(",");
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                helloStringBuilder.deleteCharAt(helloStringBuilder.length()-1);
                HelloReply helloReply = HelloReply.newBuilder()
                        .setMessage(helloStringBuilder.toString()).build();

                responseObserver.onNext(helloReply);
                responseObserver.onCompleted();
            }
        };
    }
}
