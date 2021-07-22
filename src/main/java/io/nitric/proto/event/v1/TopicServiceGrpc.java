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
public final class TopicServiceGrpc {

  private TopicServiceGrpc() {}

  public static final String SERVICE_NAME = "nitric.event.v1.TopicService";

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
    if ((getListMethod = TopicServiceGrpc.getListMethod) == null) {
      synchronized (TopicServiceGrpc.class) {
        if ((getListMethod = TopicServiceGrpc.getListMethod) == null) {
          TopicServiceGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.event.v1.TopicListRequest, io.nitric.proto.event.v1.TopicListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "List"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.event.v1.TopicListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.event.v1.TopicListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TopicServiceMethodDescriptorSupplier("List"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TopicServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TopicServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TopicServiceStub>() {
        @java.lang.Override
        public TopicServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TopicServiceStub(channel, callOptions);
        }
      };
    return TopicServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TopicServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TopicServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TopicServiceBlockingStub>() {
        @java.lang.Override
        public TopicServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TopicServiceBlockingStub(channel, callOptions);
        }
      };
    return TopicServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TopicServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TopicServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TopicServiceFutureStub>() {
        @java.lang.Override
        public TopicServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TopicServiceFutureStub(channel, callOptions);
        }
      };
    return TopicServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service for management of event topics
   * </pre>
   */
  public static abstract class TopicServiceImplBase implements io.grpc.BindableService {

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
  public static final class TopicServiceStub extends io.grpc.stub.AbstractAsyncStub<TopicServiceStub> {
    private TopicServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TopicServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TopicServiceStub(channel, callOptions);
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
  public static final class TopicServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<TopicServiceBlockingStub> {
    private TopicServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TopicServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TopicServiceBlockingStub(channel, callOptions);
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
  public static final class TopicServiceFutureStub extends io.grpc.stub.AbstractFutureStub<TopicServiceFutureStub> {
    private TopicServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TopicServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TopicServiceFutureStub(channel, callOptions);
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
    private final TopicServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TopicServiceImplBase serviceImpl, int methodId) {
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

  private static abstract class TopicServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TopicServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.event.v1.Events.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TopicService");
    }
  }

  private static final class TopicServiceFileDescriptorSupplier
      extends TopicServiceBaseDescriptorSupplier {
    TopicServiceFileDescriptorSupplier() {}
  }

  private static final class TopicServiceMethodDescriptorSupplier
      extends TopicServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TopicServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (TopicServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TopicServiceFileDescriptorSupplier())
              .addMethod(getListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
