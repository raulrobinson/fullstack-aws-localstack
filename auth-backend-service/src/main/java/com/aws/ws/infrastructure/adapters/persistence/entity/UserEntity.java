package com.aws.ws.infrastructure.adapters.persistence.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@DynamoDBTable(tableName = "${aws.dynamodb.table-name}")
public class UserEntity {
    @Id
    @DynamoDBHashKey(attributeName = "ID")
    private String ID;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String firstName;

    @DynamoDBAttribute
    private String lastName;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String role;
}
