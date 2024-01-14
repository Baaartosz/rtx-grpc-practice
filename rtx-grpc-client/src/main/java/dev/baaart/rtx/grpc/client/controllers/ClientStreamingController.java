package dev.baaart.rtx.grpc.client.controllers;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.client.services.ClientStreamingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ClientStreamingController {

    public ClientStreamingService clientStreamingService;

    @GetMapping("/clientstreaming/{names}")
    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getName(@PathVariable String names){
        return clientStreamingService.sayHelloToAll(List.of(names.split(",")));
    }

}
