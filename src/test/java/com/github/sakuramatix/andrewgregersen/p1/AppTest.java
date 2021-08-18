package com.github.sakuramatix.andrewgregersen.p1;

import com.github.sakuramatrix.andrewgregersen.p1.App;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.netty.http.client.HttpClient;

@SpringJUnitConfig(classes = App.class)
public class AppTest {

  HttpClient rest;
}
