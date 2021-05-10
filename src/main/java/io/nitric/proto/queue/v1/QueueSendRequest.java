// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: queue/v1/queue.proto

package io.nitric.proto.queue.v1;

/**
 * <pre>
 * Request to push a single event to a queue
 * </pre>
 *
 * Protobuf type {@code nitric.queue.v1.QueueSendRequest}
 */
public final class QueueSendRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:nitric.queue.v1.QueueSendRequest)
    QueueSendRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use QueueSendRequest.newBuilder() to construct.
  private QueueSendRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private QueueSendRequest() {
    queue_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new QueueSendRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private QueueSendRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            queue_ = s;
            break;
          }
          case 18: {
            io.nitric.proto.queue.v1.NitricTask.Builder subBuilder = null;
            if (task_ != null) {
              subBuilder = task_.toBuilder();
            }
            task_ = input.readMessage(io.nitric.proto.queue.v1.NitricTask.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(task_);
              task_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueSendRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueSendRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.nitric.proto.queue.v1.QueueSendRequest.class, io.nitric.proto.queue.v1.QueueSendRequest.Builder.class);
  }

  public static final int QUEUE_FIELD_NUMBER = 1;
  private volatile java.lang.Object queue_;
  /**
   * <pre>
   * The Nitric name for the queue
   * this will automatically be resolved to the provider specific queue identifier.
   * </pre>
   *
   * <code>string queue = 1;</code>
   * @return The queue.
   */
  @java.lang.Override
  public java.lang.String getQueue() {
    java.lang.Object ref = queue_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      queue_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * The Nitric name for the queue
   * this will automatically be resolved to the provider specific queue identifier.
   * </pre>
   *
   * <code>string queue = 1;</code>
   * @return The bytes for queue.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getQueueBytes() {
    java.lang.Object ref = queue_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      queue_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TASK_FIELD_NUMBER = 2;
  private io.nitric.proto.queue.v1.NitricTask task_;
  /**
   * <pre>
   * The task to push to the queue
   * </pre>
   *
   * <code>.nitric.queue.v1.NitricTask task = 2;</code>
   * @return Whether the task field is set.
   */
  @java.lang.Override
  public boolean hasTask() {
    return task_ != null;
  }
  /**
   * <pre>
   * The task to push to the queue
   * </pre>
   *
   * <code>.nitric.queue.v1.NitricTask task = 2;</code>
   * @return The task.
   */
  @java.lang.Override
  public io.nitric.proto.queue.v1.NitricTask getTask() {
    return task_ == null ? io.nitric.proto.queue.v1.NitricTask.getDefaultInstance() : task_;
  }
  /**
   * <pre>
   * The task to push to the queue
   * </pre>
   *
   * <code>.nitric.queue.v1.NitricTask task = 2;</code>
   */
  @java.lang.Override
  public io.nitric.proto.queue.v1.NitricTaskOrBuilder getTaskOrBuilder() {
    return getTask();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getQueueBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, queue_);
    }
    if (task_ != null) {
      output.writeMessage(2, getTask());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getQueueBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, queue_);
    }
    if (task_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getTask());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.nitric.proto.queue.v1.QueueSendRequest)) {
      return super.equals(obj);
    }
    io.nitric.proto.queue.v1.QueueSendRequest other = (io.nitric.proto.queue.v1.QueueSendRequest) obj;

    if (!getQueue()
        .equals(other.getQueue())) return false;
    if (hasTask() != other.hasTask()) return false;
    if (hasTask()) {
      if (!getTask()
          .equals(other.getTask())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + QUEUE_FIELD_NUMBER;
    hash = (53 * hash) + getQueue().hashCode();
    if (hasTask()) {
      hash = (37 * hash) + TASK_FIELD_NUMBER;
      hash = (53 * hash) + getTask().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.queue.v1.QueueSendRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.nitric.proto.queue.v1.QueueSendRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Request to push a single event to a queue
   * </pre>
   *
   * Protobuf type {@code nitric.queue.v1.QueueSendRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:nitric.queue.v1.QueueSendRequest)
      io.nitric.proto.queue.v1.QueueSendRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueSendRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueSendRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.nitric.proto.queue.v1.QueueSendRequest.class, io.nitric.proto.queue.v1.QueueSendRequest.Builder.class);
    }

    // Construct using io.nitric.proto.queue.v1.QueueSendRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      queue_ = "";

      if (taskBuilder_ == null) {
        task_ = null;
      } else {
        task_ = null;
        taskBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueSendRequest_descriptor;
    }

    @java.lang.Override
    public io.nitric.proto.queue.v1.QueueSendRequest getDefaultInstanceForType() {
      return io.nitric.proto.queue.v1.QueueSendRequest.getDefaultInstance();
    }

    @java.lang.Override
    public io.nitric.proto.queue.v1.QueueSendRequest build() {
      io.nitric.proto.queue.v1.QueueSendRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.nitric.proto.queue.v1.QueueSendRequest buildPartial() {
      io.nitric.proto.queue.v1.QueueSendRequest result = new io.nitric.proto.queue.v1.QueueSendRequest(this);
      result.queue_ = queue_;
      if (taskBuilder_ == null) {
        result.task_ = task_;
      } else {
        result.task_ = taskBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.nitric.proto.queue.v1.QueueSendRequest) {
        return mergeFrom((io.nitric.proto.queue.v1.QueueSendRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.nitric.proto.queue.v1.QueueSendRequest other) {
      if (other == io.nitric.proto.queue.v1.QueueSendRequest.getDefaultInstance()) return this;
      if (!other.getQueue().isEmpty()) {
        queue_ = other.queue_;
        onChanged();
      }
      if (other.hasTask()) {
        mergeTask(other.getTask());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      io.nitric.proto.queue.v1.QueueSendRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.nitric.proto.queue.v1.QueueSendRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object queue_ = "";
    /**
     * <pre>
     * The Nitric name for the queue
     * this will automatically be resolved to the provider specific queue identifier.
     * </pre>
     *
     * <code>string queue = 1;</code>
     * @return The queue.
     */
    public java.lang.String getQueue() {
      java.lang.Object ref = queue_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        queue_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * The Nitric name for the queue
     * this will automatically be resolved to the provider specific queue identifier.
     * </pre>
     *
     * <code>string queue = 1;</code>
     * @return The bytes for queue.
     */
    public com.google.protobuf.ByteString
        getQueueBytes() {
      java.lang.Object ref = queue_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        queue_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * The Nitric name for the queue
     * this will automatically be resolved to the provider specific queue identifier.
     * </pre>
     *
     * <code>string queue = 1;</code>
     * @param value The queue to set.
     * @return This builder for chaining.
     */
    public Builder setQueue(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      queue_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The Nitric name for the queue
     * this will automatically be resolved to the provider specific queue identifier.
     * </pre>
     *
     * <code>string queue = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearQueue() {

      queue_ = getDefaultInstance().getQueue();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The Nitric name for the queue
     * this will automatically be resolved to the provider specific queue identifier.
     * </pre>
     *
     * <code>string queue = 1;</code>
     * @param value The bytes for queue to set.
     * @return This builder for chaining.
     */
    public Builder setQueueBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      queue_ = value;
      onChanged();
      return this;
    }

    private io.nitric.proto.queue.v1.NitricTask task_;
    private com.google.protobuf.SingleFieldBuilderV3<
        io.nitric.proto.queue.v1.NitricTask, io.nitric.proto.queue.v1.NitricTask.Builder, io.nitric.proto.queue.v1.NitricTaskOrBuilder> taskBuilder_;
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     * @return Whether the task field is set.
     */
    public boolean hasTask() {
      return taskBuilder_ != null || task_ != null;
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     * @return The task.
     */
    public io.nitric.proto.queue.v1.NitricTask getTask() {
      if (taskBuilder_ == null) {
        return task_ == null ? io.nitric.proto.queue.v1.NitricTask.getDefaultInstance() : task_;
      } else {
        return taskBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     */
    public Builder setTask(io.nitric.proto.queue.v1.NitricTask value) {
      if (taskBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        task_ = value;
        onChanged();
      } else {
        taskBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     */
    public Builder setTask(
        io.nitric.proto.queue.v1.NitricTask.Builder builderForValue) {
      if (taskBuilder_ == null) {
        task_ = builderForValue.build();
        onChanged();
      } else {
        taskBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     */
    public Builder mergeTask(io.nitric.proto.queue.v1.NitricTask value) {
      if (taskBuilder_ == null) {
        if (task_ != null) {
          task_ =
            io.nitric.proto.queue.v1.NitricTask.newBuilder(task_).mergeFrom(value).buildPartial();
        } else {
          task_ = value;
        }
        onChanged();
      } else {
        taskBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     */
    public Builder clearTask() {
      if (taskBuilder_ == null) {
        task_ = null;
        onChanged();
      } else {
        task_ = null;
        taskBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     */
    public io.nitric.proto.queue.v1.NitricTask.Builder getTaskBuilder() {

      onChanged();
      return getTaskFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     */
    public io.nitric.proto.queue.v1.NitricTaskOrBuilder getTaskOrBuilder() {
      if (taskBuilder_ != null) {
        return taskBuilder_.getMessageOrBuilder();
      } else {
        return task_ == null ?
            io.nitric.proto.queue.v1.NitricTask.getDefaultInstance() : task_;
      }
    }
    /**
     * <pre>
     * The task to push to the queue
     * </pre>
     *
     * <code>.nitric.queue.v1.NitricTask task = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        io.nitric.proto.queue.v1.NitricTask, io.nitric.proto.queue.v1.NitricTask.Builder, io.nitric.proto.queue.v1.NitricTaskOrBuilder>
        getTaskFieldBuilder() {
      if (taskBuilder_ == null) {
        taskBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            io.nitric.proto.queue.v1.NitricTask, io.nitric.proto.queue.v1.NitricTask.Builder, io.nitric.proto.queue.v1.NitricTaskOrBuilder>(
                getTask(),
                getParentForChildren(),
                isClean());
        task_ = null;
      }
      return taskBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:nitric.queue.v1.QueueSendRequest)
  }

  // @@protoc_insertion_point(class_scope:nitric.queue.v1.QueueSendRequest)
  private static final io.nitric.proto.queue.v1.QueueSendRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.nitric.proto.queue.v1.QueueSendRequest();
  }

  public static io.nitric.proto.queue.v1.QueueSendRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<QueueSendRequest>
      PARSER = new com.google.protobuf.AbstractParser<QueueSendRequest>() {
    @java.lang.Override
    public QueueSendRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new QueueSendRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<QueueSendRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<QueueSendRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.nitric.proto.queue.v1.QueueSendRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

