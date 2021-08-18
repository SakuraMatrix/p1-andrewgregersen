package com.github.sakuramatrix.andrewgregersen.p1;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sakuramatrix.andrewgregersen.p1.application.AppConfig;
import com.github.sakuramatrix.andrewgregersen.p1.application.account.Account;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.DisposableServer;

import java.io.IOException;

public class App {

  private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("main");

  public static void main(String[] args) {
    log.info("Starting application");
    log.info("Creating beans!");
    AnnotationConfigApplicationContext applicationContext =
        new AnnotationConfigApplicationContext(AppConfig.class);
    log.info("Attempting to create a HTTP server");
    applicationContext.getBean(DisposableServer.class).onDispose().block();
    applicationContext.close();
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static ByteBuf toByteBuff(Object o) {
    try {
      return Unpooled.buffer()
          .writeBytes(OBJECT_MAPPER.writerFor(Account.class).writeValueAsBytes(o));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ByteBuf doubleToByteBuff(Object o) {
    try {
      return Unpooled.buffer()
          .writeBytes(OBJECT_MAPPER.writerFor(Double.class).writeValueAsBytes(o));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ByteBuf intToByteBuff(Object o) {
    try {
      return Unpooled.buffer()
          .writeBytes(OBJECT_MAPPER.writerFor(Integer.class).writeValueAsBytes(o));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Deserializes an Account upon a user generated JSON users POST request from the server
   *
   * @param str
   * @return
   */
  public static Account parseAccount(String str) {
    Account account = null;
    try {
      account = OBJECT_MAPPER.readValue(str, Account.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return account;
  }
}
