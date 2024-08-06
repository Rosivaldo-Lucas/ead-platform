package com.rosivaldolucas.ead.course_api.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rosivaldolucas.ead.course_api.dtos.UserEventDTO;
import com.rosivaldolucas.ead.course_api.enums.ActionType;
import com.rosivaldolucas.ead.course_api.models.User;
import com.rosivaldolucas.ead.course_api.services.UserService;

@Component
public class UserConsumer {

  @Autowired
  private UserService userService;

  @RabbitListener(
    bindings = @QueueBinding(
      value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
      exchange = @Exchange(value = "${ead.broker.exchange.userEvent}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    )
  )
  public void listenUserEvent(@Payload UserEventDTO userEventDTO) {
    User user = userEventDTO.convertToUser();

    switch (ActionType.valueOf(userEventDTO.getActionType())) {
      case CREATE:
      case UPDATE:
        this.userService.save(user);
        break;

      case DELETE:
        this.userService.delete(userEventDTO.getId());
        break;
    
      default:
        break;
    }
  }

}
