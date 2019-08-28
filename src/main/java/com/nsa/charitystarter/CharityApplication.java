package com.nsa.charitystarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CharityApplication {
  static final Logger LOG = LoggerFactory.getLogger(CharityApplication.class);

  public static void main(String[] args) {


    LOG.debug("Starting app");

    SpringApplication.run(CharityApplication.class, args);
  }

}
