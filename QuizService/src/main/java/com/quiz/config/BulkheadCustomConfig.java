package com.quiz.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;

@Configuration
public class BulkheadCustomConfig {
	
	@Bean
	BulkheadRegistry bulkheadRegistry(BulkheadConfig config) {
		return BulkheadRegistry.of(config);
	}
	@Bean
	BulkheadConfig bulkheadConfig() {
		return BulkheadConfig.custom().maxConcurrentCalls(1)
				.maxWaitDuration(Duration.ofMillis(0))
				.build();
	}
	
//	BulkheadConfig config = BulkheadConfig.custom()
//		  	.maxConcurrentCalls(150)
//		  	.maxWaitDuration(Duration.ofMillis(500))
//		  	.build();
//	// Create a BulkheadRegistry with a custom global configuration
//	BulkheadRegistry registry = BulkheadRegistry.of(config);
	
	// Get or create a Bulkhead from the registry - 
	// bulkhead will be backed by the default config
//	Bulkhead bulkheadWithDefaultConfig = registry.bulkhead("name1");
//
//	// Get or create a Bulkhead from the registry, 
//	// use a custom configuration when creating the bulkhead
//	Bulkhead bulkheadWithCustomConfig = registry.bulkhead("name2", custom);
}
