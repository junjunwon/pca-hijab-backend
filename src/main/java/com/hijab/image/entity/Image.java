package com.hijab.image.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.hijab.common.converter.LocalDateTimeConverter;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@DynamoDBTable(tableName = "Images")
public class Image {
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "url")
    private String url;

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime expiryDate;

    public Image(String id, String name, String url, LocalDateTime expiryDate) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.expiryDate = expiryDate;
    }
}
