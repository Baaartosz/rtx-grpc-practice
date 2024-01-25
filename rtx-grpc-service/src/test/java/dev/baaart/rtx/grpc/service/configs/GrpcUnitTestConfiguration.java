package dev.baaart.rtx.grpc.service.configs;

import dev.baaart.rtx.grpc.service.services.ClientStreamingService;
import dev.baaart.rtx.grpc.service.services.ServerStreamingService;
import dev.baaart.rtx.grpc.service.services.UnaryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcUnitTestConfiguration {

    @Bean
    UnaryService unaryService(){
        return new UnaryService();
    }

    @Bean
    ServerStreamingService serverStreamingService(){
        return new ServerStreamingService();
    }

    @Bean
    ClientStreamingService clientStreamingService(){
        return new ClientStreamingService();
    }
}
