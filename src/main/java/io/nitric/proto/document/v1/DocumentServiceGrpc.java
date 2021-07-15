package io.nitric.proto.document.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service for storage and retrieval of simple JSON keyValue
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: document/v1/document.proto")
public final class DocumentServiceGrpc {

  private DocumentServiceGrpc() {}

  public static final String SERVICE_NAME = "nitric.document.v1.DocumentService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentGetRequest,
      io.nitric.proto.document.v1.DocumentGetResponse> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType = io.nitric.proto.document.v1.DocumentGetRequest.class,
      responseType = io.nitric.proto.document.v1.DocumentGetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentGetRequest,
      io.nitric.proto.document.v1.DocumentGetResponse> getGetMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentGetRequest, io.nitric.proto.document.v1.DocumentGetResponse> getGetMethod;
    if ((getGetMethod = DocumentServiceGrpc.getGetMethod) == null) {
      synchronized (DocumentServiceGrpc.class) {
        if ((getGetMethod = DocumentServiceGrpc.getGetMethod) == null) {
          DocumentServiceGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.document.v1.DocumentGetRequest, io.nitric.proto.document.v1.DocumentGetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentGetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentGetResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DocumentServiceMethodDescriptorSupplier("Get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentSetRequest,
      io.nitric.proto.document.v1.DocumentSetResponse> getSetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Set",
      requestType = io.nitric.proto.document.v1.DocumentSetRequest.class,
      responseType = io.nitric.proto.document.v1.DocumentSetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentSetRequest,
      io.nitric.proto.document.v1.DocumentSetResponse> getSetMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentSetRequest, io.nitric.proto.document.v1.DocumentSetResponse> getSetMethod;
    if ((getSetMethod = DocumentServiceGrpc.getSetMethod) == null) {
      synchronized (DocumentServiceGrpc.class) {
        if ((getSetMethod = DocumentServiceGrpc.getSetMethod) == null) {
          DocumentServiceGrpc.getSetMethod = getSetMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.document.v1.DocumentSetRequest, io.nitric.proto.document.v1.DocumentSetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Set"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentSetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentSetResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DocumentServiceMethodDescriptorSupplier("Set"))
              .build();
        }
      }
    }
    return getSetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentDeleteRequest,
      io.nitric.proto.document.v1.DocumentDeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = io.nitric.proto.document.v1.DocumentDeleteRequest.class,
      responseType = io.nitric.proto.document.v1.DocumentDeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentDeleteRequest,
      io.nitric.proto.document.v1.DocumentDeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentDeleteRequest, io.nitric.proto.document.v1.DocumentDeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = DocumentServiceGrpc.getDeleteMethod) == null) {
      synchronized (DocumentServiceGrpc.class) {
        if ((getDeleteMethod = DocumentServiceGrpc.getDeleteMethod) == null) {
          DocumentServiceGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.document.v1.DocumentDeleteRequest, io.nitric.proto.document.v1.DocumentDeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentDeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DocumentServiceMethodDescriptorSupplier("Delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentQueryRequest,
      io.nitric.proto.document.v1.DocumentQueryResponse> getQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Query",
      requestType = io.nitric.proto.document.v1.DocumentQueryRequest.class,
      responseType = io.nitric.proto.document.v1.DocumentQueryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentQueryRequest,
      io.nitric.proto.document.v1.DocumentQueryResponse> getQueryMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentQueryRequest, io.nitric.proto.document.v1.DocumentQueryResponse> getQueryMethod;
    if ((getQueryMethod = DocumentServiceGrpc.getQueryMethod) == null) {
      synchronized (DocumentServiceGrpc.class) {
        if ((getQueryMethod = DocumentServiceGrpc.getQueryMethod) == null) {
          DocumentServiceGrpc.getQueryMethod = getQueryMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.document.v1.DocumentQueryRequest, io.nitric.proto.document.v1.DocumentQueryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Query"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentQueryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentQueryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DocumentServiceMethodDescriptorSupplier("Query"))
              .build();
        }
      }
    }
    return getQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentQueryStreamRequest,
      io.nitric.proto.document.v1.DocumentQueryStreamResponse> getQueryStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryStream",
      requestType = io.nitric.proto.document.v1.DocumentQueryStreamRequest.class,
      responseType = io.nitric.proto.document.v1.DocumentQueryStreamResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentQueryStreamRequest,
      io.nitric.proto.document.v1.DocumentQueryStreamResponse> getQueryStreamMethod() {
    io.grpc.MethodDescriptor<io.nitric.proto.document.v1.DocumentQueryStreamRequest, io.nitric.proto.document.v1.DocumentQueryStreamResponse> getQueryStreamMethod;
    if ((getQueryStreamMethod = DocumentServiceGrpc.getQueryStreamMethod) == null) {
      synchronized (DocumentServiceGrpc.class) {
        if ((getQueryStreamMethod = DocumentServiceGrpc.getQueryStreamMethod) == null) {
          DocumentServiceGrpc.getQueryStreamMethod = getQueryStreamMethod =
              io.grpc.MethodDescriptor.<io.nitric.proto.document.v1.DocumentQueryStreamRequest, io.nitric.proto.document.v1.DocumentQueryStreamResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentQueryStreamRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.nitric.proto.document.v1.DocumentQueryStreamResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DocumentServiceMethodDescriptorSupplier("QueryStream"))
              .build();
        }
      }
    }
    return getQueryStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DocumentServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DocumentServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DocumentServiceStub>() {
        @java.lang.Override
        public DocumentServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DocumentServiceStub(channel, callOptions);
        }
      };
    return DocumentServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DocumentServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DocumentServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DocumentServiceBlockingStub>() {
        @java.lang.Override
        public DocumentServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DocumentServiceBlockingStub(channel, callOptions);
        }
      };
    return DocumentServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DocumentServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DocumentServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DocumentServiceFutureStub>() {
        @java.lang.Override
        public DocumentServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DocumentServiceFutureStub(channel, callOptions);
        }
      };
    return DocumentServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static abstract class DocumentServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Get an existing document
     * </pre>
     */
    public void get(io.nitric.proto.document.v1.DocumentGetRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentGetResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create a new or overwrite an existing document
     * </pre>
     */
    public void set(io.nitric.proto.document.v1.DocumentSetRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentSetResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete an existing document
     * </pre>
     */
    public void delete(io.nitric.proto.document.v1.DocumentDeleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentDeleteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query the document collection (supports pagination)
     * </pre>
     */
    public void query(io.nitric.proto.document.v1.DocumentQueryRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentQueryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Query the document collection (supports streaming)
     * </pre>
     */
    public void queryStream(io.nitric.proto.document.v1.DocumentQueryStreamRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentQueryStreamResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.document.v1.DocumentGetRequest,
                io.nitric.proto.document.v1.DocumentGetResponse>(
                  this, METHODID_GET)))
          .addMethod(
            getSetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.document.v1.DocumentSetRequest,
                io.nitric.proto.document.v1.DocumentSetResponse>(
                  this, METHODID_SET)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.document.v1.DocumentDeleteRequest,
                io.nitric.proto.document.v1.DocumentDeleteResponse>(
                  this, METHODID_DELETE)))
          .addMethod(
            getQueryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.nitric.proto.document.v1.DocumentQueryRequest,
                io.nitric.proto.document.v1.DocumentQueryResponse>(
                  this, METHODID_QUERY)))
          .addMethod(
            getQueryStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.nitric.proto.document.v1.DocumentQueryStreamRequest,
                io.nitric.proto.document.v1.DocumentQueryStreamResponse>(
                  this, METHODID_QUERY_STREAM)))
          .build();
    }
  }

  /**
   * <pre>
   * Service for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static final class DocumentServiceStub extends io.grpc.stub.AbstractAsyncStub<DocumentServiceStub> {
    private DocumentServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DocumentServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DocumentServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get an existing document
     * </pre>
     */
    public void get(io.nitric.proto.document.v1.DocumentGetRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentGetResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create a new or overwrite an existing document
     * </pre>
     */
    public void set(io.nitric.proto.document.v1.DocumentSetRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentSetResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete an existing document
     * </pre>
     */
    public void delete(io.nitric.proto.document.v1.DocumentDeleteRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentDeleteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query the document collection (supports pagination)
     * </pre>
     */
    public void query(io.nitric.proto.document.v1.DocumentQueryRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentQueryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query the document collection (supports streaming)
     * </pre>
     */
    public void queryStream(io.nitric.proto.document.v1.DocumentQueryStreamRequest request,
        io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentQueryStreamResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getQueryStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Service for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static final class DocumentServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<DocumentServiceBlockingStub> {
    private DocumentServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DocumentServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DocumentServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get an existing document
     * </pre>
     */
    public io.nitric.proto.document.v1.DocumentGetResponse get(io.nitric.proto.document.v1.DocumentGetRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create a new or overwrite an existing document
     * </pre>
     */
    public io.nitric.proto.document.v1.DocumentSetResponse set(io.nitric.proto.document.v1.DocumentSetRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete an existing document
     * </pre>
     */
    public io.nitric.proto.document.v1.DocumentDeleteResponse delete(io.nitric.proto.document.v1.DocumentDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query the document collection (supports pagination)
     * </pre>
     */
    public io.nitric.proto.document.v1.DocumentQueryResponse query(io.nitric.proto.document.v1.DocumentQueryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Query the document collection (supports streaming)
     * </pre>
     */
    public java.util.Iterator<io.nitric.proto.document.v1.DocumentQueryStreamResponse> queryStream(
        io.nitric.proto.document.v1.DocumentQueryStreamRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getQueryStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Service for storage and retrieval of simple JSON keyValue
   * </pre>
   */
  public static final class DocumentServiceFutureStub extends io.grpc.stub.AbstractFutureStub<DocumentServiceFutureStub> {
    private DocumentServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DocumentServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DocumentServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get an existing document
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.document.v1.DocumentGetResponse> get(
        io.nitric.proto.document.v1.DocumentGetRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create a new or overwrite an existing document
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.document.v1.DocumentSetResponse> set(
        io.nitric.proto.document.v1.DocumentSetRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete an existing document
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.document.v1.DocumentDeleteResponse> delete(
        io.nitric.proto.document.v1.DocumentDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Query the document collection (supports pagination)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.nitric.proto.document.v1.DocumentQueryResponse> query(
        io.nitric.proto.document.v1.DocumentQueryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET = 0;
  private static final int METHODID_SET = 1;
  private static final int METHODID_DELETE = 2;
  private static final int METHODID_QUERY = 3;
  private static final int METHODID_QUERY_STREAM = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DocumentServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DocumentServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET:
          serviceImpl.get((io.nitric.proto.document.v1.DocumentGetRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentGetResponse>) responseObserver);
          break;
        case METHODID_SET:
          serviceImpl.set((io.nitric.proto.document.v1.DocumentSetRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentSetResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((io.nitric.proto.document.v1.DocumentDeleteRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentDeleteResponse>) responseObserver);
          break;
        case METHODID_QUERY:
          serviceImpl.query((io.nitric.proto.document.v1.DocumentQueryRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentQueryResponse>) responseObserver);
          break;
        case METHODID_QUERY_STREAM:
          serviceImpl.queryStream((io.nitric.proto.document.v1.DocumentQueryStreamRequest) request,
              (io.grpc.stub.StreamObserver<io.nitric.proto.document.v1.DocumentQueryStreamResponse>) responseObserver);
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

  private static abstract class DocumentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DocumentServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.nitric.proto.document.v1.Documents.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DocumentService");
    }
  }

  private static final class DocumentServiceFileDescriptorSupplier
      extends DocumentServiceBaseDescriptorSupplier {
    DocumentServiceFileDescriptorSupplier() {}
  }

  private static final class DocumentServiceMethodDescriptorSupplier
      extends DocumentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DocumentServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DocumentServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DocumentServiceFileDescriptorSupplier())
              .addMethod(getGetMethod())
              .addMethod(getSetMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getQueryMethod())
              .addMethod(getQueryStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
