package net.jaggerwang.sbip.adapter.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jagger Wang
 */
@SpringBootApplication(scanBasePackages = "net.jaggerwang.sbip")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
