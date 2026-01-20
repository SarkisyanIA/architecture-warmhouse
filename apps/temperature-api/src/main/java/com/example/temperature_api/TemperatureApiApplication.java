package com.example.temperature_api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@SpringBootApplication
@RestController
public class TemperatureApiApplication {
	private final Random random = new Random();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	public static void main(String[] args) {
		new SpringApplicationBuilder(TemperatureApiApplication.class)
				.properties("server.port=8081")
				.run(args);
	}

	// Эндпоинт для получения температуры по местоположению
	@GetMapping("/temperature")
	public TemperatureResponse getTemperature(@RequestParam(required = false) String location,
											  @RequestParam(required = false) String id) {
		// Генерируем случайную температуру от -20 до 40
		double temperature = random.nextInt(61) - 20 + random.nextDouble();

		String loc;
		String desc;

		// Определяем location и description на основе параметров
		if (location != null && !location.isEmpty()) {
			loc = location;
			desc = "Current temperature at " + location;
		} else if (id != null && !id.isEmpty()) {
			loc = "Sensor_" + id;
			desc = "Current temperature for sensor " + id;
		} else {
			loc = "Unknown_Location";
			desc = "Current temperature";
		}

		// Определяем статус на основе температуры
		String status = determineStatus(temperature);
		String timestamp = Instant.now().toString();

		return new TemperatureResponse(
				loc,
				Math.round(temperature * 10.0) / 10.0, // Округляем до 1 знака после запятой
				"°C",
				status,
				timestamp,
				desc
		);
	}

	// Эндпоинт для получения температуры по ID датчика
	@GetMapping("/temperature/{id}")
	public TemperatureResponse getTemperatureById(@PathVariable String id) {
		// Генерируем случайную температуру от -20 до 40
		double temperature = random.nextInt(61) - 20 + random.nextDouble();

		String status = determineStatus(temperature);
		String timestamp = Instant.now().toString();

		return new TemperatureResponse(
				"Sensor_" + id,
				Math.round(temperature * 10.0) / 10.0,
				"°C",
				status,
				timestamp,
				"Current temperature for sensor " + id
		);
	}

	// Вспомогательный метод для определения статуса
	private String determineStatus(double temperature) {
		if (temperature < -10) {
			return "critical";
		} else if (temperature < 0) {
			return "warning";
		} else if (temperature > 35) {
			return "warning";
		} else if (temperature > 40) {
			return "critical";
		} else {
			return "normal";
		}
	}

	static class TemperatureResponse {
		private final String location;
		private final double value;
		private final String unit;
		private final String status;
		private final String timestamp;
		private final String description;

		public TemperatureResponse(String location, double value, String unit,
								   String status, String timestamp, String description) {
			this.location = location;
			this.value = value;
			this.unit = unit;
			this.status = status;
			this.timestamp = timestamp;
			this.description = description;
		}

		// Геттеры должны соответствовать именам полей в JSON
		public String getLocation() {
			return location;
		}

		public double getValue() {
			return value;
		}

		public String getUnit() {
			return unit;
		}

		public String getStatus() {
			return status;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public String getDescription() {
			return description;
		}
	}
}