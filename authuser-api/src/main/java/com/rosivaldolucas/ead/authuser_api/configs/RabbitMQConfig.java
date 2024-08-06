package com.rosivaldolucas.ead.authuser_api.configs;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitMQConfig {

  @Value("${ead.broker.exchange.userEvent}")
  private String exchangeUserEvent;

  @Autowired
  private CachingConnectionFactory cachingConnectionFactory;

  @Bean
  public RabbitTemplate rabbitTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(this.cachingConnectionFactory);
    rabbitTemplate.setMessageConverter(this.messageConverter());

    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    return new Jackson2JsonMessageConverter(objectMapper);
  }

  @Bean
  public FanoutExchange fanoutExchangeUserEvent() {
    return new FanoutExchange(this.exchangeUserEvent);
  }

}
