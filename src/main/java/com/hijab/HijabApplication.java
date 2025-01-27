package com.hijab;

import com.hijab.common.aws.properties.AwsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AwsProperties.class)
public class HijabApplication {

	public static void main(String[] args) {
		SpringApplication.run(HijabApplication.class, args);
	}

}
