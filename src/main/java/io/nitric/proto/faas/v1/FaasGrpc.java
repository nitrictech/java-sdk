package io.nitric.proto.faas.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service for streaming communication with gRPC FaaS implementations
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: faas/v1/faas.proto")
public final class FaasGrpc {

  private FaasGrpc() {}

  public static final String SERVICE_NAME = "nitric.faas.v1.Faas";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.faas.v1.ClientMessage,
      io.nitric.proto.faas.v1.ServerMessage> getTriggerStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TriggerStream",
      requestType = io.nitric.proto.faas.v1.ClientMessage.class,
      responseType = io.nitric.proto.faas.v1.ServerMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<io.nitric.proto.faas.v1.ClientMessage,
      io.nitric.proto.faas.v1.ServerMessage> getTriggerStreamMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.faas.v1.ClientMessage, io.nitric.proto.faas.v1.ServerMessage> getTriggerStreamMethod;
    if ((getTriggerStreamMethod = FaasGrpc.getTriggerStreamMethod) == null) {
      synchronized (FaasGrpc.class) {
        if ((getTriggerStreamMethod = FaasGrpc.getTriggerStreamMethod) == null) {
          FaasGrpc.getTriggerStreamMethod = getTriggerStreamMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.faas.v1.ClientMessage, io.nitric.proto.faas.v1.ServerMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TriggerStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.faas.v1.ClientMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.faas.v1.ServerMessage.getDefaultInstance()))
              .setSchemaDescriptor(new FaasMethodDescriptorSupplier("TriggerStream"))
              .build();
        }
      }
    }
    return getTriggerStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FaasStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FaasStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FaasStub>() {
        @java.lang.Override
        public FaasStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FaasStub(channel, callOptions);
        }
      };
    return FaasStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FaasBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FaasBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FaasBlockingStub>() {
        @java.lang.Override
        public FaasBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FaasBlockingStub(channel, callOptions);
        }
      };
    return FaasBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FaasFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FaasFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FaasFutureStub>() {
        @java.lang.Override
        public FaasFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FaasFutureStub(channel, callOptions);
        }
      };
    return FaasFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service for streaming communication with gRPC FaaS implementations
   * </pre>
   */
  public static abstract class FaasImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Begin streaming triggers/response to/from the membrane
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.nitric.proto.faas.v1.ClientMessage> triggerStream(
        io.grpc.stub.StreamObserver<io.nitric.proto.faas.v1.ServerMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getTriggerStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getTriggerStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                io.nitric.proto.faas.v1.ClientMessage,
                io.nitric.proto.faas.v1.ServerMessage>(
                  this, METHODID_TRIGGER_STREAM)))
          .build();
    }
  }

  /**
   * <pre>
   * Service for streaming communication with gRPC FaaS implementations
   * </pre>
   */
  public static final class FaasStub extends io.grpc.stub.AbstractAsyncStub<FaasStub> {
    private FaasStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FaasStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FaasStub(channel, callOptions);
    }

    /**
     * <pre>
     * Begin streaming triggers/response to/from the membrane
     * </pre>
     */
    public io.grpc.stub.StreamObserver<io.nitric.proto.faas.v1.ClientMessage> triggerStream(
        io.grpc.stub.StreamObserver<io.nitric.proto.faas.v1.ServerMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getTriggerStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Service for streaming communication with gRPC FaaS implementations
   * </pre>
   */
  public static final class FaasBlockingStub extends io.grpc.stub.AbstractBlockingStub<FaasBlockingStub> {
    private FaasBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FaasBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FaasBlockingStub(channel, callOptions);
    }
  }

  /**
   * <pre>
   * Service for streaming communication with gRPC FaaS implementations
   * </pre>
   */
  public static final class FaasFutureStub extends io.grpc.stub.AbstractFutureStub<FaasFutureStub> {
    private FaasFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FaasFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FaasFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_TRIGGER_STREAM = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FaasImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FaasImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TRIGGER_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.triggerStream(
              (io.grpc.stub.StreamObserver<io.nitric.proto.faas.v1.ServerMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FaasBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FaasBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.faas.v1.NitricFaas.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Faas");
    }
  }

  private static final class FaasFileDescriptorSupplier
      extends FaasBaseDescriptorSupplier {
    FaasFileDescriptorSupplier() {}
  }

  private static final class FaasMethodDescriptorSupplier
      extends FaasBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FaasMethodDescriptorSupplier(String methodName) {
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
      synchronized (FaasGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FaasFileDescriptorSupplier())
              .addMethod(getTriggerStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
