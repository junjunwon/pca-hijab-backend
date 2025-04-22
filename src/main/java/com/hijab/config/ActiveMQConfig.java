package com.hijab.config;

import com.hijab.common.activemq.converter.MessagePayloadMessageConverter;
import jakarta.jms.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableJms
@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String borkerUrl;

    private final MessagePayloadMessageConverter messageConverter;

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        // 스레드풀 설정
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20); // 기본 스레드 수
        taskExecutor.setMaxPoolSize(50);  // 최대 스레드 수
        taskExecutor.setQueueCapacity(1000); // 큐에 쌓일 수 있는 작업 수

        // 초과된 요청에 대한 처리 - 요청을 드롭
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        taskExecutor.initialize();

        factory.setTaskExecutor(taskExecutor); // 스레드풀을 JMS 리스너에 설정
        factory.setConcurrency("10-50"); // 동시 처리 리스너 수 설정

        factory.setConnectionFactory(connectionFactory());
//        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(borkerUrl);
        activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.hijab.common.activemq.message"));
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    @Bean
    public BrokerService broker() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("embedded-broker");
        broker.setPersistent(true);
        broker.setUseShutdownHook(false);
        broker.addConnector(borkerUrl);
        broker.start();

        return broker;
    }

}