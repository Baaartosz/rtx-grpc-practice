package dev.baaart.rtx.grpc.client.controllers;

import com.google.protobuf.Descriptors;
import dev.baaart.rtx.grpc.client.services.UnaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UnaryController {

    public UnaryService unaryService;

    @GetMapping("/unary/{name}")
    public Map<Descriptors.FieldDescriptor, Object> getName(@PathVariable String name){
        return unaryService.getName(name);
    }

}
