package com.project.publisher;

import com.project.publisher.service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PublisherApplication implements CommandLineRunner {

	@Autowired
	private BrokerService brokerService;

	public static void main(String[] args) {
		SpringApplication.run(PublisherApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String leaderBrokerUrl = brokerService.getLeaderBrokerUrl();
		System.out.println("Leader Broker URL: " + leaderBrokerUrl);
	}
}
