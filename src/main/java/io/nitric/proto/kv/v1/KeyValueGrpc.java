package io.nitric.proto.kv.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Services for storage and retrieval of simple JSON keyValue
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: kv/v1/kv.proto")
public final class KeyValueGrpc {

  private KeyValueGrpc() {}

  public static final String SERVICE_NAME = "nitric.kv.v1.KeyValue";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValueGetRequest,
      io.nitric.proto.kv.v1.KeyValueGetResponse> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType = io.nitric.proto.kv.v1.KeyValueGetRequest.class,
      responseType = io.nitric.proto.kv.v1.KeyValueGetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValueGetRequest,
      io.nitric.proto.kv.v1.KeyValueGetResponse> getGetMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValueGetRequest, io.nitric.proto.kv.v1.KeyValueGetResponse> getGetMethod;
    if ((getGetMethod = KeyValueGrpc.getGetMethod) == null) {
      synchronized (KeyValueGrpc.class) {
        if ((getGetMethod = KeyValueGrpc.getGetMethod) == null) {
          KeyValueGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.kv.v1.KeyValueGetRequest, io.nitric.proto.kv.v1.KeyValueGetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.kv.v1.KeyValueGetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.kv.v1.KeyValueGetResponse.getDefaultInstance()))
              .setSchemaDescriptor(new KeyValueMethodDescriptorSupplier("Get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValuePutRequest,
      io.nitric.proto.kv.v1.KeyValuePutResponse> getPutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Put",
      requestType = io.nitric.proto.kv.v1.KeyValuePutRequest.class,
      responseType = io.nitric.proto.kv.v1.KeyValuePutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValuePutRequest,
      io.nitric.proto.kv.v1.KeyValuePutResponse> getPutMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValuePutRequest, io.nitric.proto.kv.v1.KeyValuePutResponse> getPutMethod;
    if ((getPutMethod = KeyValueGrpc.getPutMethod) == null) {
      synchronized (KeyValueGrpc.class) {
        if ((getPutMethod = KeyValueGrpc.getPutMethod) == null) {
          KeyValueGrpc.getPutMethod = getPutMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.kv.v1.KeyValuePutRequest, io.nitric.proto.kv.v1.KeyValuePutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Put"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.kv.v1.KeyValuePutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.kv.v1.KeyValuePutResponse.getDefaultInstance()))
              .setSchemaDescriptor(new KeyValueMethodDescriptorSupplier("Put"))
              .build();
        }
      }
    }
    return getPutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValueDeleteRequest,
      io.nitric.proto.kv.v1.KeyValueDeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = io.nitric.proto.kv.v1.KeyValueDeleteRequest.class,
      responseType = io.nitric.proto.kv.v1.KeyValueDeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValueDeleteRequest,
      io.nitric.proto.kv.v1.KeyValueDeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.kv.v1.KeyValueDeleteRequest, io.nitric.proto.kv.v1.KeyValueDeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = KeyValueGrpc.getDeleteMethod) == null) {
      synchronized (KeyValueGrpc.class) {
        if ((getDeleteMethod = KeyValueGrpc.getDeleteMethod) == null) {
          KeyValueGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.kv.v1.KeyValueDeleteRequest, io.nitric.proto.kv.v1.KeyValueDeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.kv.v1.KeyValueDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.kv.v1.KeyValueDeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new KeyValueMethodDescriptorSupplier("Delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static KeyValueStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<KeyValueStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<KeyValueStub>() {
        @java.lang.Override
        public KeyValueStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new KeyValueStub(channel, callOptions);
        }
      };
    return KeyValueStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static KeyValueBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<KeyValueBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<KeyValueBlockingStub>() {
        @java.lang.Override
        public KeyValueBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new KeyValueBlockingStub(channel, callOptions);
        }
      };
    return KeyValueBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static KeyValueFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<KeyValueFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<KeyValueFutureStub>() {
        @java.lang.Override
        public KeyValueFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new KeyValueFutureStub(channel, callOptions);
        }
      };
    return KeyValueFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Services for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static abstract class KeyValueImplBase implements io.grpc.BindableService {

    /**
     */
    public void get(io.nitric.proto.kv.v1.KeyValueGetRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValueGetResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     */
    public void put(io.nitric.proto.kv.v1.KeyValuePutRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValuePutResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutMethod(), responseObserver);
    }

    /**
     */
    public void delete(io.nitric.proto.kv.v1.KeyValueDeleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValueDeleteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.kv.v1.KeyValueGetRequest,
                io.nitric.proto.kv.v1.KeyValueGetResponse>(
                  this, METHODID_GET)))
          .addMethod(
            getPutMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.kv.v1.KeyValuePutRequest,
                io.nitric.proto.kv.v1.KeyValuePutResponse>(
                  this, METHODID_PUT)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.kv.v1.KeyValueDeleteRequest,
                io.nitric.proto.kv.v1.KeyValueDeleteResponse>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   * <pre>
   * Services for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static final class KeyValueStub extends io.grpc.stub.AbstractAsyncStub<KeyValueStub> {
    private KeyValueStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KeyValueStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new KeyValueStub(channel, callOptions);
    }

    /**
     */
    public void get(io.nitric.proto.kv.v1.KeyValueGetRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValueGetResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void put(io.nitric.proto.kv.v1.KeyValuePutRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValuePutResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(io.nitric.proto.kv.v1.KeyValueDeleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValueDeleteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Services for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static class KeyValueBlockingStub extends io.grpc.stub.AbstractBlockingStub<KeyValueBlockingStub> {
    protected KeyValueBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KeyValueBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new KeyValueBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.nitric.proto.kv.v1.KeyValueGetResponse get(io.nitric.proto.kv.v1.KeyValueGetRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.nitric.proto.kv.v1.KeyValuePutResponse put(io.nitric.proto.kv.v1.KeyValuePutRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.nitric.proto.kv.v1.KeyValueDeleteResponse delete(io.nitric.proto.kv.v1.KeyValueDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Services for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static final class KeyValueFutureStub extends io.grpc.stub.AbstractFutureStub<KeyValueFutureStub> {
    private KeyValueFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KeyValueFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new KeyValueFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.kv.v1.KeyValueGetResponse> get(
        io.nitric.proto.kv.v1.KeyValueGetRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.kv.v1.KeyValuePutResponse> put(
        io.nitric.proto.kv.v1.KeyValuePutRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.kv.v1.KeyValueDeleteResponse> delete(
        io.nitric.proto.kv.v1.KeyValueDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET = 0;
  private static final int METHODID_PUT = 1;
  private static final int METHODID_DELETE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final KeyValueImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(KeyValueImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET:
          serviceImpl.get((io.nitric.proto.kv.v1.KeyValueGetRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValueGetResponse>) responseObserver);
          break;
        case METHODID_PUT:
          serviceImpl.put((io.nitric.proto.kv.v1.KeyValuePutRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValuePutResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((io.nitric.proto.kv.v1.KeyValueDeleteRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.kv.v1.KeyValueDeleteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class KeyValueBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    KeyValueBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.kv.v1.KeyValues.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("KeyValue");
    }
  }

  private static final class KeyValueFileDescriptorSupplier
      extends KeyValueBaseDescriptorSupplier {
    KeyValueFileDescriptorSupplier() {}
  }

  private static final class KeyValueMethodDescriptorSupplier
      extends KeyValueBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    KeyValueMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (KeyValueGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new KeyValueFileDescriptorSupplier())
              .addMethod(getGetMethod())
              .addMethod(getPutMethod())
              .addMethod(getDeleteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
