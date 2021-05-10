// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: kv/v1/kv.proto

package io.nitric.proto.kv.v1;

/**
 * Protobuf type {@code nitric.kv.v1.KeyValueDeleteRequest}
 */
public final class KeyValueDeleteRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:nitric.kv.v1.KeyValueDeleteRequest)
    KeyValueDeleteRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use KeyValueDeleteRequest.newBuilder() to construct.
  private KeyValueDeleteRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private KeyValueDeleteRequest() {
    collection_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new KeyValueDeleteRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private KeyValueDeleteRequest(
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

            collection_ = s;
            break;
          }
          case 18: {
            com.google.protobuf.Struct.Builder subBuilder = null;
            if (key_ != null) {
              subBuilder = key_.toBuilder();
            }
            key_ = input.readMessage(com.google.protobuf.Struct.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(key_);
              key_ = subBuilder.buildPartial();
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
    return io.nitric.proto.kv.v1.KeyValues.internal_static_nitric_kv_v1_KeyValueDeleteRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.nitric.proto.kv.v1.KeyValues.internal_static_nitric_kv_v1_KeyValueDeleteRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.nitric.proto.kv.v1.KeyValueDeleteRequest.class, io.nitric.proto.kv.v1.KeyValueDeleteRequest.Builder.class);
  }

  public static final int COLLECTION_FIELD_NUMBER = 1;
  private volatile java.lang.Object collection_;
  /**
   * <pre>
   * The collection containing the existing keyValue to be deleted
   * </pre>
   *
   * <code>string collection = 1;</code>
   * @return The collection.
   */
  @java.lang.Override
  public java.lang.String getCollection() {
    java.lang.Object ref = collection_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      collection_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * The collection containing the existing keyValue to be deleted
   * </pre>
   *
   * <code>string collection = 1;</code>
   * @return The bytes for collection.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getCollectionBytes() {
    java.lang.Object ref = collection_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      collection_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int KEY_FIELD_NUMBER = 2;
  private com.google.protobuf.Struct key_;
  /**
   * <pre>
   * The unique key of the keyValue to delete
   * </pre>
   *
   * <code>.google.protobuf.Struct key = 2;</code>
   * @return Whether the key field is set.
   */
  @java.lang.Override
  public boolean hasKey() {
    return key_ != null;
  }
  /**
   * <pre>
   * The unique key of the keyValue to delete
   * </pre>
   *
   * <code>.google.protobuf.Struct key = 2;</code>
   * @return The key.
   */
  @java.lang.Override
  public com.google.protobuf.Struct getKey() {
    return key_ == null ? com.google.protobuf.Struct.getDefaultInstance() : key_;
  }
  /**
   * <pre>
   * The unique key of the keyValue to delete
   * </pre>
   *
   * <code>.google.protobuf.Struct key = 2;</code>
   */
  @java.lang.Override
  public com.google.protobuf.StructOrBuilder getKeyOrBuilder() {
    return getKey();
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
    if (!getCollectionBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, collection_);
    }
    if (key_ != null) {
      output.writeMessage(2, getKey());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getCollectionBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, collection_);
    }
    if (key_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getKey());
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
    if (!(obj instanceof io.nitric.proto.kv.v1.KeyValueDeleteRequest)) {
      return super.equals(obj);
    }
    io.nitric.proto.kv.v1.KeyValueDeleteRequest other = (io.nitric.proto.kv.v1.KeyValueDeleteRequest) obj;

    if (!getCollection()
        .equals(other.getCollection())) return false;
    if (hasKey() != other.hasKey()) return false;
    if (hasKey()) {
      if (!getKey()
          .equals(other.getKey())) return false;
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
    hash = (37 * hash) + COLLECTION_FIELD_NUMBER;
    hash = (53 * hash) + getCollection().hashCode();
    if (hasKey()) {
      hash = (37 * hash) + KEY_FIELD_NUMBER;
      hash = (53 * hash) + getKey().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest parseFrom(
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
  public static Builder newBuilder(io.nitric.proto.kv.v1.KeyValueDeleteRequest prototype) {
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
   * Protobuf type {@code nitric.kv.v1.KeyValueDeleteRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:nitric.kv.v1.KeyValueDeleteRequest)
      io.nitric.proto.kv.v1.KeyValueDeleteRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.nitric.proto.kv.v1.KeyValues.internal_static_nitric_kv_v1_KeyValueDeleteRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.nitric.proto.kv.v1.KeyValues.internal_static_nitric_kv_v1_KeyValueDeleteRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.nitric.proto.kv.v1.KeyValueDeleteRequest.class, io.nitric.proto.kv.v1.KeyValueDeleteRequest.Builder.class);
    }

    // Construct using io.nitric.proto.kv.v1.KeyValueDeleteRequest.newBuilder()
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
      collection_ = "";

      if (keyBuilder_ == null) {
        key_ = null;
      } else {
        key_ = null;
        keyBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.nitric.proto.kv.v1.KeyValues.internal_static_nitric_kv_v1_KeyValueDeleteRequest_descriptor;
    }

    @java.lang.Override
    public io.nitric.proto.kv.v1.KeyValueDeleteRequest getDefaultInstanceForType() {
      return io.nitric.proto.kv.v1.KeyValueDeleteRequest.getDefaultInstance();
    }

    @java.lang.Override
    public io.nitric.proto.kv.v1.KeyValueDeleteRequest build() {
      io.nitric.proto.kv.v1.KeyValueDeleteRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.nitric.proto.kv.v1.KeyValueDeleteRequest buildPartial() {
      io.nitric.proto.kv.v1.KeyValueDeleteRequest result = new io.nitric.proto.kv.v1.KeyValueDeleteRequest(this);
      result.collection_ = collection_;
      if (keyBuilder_ == null) {
        result.key_ = key_;
      } else {
        result.key_ = keyBuilder_.build();
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
      if (other instanceof io.nitric.proto.kv.v1.KeyValueDeleteRequest) {
        return mergeFrom((io.nitric.proto.kv.v1.KeyValueDeleteRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.nitric.proto.kv.v1.KeyValueDeleteRequest other) {
      if (other == io.nitric.proto.kv.v1.KeyValueDeleteRequest.getDefaultInstance()) return this;
      if (!other.getCollection().isEmpty()) {
        collection_ = other.collection_;
        onChanged();
      }
      if (other.hasKey()) {
        mergeKey(other.getKey());
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
      io.nitric.proto.kv.v1.KeyValueDeleteRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.nitric.proto.kv.v1.KeyValueDeleteRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object collection_ = "";
    /**
     * <pre>
     * The collection containing the existing keyValue to be deleted
     * </pre>
     *
     * <code>string collection = 1;</code>
     * @return The collection.
     */
    public java.lang.String getCollection() {
      java.lang.Object ref = collection_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        collection_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * The collection containing the existing keyValue to be deleted
     * </pre>
     *
     * <code>string collection = 1;</code>
     * @return The bytes for collection.
     */
    public com.google.protobuf.ByteString
        getCollectionBytes() {
      java.lang.Object ref = collection_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        collection_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * The collection containing the existing keyValue to be deleted
     * </pre>
     *
     * <code>string collection = 1;</code>
     * @param value The collection to set.
     * @return This builder for chaining.
     */
    public Builder setCollection(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      collection_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The collection containing the existing keyValue to be deleted
     * </pre>
     *
     * <code>string collection = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearCollection() {

      collection_ = getDefaultInstance().getCollection();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The collection containing the existing keyValue to be deleted
     * </pre>
     *
     * <code>string collection = 1;</code>
     * @param value The bytes for collection to set.
     * @return This builder for chaining.
     */
    public Builder setCollectionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      collection_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.Struct key_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Struct, com.google.protobuf.Struct.Builder, com.google.protobuf.StructOrBuilder> keyBuilder_;
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     * @return Whether the key field is set.
     */
    public boolean hasKey() {
      return keyBuilder_ != null || key_ != null;
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     * @return The key.
     */
    public com.google.protobuf.Struct getKey() {
      if (keyBuilder_ == null) {
        return key_ == null ? com.google.protobuf.Struct.getDefaultInstance() : key_;
      } else {
        return keyBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     */
    public Builder setKey(com.google.protobuf.Struct value) {
      if (keyBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        key_ = value;
        onChanged();
      } else {
        keyBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     */
    public Builder setKey(
        com.google.protobuf.Struct.Builder builderForValue) {
      if (keyBuilder_ == null) {
        key_ = builderForValue.build();
        onChanged();
      } else {
        keyBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     */
    public Builder mergeKey(com.google.protobuf.Struct value) {
      if (keyBuilder_ == null) {
        if (key_ != null) {
          key_ =
            com.google.protobuf.Struct.newBuilder(key_).mergeFrom(value).buildPartial();
        } else {
          key_ = value;
        }
        onChanged();
      } else {
        keyBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     */
    public Builder clearKey() {
      if (keyBuilder_ == null) {
        key_ = null;
        onChanged();
      } else {
        key_ = null;
        keyBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     */
    public com.google.protobuf.Struct.Builder getKeyBuilder() {

      onChanged();
      return getKeyFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     */
    public com.google.protobuf.StructOrBuilder getKeyOrBuilder() {
      if (keyBuilder_ != null) {
        return keyBuilder_.getMessageOrBuilder();
      } else {
        return key_ == null ?
            com.google.protobuf.Struct.getDefaultInstance() : key_;
      }
    }
    /**
     * <pre>
     * The unique key of the keyValue to delete
     * </pre>
     *
     * <code>.google.protobuf.Struct key = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Struct, com.google.protobuf.Struct.Builder, com.google.protobuf.StructOrBuilder>
        getKeyFieldBuilder() {
      if (keyBuilder_ == null) {
        keyBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Struct, com.google.protobuf.Struct.Builder, com.google.protobuf.StructOrBuilder>(
                getKey(),
                getParentForChildren(),
                isClean());
        key_ = null;
      }
      return keyBuilder_;
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


    // @@protoc_insertion_point(builder_scope:nitric.kv.v1.KeyValueDeleteRequest)
  }

  // @@protoc_insertion_point(class_scope:nitric.kv.v1.KeyValueDeleteRequest)
  private static final io.nitric.proto.kv.v1.KeyValueDeleteRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.nitric.proto.kv.v1.KeyValueDeleteRequest();
  }

  public static io.nitric.proto.kv.v1.KeyValueDeleteRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<KeyValueDeleteRequest>
      PARSER = new com.google.protobuf.AbstractParser<KeyValueDeleteRequest>() {
    @java.lang.Override
    public KeyValueDeleteRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new KeyValueDeleteRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<KeyValueDeleteRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<KeyValueDeleteRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.nitric.proto.kv.v1.KeyValueDeleteRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

