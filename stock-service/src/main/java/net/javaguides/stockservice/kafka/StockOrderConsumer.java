package net.javaguides.stockservice.kafka;

import net.javaguides.basedomains.dto.OrderEvent;
import net.javaguides.stockservice.entity.OrderEventDetails;
import net.javaguides.stockservice.repository.OrderEventDetailsRepository;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StockOrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockOrderConsumer.class);

    private OrderEventDetailsRepository repository;

    private final Long lowerBound = 1000L;

    private final Long upperBound = 30000L;

    public StockOrderConsumer(OrderEventDetailsRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent orderEvent) {

        LOGGER.info(String.format("Order event received in stock service => %s", orderEvent.toString()));

        // Save order event detail into database
        OrderEventDetails orderEventDetails = new OrderEventDetails();
        orderEventDetails.setOrderNumber(RandomUtils.nextLong(lowerBound, upperBound));
        orderEventDetails.setProdName(orderEvent.getOrder().getName());
        orderEventDetails.setQty(orderEvent.getOrder().getQty());
        orderEventDetails.setPrice(orderEvent.getOrder().getPrice());

        repository.save(orderEventDetails);
    }
}
