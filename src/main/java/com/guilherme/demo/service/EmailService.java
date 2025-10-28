package com.guilherme.demo.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEmail(String destinatario, String assunto, String corpo) {
        System.out.println("[EMAIL] Para: " + destinatario);
        System.out.println("Assunto: " + assunto);
        System.out.println("Corpo: " + corpo);
        System.out.println("--------------------------------------------------");
    }

}
