package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.Servicios.NotificacionProducerService;
import com.Jesus.Ecommerce.configuracion.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionProducerServiceImpl implements NotificacionProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarCorreoBienvenida(String emailUsuario) {

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NOTIFICACIONES, RabbitMQConfig.ROUTING_KEY_NOTIFICACIONES, emailUsuario);
        System.out.println("📤 [Productor] Orden de correo enviada a la cola para: " + emailUsuario);
    }
}
