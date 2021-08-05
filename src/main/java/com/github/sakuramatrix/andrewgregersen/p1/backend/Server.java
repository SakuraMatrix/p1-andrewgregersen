package com.github.sakuramatrix.andrewgregersen.p1.backend;

import com.github.sakuramatrix.andrewgregersen.p1.interfaces.ServerInterface;

public class Server implements ServerInterface {

  public static Server start() {
    return new Server();
  }

  public static Server start(int port) {
    return new Server(port);
  }

  public Server(int port) {}

  public Server() {}

  @Override
  public void connect() {}

  @Override
  public void respond() {}
}
/* A server class that uses Reactor-Netty */
