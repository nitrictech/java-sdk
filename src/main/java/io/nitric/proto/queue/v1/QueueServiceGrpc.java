package io.nitric.proto.queue.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The Nitric Queue Service contract
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: queue/v1/queue.proto")
public final class QueueServiceGrpc {

  private QueueServiceGrpc() {}

  public static final String SERVICE_NAME = "nitric.queue.v1.QueueService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueSendRequest,
      io.nitric.proto.queue.v1.QueueSendResponse> getSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Send",
      requestType = io.nitric.proto.queue.v1.QueueSendRequest.class,
      responseType = io.nitric.proto.queue.v1.QueueSendResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueSendRequest,
      io.nitric.proto.queue.v1.QueueSendResponse> getSendMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueSendRequest, io.nitric.proto.queue.v1.QueueSendResponse> getSendMethod;
    if ((getSendMethod = QueueServiceGrpc.getSendMethod) == null) {
      synchronized (QueueServiceGrpc.class) {
        if ((getSendMethod = QueueServiceGrpc.getSendMethod) == null) {
          QueueServiceGrpc.getSendMethod = getSendMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.queue.v1.QueueSendRequest, io.nitric.proto.queue.v1.QueueSendResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Send"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueSendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueSendResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueueServiceMethodDescriptorSupplier("Send"))
              .build();
        }
      }
    }
    return getSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueSendBatchRequest,
      io.nitric.proto.queue.v1.QueueSendBatchResponse> getSendBatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendBatch",
      requestType = io.nitric.proto.queue.v1.QueueSendBatchRequest.class,
      responseType = io.nitric.proto.queue.v1.QueueSendBatchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueSendBatchRequest,
      io.nitric.proto.queue.v1.QueueSendBatchResponse> getSendBatchMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueSendBatchRequest, io.nitric.proto.queue.v1.QueueSendBatchResponse> getSendBatchMethod;
    if ((getSendBatchMethod = QueueServiceGrpc.getSendBatchMethod) == null) {
      synchronized (QueueServiceGrpc.class) {
        if ((getSendBatchMethod = QueueServiceGrpc.getSendBatchMethod) == null) {
          QueueServiceGrpc.getSendBatchMethod = getSendBatchMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.queue.v1.QueueSendBatchRequest, io.nitric.proto.queue.v1.QueueSendBatchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendBatch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueSendBatchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueSendBatchResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueueServiceMethodDescriptorSupplier("SendBatch"))
              .build();
        }
      }
    }
    return getSendBatchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueReceiveRequest,
      io.nitric.proto.queue.v1.QueueReceiveResponse> getReceiveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Receive",
      requestType = io.nitric.proto.queue.v1.QueueReceiveRequest.class,
      responseType = io.nitric.proto.queue.v1.QueueReceiveResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueReceiveRequest,
      io.nitric.proto.queue.v1.QueueReceiveResponse> getReceiveMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueReceiveRequest, io.nitric.proto.queue.v1.QueueReceiveResponse> getReceiveMethod;
    if ((getReceiveMethod = QueueServiceGrpc.getReceiveMethod) == null) {
      synchronized (QueueServiceGrpc.class) {
        if ((getReceiveMethod = QueueServiceGrpc.getReceiveMethod) == null) {
          QueueServiceGrpc.getReceiveMethod = getReceiveMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.queue.v1.QueueReceiveRequest, io.nitric.proto.queue.v1.QueueReceiveResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Receive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueReceiveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueReceiveResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueueServiceMethodDescriptorSupplier("Receive"))
              .build();
        }
      }
    }
    return getReceiveMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueCompleteRequest,
      io.nitric.proto.queue.v1.QueueCompleteResponse> getCompleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Complete",
      requestType = io.nitric.proto.queue.v1.QueueCompleteRequest.class,
      responseType = io.nitric.proto.queue.v1.QueueCompleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueCompleteRequest,
      io.nitric.proto.queue.v1.QueueCompleteResponse> getCompleteMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.queue.v1.QueueCompleteRequest, io.nitric.proto.queue.v1.QueueCompleteResponse> getCompleteMethod;
    if ((getCompleteMethod = QueueServiceGrpc.getCompleteMethod) == null) {
      synchronized (QueueServiceGrpc.class) {
        if ((getCompleteMethod = QueueServiceGrpc.getCompleteMethod) == null) {
          QueueServiceGrpc.getCompleteMethod = getCompleteMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.queue.v1.QueueCompleteRequest, io.nitric.proto.queue.v1.QueueCompleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Complete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueCompleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.queue.v1.QueueCompleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueueServiceMethodDescriptorSupplier("Complete"))
              .build();
        }
      }
    }
    return getCompleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QueueServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueueServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueueServiceStub>() {
        @java.lang.Override
        public QueueServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueueServiceStub(channel, callOptions);
        }
      };
    return QueueServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueueServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueueServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueueServiceBlockingStub>() {
        @java.lang.Override
        public QueueServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueueServiceBlockingStub(channel, callOptions);
        }
      };
    return QueueServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QueueServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueueServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueueServiceFutureStub>() {
        @java.lang.Override
        public QueueServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueueServiceFutureStub(channel, callOptions);
        }
      };
    return QueueServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The Nitric Queue Service contract
   * </pre>
   */
  public static abstract class QueueServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Send a single event to a queue
     * </pre>
     */
    public void send(io.nitric.proto.queue.v1.QueueSendRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueSendResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMethod(), responseObserver);
    }

    /**
     * <pre>
     * Send multiple events to a queue
     * </pre>
     */
    public void sendBatch(io.nitric.proto.queue.v1.QueueSendBatchRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueSendBatchResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendBatchMethod(), responseObserver);
    }

    /**
     * <pre>
     * Receive event(s) off a queue
     * </pre>
     */
    public void receive(io.nitric.proto.queue.v1.QueueReceiveRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueReceiveResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReceiveMethod(), responseObserver);
    }

    /**
     * <pre>
     * Complete an event previously popped from a queue
     * </pre>
     */
    public void complete(io.nitric.proto.queue.v1.QueueCompleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueCompleteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCompleteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.queue.v1.QueueSendRequest,
                io.nitric.proto.queue.v1.QueueSendResponse>(
                  this, METHODID_SEND)))
          .addMethod(
            getSendBatchMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.queue.v1.QueueSendBatchRequest,
                io.nitric.proto.queue.v1.QueueSendBatchResponse>(
                  this, METHODID_SEND_BATCH)))
          .addMethod(
            getReceiveMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.queue.v1.QueueReceiveRequest,
                io.nitric.proto.queue.v1.QueueReceiveResponse>(
                  this, METHODID_RECEIVE)))
          .addMethod(
            getCompleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.queue.v1.QueueCompleteRequest,
                io.nitric.proto.queue.v1.QueueCompleteResponse>(
                  this, METHODID_COMPLETE)))
          .build();
    }
  }

  /**
   * <pre>
   * The Nitric Queue Service contract
   * </pre>
   */
  public static final class QueueServiceStub extends io.grpc.stub.AbstractAsyncStub<QueueServiceStub> {
    private QueueServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueueServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueueServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send a single event to a queue
     * </pre>
     */
    public void send(io.nitric.proto.queue.v1.QueueSendRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueSendResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Send multiple events to a queue
     * </pre>
     */
    public void sendBatch(io.nitric.proto.queue.v1.QueueSendBatchRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueSendBatchResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendBatchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Receive event(s) off a queue
     * </pre>
     */
    public void receive(io.nitric.proto.queue.v1.QueueReceiveRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueReceiveResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReceiveMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Complete an event previously popped from a queue
     * </pre>
     */
    public void complete(io.nitric.proto.queue.v1.QueueCompleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueCompleteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCompleteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The Nitric Queue Service contract
   * </pre>
   */
  public static final class QueueServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<QueueServiceBlockingStub> {
    private QueueServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueueServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueueServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send a single event to a queue
     * </pre>
     */
    public io.nitric.proto.queue.v1.QueueSendResponse send(io.nitric.proto.queue.v1.QueueSendRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Send multiple events to a queue
     * </pre>
     */
    public io.nitric.proto.queue.v1.QueueSendBatchResponse sendBatch(io.nitric.proto.queue.v1.QueueSendBatchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendBatchMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Receive event(s) off a queue
     * </pre>
     */
    public io.nitric.proto.queue.v1.QueueReceiveResponse receive(io.nitric.proto.queue.v1.QueueReceiveRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReceiveMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Complete an event previously popped from a queue
     * </pre>
     */
    public io.nitric.proto.queue.v1.QueueCompleteResponse complete(io.nitric.proto.queue.v1.QueueCompleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCompleteMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The Nitric Queue Service contract
   * </pre>
   */
  public static final class QueueServiceFutureStub extends io.grpc.stub.AbstractFutureStub<QueueServiceFutureStub> {
    private QueueServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueueServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueueServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send a single event to a queue
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.queue.v1.QueueSendResponse> send(
        io.nitric.proto.queue.v1.QueueSendRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Send multiple events to a queue
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.queue.v1.QueueSendBatchResponse> sendBatch(
        io.nitric.proto.queue.v1.QueueSendBatchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendBatchMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Receive event(s) off a queue
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.queue.v1.QueueReceiveResponse> receive(
        io.nitric.proto.queue.v1.QueueReceiveRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReceiveMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Complete an event previously popped from a queue
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.queue.v1.QueueCompleteResponse> complete(
        io.nitric.proto.queue.v1.QueueCompleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCompleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND = 0;
  private static final int METHODID_SEND_BATCH = 1;
  private static final int METHODID_RECEIVE = 2;
  private static final int METHODID_COMPLETE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QueueServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QueueServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND:
          serviceImpl.send((io.nitric.proto.queue.v1.QueueSendRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueSendResponse>) responseObserver);
          break;
        case METHODID_SEND_BATCH:
          serviceImpl.sendBatch((io.nitric.proto.queue.v1.QueueSendBatchRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueSendBatchResponse>) responseObserver);
          break;
        case METHODID_RECEIVE:
          serviceImpl.receive((io.nitric.proto.queue.v1.QueueReceiveRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueReceiveResponse>) responseObserver);
          break;
        case METHODID_COMPLETE:
          serviceImpl.complete((io.nitric.proto.queue.v1.QueueCompleteRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.queue.v1.QueueCompleteResponse>) responseObserver);
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

  private static abstract class QueueServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QueueServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.queue.v1.Queues.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("QueueService");
    }
  }

  private static final class QueueServiceFileDescriptorSupplier
      extends QueueServiceBaseDescriptorSupplier {
    QueueServiceFileDescriptorSupplier() {}
  }

  private static final class QueueServiceMethodDescriptorSupplier
      extends QueueServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QueueServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (QueueServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QueueServiceFileDescriptorSupplier())
              .addMethod(getSendMethod())
              .addMethod(getSendBatchMethod())
              .addMethod(getReceiveMethod())
              .addMethod(getCompleteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
