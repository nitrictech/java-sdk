package io.nitric.proto.event.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service for publishing asynchronous event
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: event/v1/event.proto")
public final class EventGrpc {

  private EventGrpc() {}

  public static final String SERVICE_NAME = "nitric.event.v1.Event";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.event.v1.EventPublishRequest,
      io.nitric.proto.event.v1.EventPublishResponse> getPublishMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Publish",
      requestType = io.nitric.proto.event.v1.EventPublishRequest.class,
      responseType = io.nitric.proto.event.v1.EventPublishResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.event.v1.EventPublishRequest,
      io.nitric.proto.event.v1.EventPublishResponse> getPublishMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.event.v1.EventPublishRequest, io.nitric.proto.event.v1.EventPublishResponse> getPublishMethod;
    if ((getPublishMethod = EventGrpc.getPublishMethod) == null) {
      synchronized (EventGrpc.class) {
        if ((getPublishMethod = EventGrpc.getPublishMethod) == null) {
          EventGrpc.getPublishMethod = getPublishMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.event.v1.EventPublishRequest, io.nitric.proto.event.v1.EventPublishResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Publish"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.event.v1.EventPublishRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.event.v1.EventPublishResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EventMethodDescriptorSupplier("Publish"))
              .build();
        }
      }
    }
    return getPublishMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EventStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventStub>() {
        @java.lang.Override
        public EventStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventStub(channel, callOptions);
        }
      };
    return EventStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EventBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventBlockingStub>() {
        @java.lang.Override
        public EventBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventBlockingStub(channel, callOptions);
        }
      };
    return EventBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EventFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventFutureStub>() {
        @java.lang.Override
        public EventFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventFutureStub(channel, callOptions);
        }
      };
    return EventFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service for publishing asynchronous event
   * </pre>
   */
  public static abstract class EventImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Publish a message to a given topic
     * </pre>
     */
    public void publish(io.nitric.proto.event.v1.EventPublishRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.event.v1.EventPublishResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPublishMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.event.v1.EventPublishRequest,
                io.nitric.proto.event.v1.EventPublishResponse>(
                  this, METHODID_PUBLISH)))
          .build();
    }
  }

  /**
   * <pre>
   * Service for publishing asynchronous event
   * </pre>
   */
  public static final class EventStub extends io.grpc.stub.AbstractAsyncStub<EventStub> {
    private EventStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventStub(channel, callOptions);
    }

    /**
     * <pre>
     * Publish a message to a given topic
     * </pre>
     */
    public void publish(io.nitric.proto.event.v1.EventPublishRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.event.v1.EventPublishResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Service for publishing asynchronous event
   * </pre>
   */
  public static final class EventBlockingStub extends io.grpc.stub.AbstractBlockingStub<EventBlockingStub> {
    private EventBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Publish a message to a given topic
     * </pre>
     */
    public io.nitric.proto.event.v1.EventPublishResponse publish(io.nitric.proto.event.v1.EventPublishRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Service for publishing asynchronous event
   * </pre>
   */
  public static final class EventFutureStub extends io.grpc.stub.AbstractFutureStub<EventFutureStub> {
    private EventFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Publish a message to a given topic
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.event.v1.EventPublishResponse> publish(
        io.nitric.proto.event.v1.EventPublishRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUBLISH = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EventImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EventImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUBLISH:
          serviceImpl.publish((io.nitric.proto.event.v1.EventPublishRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.event.v1.EventPublishResponse>) responseObserver);
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

  private static abstract class EventBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EventBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.event.v1.Events.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Event");
    }
  }

  private static final class EventFileDescriptorSupplier
      extends EventBaseDescriptorSupplier {
    EventFileDescriptorSupplier() {}
  }

  private static final class EventMethodDescriptorSupplier
      extends EventBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EventMethodDescriptorSupplier(String methodName) {
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
      synchronized (EventGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EventFileDescriptorSupplier())
              .addMethod(getPublishMethod())
              .build();
        }
      }
    }
    return result;
  }
}
