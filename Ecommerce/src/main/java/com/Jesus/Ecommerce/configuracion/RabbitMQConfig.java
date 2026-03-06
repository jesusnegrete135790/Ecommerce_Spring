package com.Jesus.Ecommerce.configuracion;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NOTIFICACIONES = "cola_notificaciones";
    public static final String EXCHANGE_NOTIFICACIONES = "exchange_notificaciones";
    public static final String ROUTING_KEY_NOTIFICACIONES = "routing_notificaciones";

    @Bean
    public Queue queueNotificaciones() {
        return new Queue(QUEUE_NOTIFICACIONES, true);
    }

    @Bean
    public TopicExchange exchangeNotificaciones() {
        return new TopicExchange(EXCHANGE_NOTIFICACIONES);
    }

    @Bean
    public Binding bindingNotificaciones(Queue queueNotificaciones, TopicExchange exchangeNotificaciones) {
        return BindingBuilder.bind(queueNotificaciones).to(exchangeNotificaciones).with(ROUTING_KEY_NOTIFICACIONES);
    }
}
