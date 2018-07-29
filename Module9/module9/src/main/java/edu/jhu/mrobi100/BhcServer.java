/*
 *  Copyright (c) 2018.
 *  Max Robinson
 *  All Rights reserved.
 */

package edu.jhu.mrobi100;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/** The BHC Socket Server! */
public class BhcServer {
  private static final int PORT = 20009;
  private static Logger LOG = Logger.getLogger(BhcServer.class.getName());

  /** @param args the command line arguments */
  public static void main(String[] args) throws IOException {

    ServerSocket serverSocket = null;
    ExecutorService executorService = Executors.newCachedThreadPool();

    try {
      serverSocket = new ServerSocket(PORT);
    } catch (IOException e) {
      System.err.println(String.format("Could not listen on port: %d.", PORT));
      System.exit(1);
    }

    Socket clientSocket = null;
    while (true) {
      clientSocket = serverSocket.accept();
      LOG.info("Connection with client initiated");
      executorService.submit(new BhcResponseHandler(clientSocket));
    }
  }
}
