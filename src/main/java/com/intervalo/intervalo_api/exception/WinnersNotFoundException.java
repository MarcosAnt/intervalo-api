package com.intervalo.intervalo_api.exception;


public class WinnersNotFoundException extends Exception{

    public WinnersNotFoundException() {
        super("Nenhum filme vencedor encontrado.");
    }

    public WinnersNotFoundException(String message) {
        super(message);
    }
}
