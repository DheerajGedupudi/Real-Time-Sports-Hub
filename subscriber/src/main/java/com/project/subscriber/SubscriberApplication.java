package com.project.subscriber;

import com.project.subscriber.service.LeaderBrokerService;
import com.project.subscriber.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubscriberApplication implements CommandLineRunner {

	@Value("${server.port}")
	private int port;

	@Autowired
	private LeaderBrokerService leaderBrokerService;

	@Autowired
	private SubscriberService subscriberService;

	public static void main(String[] args) {
		SpringApplication.run(SubscriberApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String leaderBrokerUrl = leaderBrokerService.getLeaderBrokerUrl();
		System.out.println("Leader Broker URL: " + leaderBrokerUrl);
		subscriberService.setPort(String.valueOf(port));
		System.out.println("Available topics: " + subscriberService.getTopics());
	}
}
