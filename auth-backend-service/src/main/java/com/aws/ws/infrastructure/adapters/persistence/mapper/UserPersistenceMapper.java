package com.aws.ws.infrastructure.adapters.persistence.mapper;

import com.aws.ws.domain.model.User;
import com.aws.ws.infrastructure.adapters.persistence.constants.UserDefinition;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Component
public class UserPersistenceMapper {

    public User toUser(Map<String, AttributeValue> item) {
        User user = new User();
        user.setUserId(item.get(UserDefinition.USER_ID).s());
        user.setEmail(item.get(UserDefinition.USER_EMAIL).s());
        user.setFirstName(item.get(UserDefinition.USER_FIRSTNAME).s());
        user.setLastName(item.get(UserDefinition.USER_LASTNAME).s());
        user.setPassword(item.get(UserDefinition.USER_PASSWORD).s());
        user.setRole(item.get(UserDefinition.USER_ROLE).s());
        return user;
    }
}
