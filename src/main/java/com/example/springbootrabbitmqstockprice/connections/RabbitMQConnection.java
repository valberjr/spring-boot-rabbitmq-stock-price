package com.example.springbootrabbitmqstockprice.connections;

import com.example.springbootrabbitmqstockprice.constants.RabbitMQConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class RabbitMQConnection {

    private static final String EXCHANGE_NAME = "amq.direct";
    private final AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding relationship(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add() {
        var queueStock = this.queue(RabbitMQConstants.QUEUE_STOCK);
        var queuePrice = this.queue(RabbitMQConstants.QUEUE_PRICE);
        var exchange = this.directExchange();

        var relationshipStock = this.relationship(queueStock, exchange);
        var relationshipPrice = this.relationship(queuePrice, exchange);

        // queue creation
        this.amqpAdmin.declareQueue(queueStock);
        this.amqpAdmin.declareQueue(queuePrice);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(relationshipStock);
        this.amqpAdmin.declareBinding(relationshipPrice);
    }
}
