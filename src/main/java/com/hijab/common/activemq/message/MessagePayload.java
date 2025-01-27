package com.hijab.common.activemq.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record MessagePayload<T>(@JsonProperty("data") T data) implements Serializable {}
