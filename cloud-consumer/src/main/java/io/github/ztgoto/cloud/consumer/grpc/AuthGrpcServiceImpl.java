package io.github.ztgoto.cloud.consumer.grpc;

import io.github.ztgoto.grpc.messages.RequestMessage;
import io.github.ztgoto.grpc.messages.ResponseMessage;
import io.github.ztgoto.grpc.services.AuthRpcServiceGrpc.AuthRpcServiceImplBase;
import io.grpc.stub.StreamObserver;

public class AuthGrpcServiceImpl extends AuthRpcServiceImplBase  {

	@Override
	public void check(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {
		System.out.println(request);
		ResponseMessage.Builder builder = ResponseMessage.newBuilder();
		if (request.getToken()!=null) {
			builder.setAllow(true);
		} else {
			builder.setAllow(false);
		}
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
	
}
