package io.github.ztgoto.cloud.consumer.grpc;

import java.util.concurrent.TimeUnit;

import io.github.ztgoto.grpc.messages.RequestMessage;
import io.github.ztgoto.grpc.messages.ResponseMessage;
import io.github.ztgoto.grpc.services.AuthRpcServiceGrpc;
import io.github.ztgoto.grpc.services.AuthRpcServiceGrpc.AuthRpcServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client {

	ManagedChannel channel;
	AuthRpcServiceBlockingStub stub;

	public Client(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
		stub = AuthRpcServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
	
	public boolean check(RequestMessage rquest){
		
		ResponseMessage result = stub.check(rquest);
		return result.getAllow();
		
	}
	
	public static void main(String[] args) {
		try {
			Client c = new Client("127.0.0.1", 50051);
			
			RequestMessage.Builder build = RequestMessage
					.newBuilder()
					.setUri("/oauth/login")
					.setContentType("application/json")
					.setMethod("PUT")
					.setToken("abc123456");
			
			boolean result = c.check(build.build());
			System.out.println(result);
			c.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
