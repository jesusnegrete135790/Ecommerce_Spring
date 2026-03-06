package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.Servicios.NotificacionConsumerService;
import com.Jesus.Ecommerce.configuracion.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionConsumerServiceImpl implements NotificacionConsumerService {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICACIONES)
    public void procesarCorreo(String emailDestino) {
        System.out.println("[Consumidor] Preparando correo real para: " + emailDestino);

        try {
            // 1. Creamos la estructura del correo
            SimpleMailMessage mensaje = new SimpleMailMessage();

            // Reemplaza con el correo que configuraste en properties
            mensaje.setFrom("tu_correo@gmail.com");
            mensaje.setTo(emailDestino);
            mensaje.setSubject("¡Bienvenido a nuestro E-commerce! ");
            mensaje.setText("Hola!\n\nGracias por registrarte en nuestra plataforma. " +
                    "Estamos emocionados de tenerte con nosotros.\n\n" +
                    "Explora nuestro catálogo y descubre los mejores productos.");


            mailSender.send(mensaje);

            System.out.println("✅ [Consumidor] ¡Correo electrónico enviado con éxito a " + emailDestino + "!");

        } catch (Exception e) {
            System.err.println("Error crítico al enviar el correo a " + emailDestino);
            e.printStackTrace();

        }
    }

}
