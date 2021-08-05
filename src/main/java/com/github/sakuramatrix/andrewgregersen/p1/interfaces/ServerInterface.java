package com.github.sakuramatrix.andrewgregersen.p1.interfaces;

import com.github.sakuramatrix.andrewgregersen.p1.backend.Server;

public interface ServerInterface {
  static Server start() {
    return null;
  }

  static Server start(int port) {
    return null;
  }

  void connect();

  void respond();
}
