# rtx-grpc-practice

### Brief Description
This repository serves as a practice repo for integrating gRPC with a Spring Boot application. 
It demonstrates the implementation of all four gRPC connection types, 
providing a practical understanding of gRPC's versatility in microservices communication

##### Unary 
A simple, one-to-one communication where the client sends a single request and receives a single response from the server. 
Ideal for straightforward request-reply interactions.

![unary](/docs/unary.webp)
##### Server Streaming 
The client sends a request and receives a stream of responses from the server. 
Useful for situations where the server needs to send multiple pieces of data over time, 
such as real-time data feeds or large datasets that are too big to send in a single response.

![server-streaming](/docs/server-streaming.webp)
##### Client Streaming
The client streams multiple requests to the server, which then sends back a single response. 
This is particularly useful for scenarios where the client has a lot of data to send to the server, 
like file uploads or batch processing

![client-streaming](/docs/client-streaming.webp)
##### Bi Directional Streaming 
Both the client and server can send a stream of messages to each other. 
This type of communication is suitable for scenarios where both parties need to exchange information in real-time, 
such as in a live interactive session or chat application.

![bi-directional-streaming](/docs/bi-directional-streaming.webp)

---

### gRPC methods

The ``StreamObserver`` in gRPC is a key component used in handling streaming data, 
both in client-streaming and bi-directional streaming scenarios. 
An interface provided by gRPC that you implement to handle the stream of messages.

```java
StreamObserver<HelloRequest> requestStreamObserver = asynchronousClient.sayHello(new StreamObserver<HelloReply>() {
    @Override
    public void onNext(HelloReply value) {
        
    }

    @Override
    public void onError(Throwable t) {
        
    }

    @Override
    public void onCompleted() {
        
    }
});
```

``onNext(HelloReply value)`` 

This method is called every time a new message (HelloReply) is received in the stream. 
For a client, this is where you would handle each piece of data sent by the server.

``onError(Throwable t``) 

If there's an error during the streaming process (like a network issue), this method gets called. 
You can use it to handle the error, like logging it or notifying the user.

``onCompleted()`` 

This method is called once the stream is finished, meaning no more data will be sent. 
It's where you would typically perform cleanup operations or trigger actions that depend on the completion of the streaming.


