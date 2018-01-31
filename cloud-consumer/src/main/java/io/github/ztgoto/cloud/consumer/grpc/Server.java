package io.github.ztgoto.cloud.consumer.grpc;

import java.io.IOException;

import io.grpc.ServerBuilder;

public class Server {

	int port = 50051;

	io.grpc.Server server;

	public void start() throws IOException {
		AuthGrpcServiceImpl service = new AuthGrpcServiceImpl();
		server = ServerBuilder.forPort(port).addService(service).build().start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its
				// JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				Server.this.stop();
				System.err.println("*** server shut down");
			}
		});
		System.out.println("---server start :" + port + "--");
	}

	public void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	public void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	public static void main(String[] args) {

		try {
			Server s = new Server();
			s.start();
			s.blockUntilShutdown();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
