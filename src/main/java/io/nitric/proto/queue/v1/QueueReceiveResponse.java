// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: queue/v1/queue.proto

package io.nitric.proto.queue.v1;

/**
 * Protobuf type {@code nitric.queue.v1.QueueReceiveResponse}
 */
public final class QueueReceiveResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:nitric.queue.v1.QueueReceiveResponse)
    QueueReceiveResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use QueueReceiveResponse.newBuilder() to construct.
  private QueueReceiveResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private QueueReceiveResponse() {
    tasks_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new QueueReceiveResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private QueueReceiveResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              tasks_ = new java.util.ArrayList<io.nitric.proto.queue.v1.NitricTask>();
              mutable_bitField0_ |= 0x00000001;
            }
            tasks_.add(
                input.readMessage(io.nitric.proto.queue.v1.NitricTask.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        tasks_ = java.util.Collections.unmodifiableList(tasks_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueReceiveResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueReceiveResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.nitric.proto.queue.v1.QueueReceiveResponse.class, io.nitric.proto.queue.v1.QueueReceiveResponse.Builder.class);
  }

  public static final int TASKS_FIELD_NUMBER = 1;
  private java.util.List<io.nitric.proto.queue.v1.NitricTask> tasks_;
  /**
   * <pre>
   * Array of tasks popped off the queue
   * </pre>
   *
   * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
   */
  @java.lang.Override
  public java.util.List<io.nitric.proto.queue.v1.NitricTask> getTasksList() {
    return tasks_;
  }
  /**
   * <pre>
   * Array of tasks popped off the queue
   * </pre>
   *
   * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends io.nitric.proto.queue.v1.NitricTaskOrBuilder> 
      getTasksOrBuilderList() {
    return tasks_;
  }
  /**
   * <pre>
   * Array of tasks popped off the queue
   * </pre>
   *
   * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
   */
  @java.lang.Override
  public int getTasksCount() {
    return tasks_.size();
  }
  /**
   * <pre>
   * Array of tasks popped off the queue
   * </pre>
   *
   * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
   */
  @java.lang.Override
  public io.nitric.proto.queue.v1.NitricTask getTasks(int index) {
    return tasks_.get(index);
  }
  /**
   * <pre>
   * Array of tasks popped off the queue
   * </pre>
   *
   * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
   */
  @java.lang.Override
  public io.nitric.proto.queue.v1.NitricTaskOrBuilder getTasksOrBuilder(
      int index) {
    return tasks_.get(index);
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
    for (int i = 0; i < tasks_.size(); i++) {
      output.writeMessage(1, tasks_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < tasks_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, tasks_.get(i));
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
    if (!(obj instanceof io.nitric.proto.queue.v1.QueueReceiveResponse)) {
      return super.equals(obj);
    }
    io.nitric.proto.queue.v1.QueueReceiveResponse other = (io.nitric.proto.queue.v1.QueueReceiveResponse) obj;

    if (!getTasksList()
        .equals(other.getTasksList())) return false;
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
    if (getTasksCount() > 0) {
      hash = (37 * hash) + TASKS_FIELD_NUMBER;
      hash = (53 * hash) + getTasksList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.queue.v1.QueueReceiveResponse parseFrom(
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
  public static Builder newBuilder(io.nitric.proto.queue.v1.QueueReceiveResponse prototype) {
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
   * Protobuf type {@code nitric.queue.v1.QueueReceiveResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:nitric.queue.v1.QueueReceiveResponse)
      io.nitric.proto.queue.v1.QueueReceiveResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueReceiveResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueReceiveResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.nitric.proto.queue.v1.QueueReceiveResponse.class, io.nitric.proto.queue.v1.QueueReceiveResponse.Builder.class);
    }

    // Construct using io.nitric.proto.queue.v1.QueueReceiveResponse.newBuilder()
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
        getTasksFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (tasksBuilder_ == null) {
        tasks_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        tasksBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.nitric.proto.queue.v1.Queues.internal_static_nitric_queue_v1_QueueReceiveResponse_descriptor;
    }

    @java.lang.Override
    public io.nitric.proto.queue.v1.QueueReceiveResponse getDefaultInstanceForType() {
      return io.nitric.proto.queue.v1.QueueReceiveResponse.getDefaultInstance();
    }

    @java.lang.Override
    public io.nitric.proto.queue.v1.QueueReceiveResponse build() {
      io.nitric.proto.queue.v1.QueueReceiveResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.nitric.proto.queue.v1.QueueReceiveResponse buildPartial() {
      io.nitric.proto.queue.v1.QueueReceiveResponse result = new io.nitric.proto.queue.v1.QueueReceiveResponse(this);
      int from_bitField0_ = bitField0_;
      if (tasksBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          tasks_ = java.util.Collections.unmodifiableList(tasks_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.tasks_ = tasks_;
      } else {
        result.tasks_ = tasksBuilder_.build();
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
      if (other instanceof io.nitric.proto.queue.v1.QueueReceiveResponse) {
        return mergeFrom((io.nitric.proto.queue.v1.QueueReceiveResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.nitric.proto.queue.v1.QueueReceiveResponse other) {
      if (other == io.nitric.proto.queue.v1.QueueReceiveResponse.getDefaultInstance()) return this;
      if (tasksBuilder_ == null) {
        if (!other.tasks_.isEmpty()) {
          if (tasks_.isEmpty()) {
            tasks_ = other.tasks_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureTasksIsMutable();
            tasks_.addAll(other.tasks_);
          }
          onChanged();
        }
      } else {
        if (!other.tasks_.isEmpty()) {
          if (tasksBuilder_.isEmpty()) {
            tasksBuilder_.dispose();
            tasksBuilder_ = null;
            tasks_ = other.tasks_;
            bitField0_ = (bitField0_ & ~0x00000001);
            tasksBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getTasksFieldBuilder() : null;
          } else {
            tasksBuilder_.addAllMessages(other.tasks_);
          }
        }
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
      io.nitric.proto.queue.v1.QueueReceiveResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.nitric.proto.queue.v1.QueueReceiveResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<io.nitric.proto.queue.v1.NitricTask> tasks_ =
      java.util.Collections.emptyList();
    private void ensureTasksIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        tasks_ = new java.util.ArrayList<io.nitric.proto.queue.v1.NitricTask>(tasks_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        io.nitric.proto.queue.v1.NitricTask, io.nitric.proto.queue.v1.NitricTask.Builder, io.nitric.proto.queue.v1.NitricTaskOrBuilder> tasksBuilder_;

    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public java.util.List<io.nitric.proto.queue.v1.NitricTask> getTasksList() {
      if (tasksBuilder_ == null) {
        return java.util.Collections.unmodifiableList(tasks_);
      } else {
        return tasksBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public int getTasksCount() {
      if (tasksBuilder_ == null) {
        return tasks_.size();
      } else {
        return tasksBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public io.nitric.proto.queue.v1.NitricTask getTasks(int index) {
      if (tasksBuilder_ == null) {
        return tasks_.get(index);
      } else {
        return tasksBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder setTasks(
        int index, io.nitric.proto.queue.v1.NitricTask value) {
      if (tasksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTasksIsMutable();
        tasks_.set(index, value);
        onChanged();
      } else {
        tasksBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder setTasks(
        int index, io.nitric.proto.queue.v1.NitricTask.Builder builderForValue) {
      if (tasksBuilder_ == null) {
        ensureTasksIsMutable();
        tasks_.set(index, builderForValue.build());
        onChanged();
      } else {
        tasksBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder addTasks(io.nitric.proto.queue.v1.NitricTask value) {
      if (tasksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTasksIsMutable();
        tasks_.add(value);
        onChanged();
      } else {
        tasksBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder addTasks(
        int index, io.nitric.proto.queue.v1.NitricTask value) {
      if (tasksBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureTasksIsMutable();
        tasks_.add(index, value);
        onChanged();
      } else {
        tasksBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder addTasks(
        io.nitric.proto.queue.v1.NitricTask.Builder builderForValue) {
      if (tasksBuilder_ == null) {
        ensureTasksIsMutable();
        tasks_.add(builderForValue.build());
        onChanged();
      } else {
        tasksBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder addTasks(
        int index, io.nitric.proto.queue.v1.NitricTask.Builder builderForValue) {
      if (tasksBuilder_ == null) {
        ensureTasksIsMutable();
        tasks_.add(index, builderForValue.build());
        onChanged();
      } else {
        tasksBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder addAllTasks(
        java.lang.Iterable<? extends io.nitric.proto.queue.v1.NitricTask> values) {
      if (tasksBuilder_ == null) {
        ensureTasksIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, tasks_);
        onChanged();
      } else {
        tasksBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder clearTasks() {
      if (tasksBuilder_ == null) {
        tasks_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        tasksBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public Builder removeTasks(int index) {
      if (tasksBuilder_ == null) {
        ensureTasksIsMutable();
        tasks_.remove(index);
        onChanged();
      } else {
        tasksBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public io.nitric.proto.queue.v1.NitricTask.Builder getTasksBuilder(
        int index) {
      return getTasksFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public io.nitric.proto.queue.v1.NitricTaskOrBuilder getTasksOrBuilder(
        int index) {
      if (tasksBuilder_ == null) {
        return tasks_.get(index);  } else {
        return tasksBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public java.util.List<? extends io.nitric.proto.queue.v1.NitricTaskOrBuilder> 
         getTasksOrBuilderList() {
      if (tasksBuilder_ != null) {
        return tasksBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(tasks_);
      }
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public io.nitric.proto.queue.v1.NitricTask.Builder addTasksBuilder() {
      return getTasksFieldBuilder().addBuilder(
          io.nitric.proto.queue.v1.NitricTask.getDefaultInstance());
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public io.nitric.proto.queue.v1.NitricTask.Builder addTasksBuilder(
        int index) {
      return getTasksFieldBuilder().addBuilder(
          index, io.nitric.proto.queue.v1.NitricTask.getDefaultInstance());
    }
    /**
     * <pre>
     * Array of tasks popped off the queue
     * </pre>
     *
     * <code>repeated .nitric.queue.v1.NitricTask tasks = 1;</code>
     */
    public java.util.List<io.nitric.proto.queue.v1.NitricTask.Builder> 
         getTasksBuilderList() {
      return getTasksFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        io.nitric.proto.queue.v1.NitricTask, io.nitric.proto.queue.v1.NitricTask.Builder, io.nitric.proto.queue.v1.NitricTaskOrBuilder> 
        getTasksFieldBuilder() {
      if (tasksBuilder_ == null) {
        tasksBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            io.nitric.proto.queue.v1.NitricTask, io.nitric.proto.queue.v1.NitricTask.Builder, io.nitric.proto.queue.v1.NitricTaskOrBuilder>(
                tasks_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        tasks_ = null;
      }
      return tasksBuilder_;
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


    // @@protoc_insertion_point(builder_scope:nitric.queue.v1.QueueReceiveResponse)
  }

  // @@protoc_insertion_point(class_scope:nitric.queue.v1.QueueReceiveResponse)
  private static final io.nitric.proto.queue.v1.QueueReceiveResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.nitric.proto.queue.v1.QueueReceiveResponse();
  }

  public static io.nitric.proto.queue.v1.QueueReceiveResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<QueueReceiveResponse>
      PARSER = new com.google.protobuf.AbstractParser<QueueReceiveResponse>() {
    @java.lang.Override
    public QueueReceiveResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new QueueReceiveResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<QueueReceiveResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<QueueReceiveResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.nitric.proto.queue.v1.QueueReceiveResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

