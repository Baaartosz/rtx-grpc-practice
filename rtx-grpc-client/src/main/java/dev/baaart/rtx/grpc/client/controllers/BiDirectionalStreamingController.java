package dev.baaart.rtx.grpc.client.controllers;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.client.services.BiDirectionalStreamingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BiDirectionalStreamingController {

    public BiDirectionalStreamingService biDirectionalStreamingService;

    @GetMapping("/bidirectionalstreaming/{names}")
    public List<Map<Descriptors.FieldDescriptor, Object>> getName(@PathVariable String names){
        return biDirectionalStreamingService.sayHello(List.of(names.split(",")));
    }
}
