package io.nitric.proto.storage.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Services for storage and retrieval of files in the form of byte arrays, such as text and binary files.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: storage/v1/storage.proto")
public final class StorageGrpc {

  private StorageGrpc() {}

  public static final String SERVICE_NAME = "nitric.storage.v1.Storage";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageReadRequest,
      io.nitric.proto.storage.v1.StorageReadResponse> getReadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Read",
      requestType = io.nitric.proto.storage.v1.StorageReadRequest.class,
      responseType = io.nitric.proto.storage.v1.StorageReadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageReadRequest,
      io.nitric.proto.storage.v1.StorageReadResponse> getReadMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageReadRequest, io.nitric.proto.storage.v1.StorageReadResponse> getReadMethod;
    if ((getReadMethod = StorageGrpc.getReadMethod) == null) {
      synchronized (StorageGrpc.class) {
        if ((getReadMethod = StorageGrpc.getReadMethod) == null) {
          StorageGrpc.getReadMethod = getReadMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.storage.v1.StorageReadRequest, io.nitric.proto.storage.v1.StorageReadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Read"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.storage.v1.StorageReadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.storage.v1.StorageReadResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StorageMethodDescriptorSupplier("Read"))
              .build();
        }
      }
    }
    return getReadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageWriteRequest,
      io.nitric.proto.storage.v1.StorageWriteResponse> getWriteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Write",
      requestType = io.nitric.proto.storage.v1.StorageWriteRequest.class,
      responseType = io.nitric.proto.storage.v1.StorageWriteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageWriteRequest,
      io.nitric.proto.storage.v1.StorageWriteResponse> getWriteMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageWriteRequest, io.nitric.proto.storage.v1.StorageWriteResponse> getWriteMethod;
    if ((getWriteMethod = StorageGrpc.getWriteMethod) == null) {
      synchronized (StorageGrpc.class) {
        if ((getWriteMethod = StorageGrpc.getWriteMethod) == null) {
          StorageGrpc.getWriteMethod = getWriteMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.storage.v1.StorageWriteRequest, io.nitric.proto.storage.v1.StorageWriteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Write"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.storage.v1.StorageWriteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.storage.v1.StorageWriteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StorageMethodDescriptorSupplier("Write"))
              .build();
        }
      }
    }
    return getWriteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageDeleteRequest,
      io.nitric.proto.storage.v1.StorageDeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = io.nitric.proto.storage.v1.StorageDeleteRequest.class,
      responseType = io.nitric.proto.storage.v1.StorageDeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageDeleteRequest,
      io.nitric.proto.storage.v1.StorageDeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.storage.v1.StorageDeleteRequest, io.nitric.proto.storage.v1.StorageDeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = StorageGrpc.getDeleteMethod) == null) {
      synchronized (StorageGrpc.class) {
        if ((getDeleteMethod = StorageGrpc.getDeleteMethod) == null) {
          StorageGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.storage.v1.StorageDeleteRequest, io.nitric.proto.storage.v1.StorageDeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.storage.v1.StorageDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.storage.v1.StorageDeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StorageMethodDescriptorSupplier("Delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StorageStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageStub>() {
        @java.lang.Override
        public StorageStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageStub(channel, callOptions);
        }
      };
    return StorageStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StorageBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageBlockingStub>() {
        @java.lang.Override
        public StorageBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageBlockingStub(channel, callOptions);
        }
      };
    return StorageBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StorageFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StorageFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StorageFutureStub>() {
        @java.lang.Override
        public StorageFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StorageFutureStub(channel, callOptions);
        }
      };
    return StorageFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Services for storage and retrieval of files in the form of byte arrays, such as text and binary files.
   * </pre>
   */
  public static abstract class StorageImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Retrieve an item from a bucket
     * </pre>
     */
    public void read(io.nitric.proto.storage.v1.StorageReadRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageReadResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadMethod(), responseObserver);
    }

    /**
     * <pre>
     * Store an item to a bucket
     * </pre>
     */
    public void write(io.nitric.proto.storage.v1.StorageWriteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageWriteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWriteMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete an item from a bucket
     * </pre>
     */
    public void delete(io.nitric.proto.storage.v1.StorageDeleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageDeleteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReadMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.storage.v1.StorageReadRequest,
                io.nitric.proto.storage.v1.StorageReadResponse>(
                  this, METHODID_READ)))
          .addMethod(
            getWriteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.storage.v1.StorageWriteRequest,
                io.nitric.proto.storage.v1.StorageWriteResponse>(
                  this, METHODID_WRITE)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.storage.v1.StorageDeleteRequest,
                io.nitric.proto.storage.v1.StorageDeleteResponse>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   * <pre>
   * Services for storage and retrieval of files in the form of byte arrays, such as text and binary files.
   * </pre>
   */
  public static final class StorageStub extends io.grpc.stub.AbstractAsyncStub<StorageStub> {
    private StorageStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve an item from a bucket
     * </pre>
     */
    public void read(io.nitric.proto.storage.v1.StorageReadRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageReadResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Store an item to a bucket
     * </pre>
     */
    public void write(io.nitric.proto.storage.v1.StorageWriteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageWriteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWriteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete an item from a bucket
     * </pre>
     */
    public void delete(io.nitric.proto.storage.v1.StorageDeleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageDeleteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Services for storage and retrieval of files in the form of byte arrays, such as text and binary files.
   * </pre>
   */
  public static class StorageBlockingStub extends io.grpc.stub.AbstractBlockingStub<StorageBlockingStub> {
    protected StorageBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve an item from a bucket
     * </pre>
     */
    public io.nitric.proto.storage.v1.StorageReadResponse read(io.nitric.proto.storage.v1.StorageReadRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Store an item to a bucket
     * </pre>
     */
    public io.nitric.proto.storage.v1.StorageWriteResponse write(io.nitric.proto.storage.v1.StorageWriteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWriteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete an item from a bucket
     * </pre>
     */
    public io.nitric.proto.storage.v1.StorageDeleteResponse delete(io.nitric.proto.storage.v1.StorageDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Services for storage and retrieval of files in the form of byte arrays, such as text and binary files.
   * </pre>
   */
  public static final class StorageFutureStub extends io.grpc.stub.AbstractFutureStub<StorageFutureStub> {
    private StorageFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StorageFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StorageFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Retrieve an item from a bucket
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.storage.v1.StorageReadResponse> read(
        io.nitric.proto.storage.v1.StorageReadRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Store an item to a bucket
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.storage.v1.StorageWriteResponse> write(
        io.nitric.proto.storage.v1.StorageWriteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWriteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete an item from a bucket
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.storage.v1.StorageDeleteResponse> delete(
        io.nitric.proto.storage.v1.StorageDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_READ = 0;
  private static final int METHODID_WRITE = 1;
  private static final int METHODID_DELETE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StorageImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StorageImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_READ:
          serviceImpl.read((io.nitric.proto.storage.v1.StorageReadRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageReadResponse>) responseObserver);
          break;
        case METHODID_WRITE:
          serviceImpl.write((io.nitric.proto.storage.v1.StorageWriteRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageWriteResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((io.nitric.proto.storage.v1.StorageDeleteRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.storage.v1.StorageDeleteResponse>) responseObserver);
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

  private static abstract class StorageBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StorageBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.storage.v1.Storages.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Storage");
    }
  }

  private static final class StorageFileDescriptorSupplier
      extends StorageBaseDescriptorSupplier {
    StorageFileDescriptorSupplier() {}
  }

  private static final class StorageMethodDescriptorSupplier
      extends StorageBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StorageMethodDescriptorSupplier(String methodName) {
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
      synchronized (StorageGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StorageFileDescriptorSupplier())
              .addMethod(getReadMethod())
              .addMethod(getWriteMethod())
              .addMethod(getDeleteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
