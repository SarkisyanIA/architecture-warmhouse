package com.example.temperature_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@SpringBootApplication
@RestController
public class TemperatureApiApplication {
	private final Random random = new Random();

	public static void main(String[] args) {
		new SpringApplicationBuilder(TemperatureApiApplication.class)
				.properties("server.port=8081")
				.run(args);
	}

	@GetMapping("/temperature")
	public TemperatureResponse getTemperature(@RequestParam String location) {
		int temperature = random.nextInt(61) - 20;

		return new TemperatureResponse(location, temperature);
	}

	static class TemperatureResponse {
		private final String location;
		private final int temperature;

		public TemperatureResponse(String location, int temperature) {
			this.location = location;
			this.temperature = temperature;
		}

		public String getLocation() {
			return location;
		}

		public int getTemperature() {
			return temperature;
		}
	}
}
