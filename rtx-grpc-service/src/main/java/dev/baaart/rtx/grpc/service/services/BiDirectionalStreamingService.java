package dev.baaart.rtx.grpc.service.services;

import dev.baaart.rtx.grpc.models.bidirectionalstreaming.BiDirectionalServiceGrpc;
import dev.baaart.rtx.grpc.models.bidirectionalstreaming.HelloReply;
import dev.baaart.rtx.grpc.models.bidirectionalstreaming.HelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class BiDirectionalStreamingService extends BiDirectionalServiceGrpc.BiDirectionalServiceImplBase {
    @Override
    public StreamObserver<HelloRequest> sayHello(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            List<HelloReply> replyList = new ArrayList<>();
            @Override
            public void onNext(HelloRequest value) {
                HelloReply helloReply = HelloReply.newBuilder()
                        .setMessage("Hello ==> " + value.getName()).build();
                replyList.add(helloReply);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                replyList.forEach(responseObserver::onNext);
                responseObserver.onCompleted();
            }
        };
    }
}
