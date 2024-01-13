package dev.baaart.rtx.grpc.service.models;

public enum Greeting {
    ENGLISH("Hello"),
    RUSSIAN("Privet"),
    POLISH("Cześć"),
    SPANISH("Hola"),
    FRENCH("Bonjour"),
    GERMAN("Hallo"),
    ITALIAN("Ciao"),
    PORTUGUESE("Olá"),
    JAPANESE("Konnichiwa"),
    CHINESE("Nǐ hǎo");

    private final String greeting;

    Greeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
