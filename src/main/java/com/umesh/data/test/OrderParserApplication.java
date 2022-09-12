package com.umesh.data.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class OrderParserApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(OrderParserApplication.class);

	@Autowired
	private TaskWorker taskWorker;

	public static void main(String[] args) {
		SpringApplication.run(OrderParserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Files name {}", Arrays.toString(args));
		if(args.length < 1){
			throw new RuntimeException("File name argument is required");
		}
		taskWorker.parseOrderFiles(Arrays.asList(args));
	}
}
