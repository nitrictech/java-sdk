package io.nitric.proto.event.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service for management of event topics
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: event/v1/event.proto")
public final class TopicGrpc {

  private TopicGrpc() {}

  public static final String SERVICE_NAME = "nitric.event.v1.Topic";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.event.v1.TopicListRequest,
      io.nitric.proto.event.v1.TopicListResponse> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "List",
      requestType = io.nitric.proto.event.v1.TopicListRequest.class,
      responseType = io.nitric.proto.event.v1.TopicListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.event.v1.TopicListRequest,
      io.nitric.proto.event.v1.TopicListResponse> getListMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.event.v1.TopicListRequest, io.nitric.proto.event.v1.TopicListResponse> getListMethod;
    if ((getListMethod = TopicGrpc.getListMethod) == null) {
      synchronized (TopicGrpc.class) {
        if ((getListMethod = TopicGrpc.getListMethod) == null) {
          TopicGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.event.v1.TopicListRequest, io.nitric.proto.event.v1.TopicListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "List"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.event.v1.TopicListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.event.v1.TopicListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TopicMethodDescriptorSupplier("List"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TopicStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TopicStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TopicStub>() {
        @java.lang.Override
        public TopicStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TopicStub(channel, callOptions);
        }
      };
    return TopicStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TopicBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TopicBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TopicBlockingStub>() {
        @java.lang.Override
        public TopicBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TopicBlockingStub(channel, callOptions);
        }
      };
    return TopicBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TopicFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TopicFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TopicFutureStub>() {
        @java.lang.Override
        public TopicFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TopicFutureStub(channel, callOptions);
        }
      };
    return TopicFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service for management of event topics
   * </pre>
   */
  public static abstract class TopicImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Return a list of existing topics in the provider environment
     * </pre>
     */
    public void list(io.nitric.proto.event.v1.TopicListRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.event.v1.TopicListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.event.v1.TopicListRequest,
                io.nitric.proto.event.v1.TopicListResponse>(
                  this, METHODID_LIST)))
          .build();
    }
  }

  /**
   * <pre>
   * Service for management of event topics
   * </pre>
   */
  public static final class TopicStub extends io.grpc.stub.AbstractAsyncStub<TopicStub> {
    private TopicStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TopicStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TopicStub(channel, callOptions);
    }

    /**
     * <pre>
     * Return a list of existing topics in the provider environment
     * </pre>
     */
    public void list(io.nitric.proto.event.v1.TopicListRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.event.v1.TopicListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Service for management of event topics
   * </pre>
   */
  public static class TopicBlockingStub extends io.grpc.stub.AbstractBlockingStub<TopicBlockingStub> {
    TopicBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TopicBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TopicBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Return a list of existing topics in the provider environment
     * </pre>
     */
    public io.nitric.proto.event.v1.TopicListResponse list(io.nitric.proto.event.v1.TopicListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Service for management of event topics
   * </pre>
   */
  public static final class TopicFutureStub extends io.grpc.stub.AbstractFutureStub<TopicFutureStub> {
    private TopicFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TopicFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TopicFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Return a list of existing topics in the provider environment
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.event.v1.TopicListResponse> list(
        io.nitric.proto.event.v1.TopicListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TopicImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TopicImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((io.nitric.proto.event.v1.TopicListRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.event.v1.TopicListResponse>) responseObserver);
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

  private static abstract class TopicBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TopicBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.event.v1.Events.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Topic");
    }
  }

  private static final class TopicFileDescriptorSupplier
      extends TopicBaseDescriptorSupplier {
    TopicFileDescriptorSupplier() {}
  }

  private static final class TopicMethodDescriptorSupplier
      extends TopicBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TopicMethodDescriptorSupplier(String methodName) {
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
      synchronized (TopicGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TopicFileDescriptorSupplier())
              .addMethod(getListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
