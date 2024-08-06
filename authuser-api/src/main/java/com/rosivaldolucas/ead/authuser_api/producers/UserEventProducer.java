package com.rosivaldolucas.ead.authuser_api.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rosivaldolucas.ead.authuser_api.dtos.UserEventDTO;
import com.rosivaldolucas.ead.authuser_api.enums.ActionType;

@Component
public class UserEventProducer {
  
  @Value("${ead.broker.exchange.userEvent}")
  private String exchangeUserEvent;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void producerUseEvent(UserEventDTO userEventDTO, ActionType actionType) {
    userEventDTO.setActionType(actionType.toString());

    this.rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDTO);
  }

}
