// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: document/v1/document.proto

package io.nitric.proto.document.v1;

/**
 * <pre>
 * Provides a return document type
 * </pre>
 *
 * Protobuf type {@code nitric.document.v1.Document}
 */
public final class Document extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:nitric.document.v1.Document)
    DocumentOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Document.newBuilder() to construct.
  private Document(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Document() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Document();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Document(
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
            com.google.protobuf.Struct.Builder subBuilder = null;
            if (content_ != null) {
              subBuilder = content_.toBuilder();
            }
            content_ = input.readMessage(com.google.protobuf.Struct.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(content_);
              content_ = subBuilder.buildPartial();
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
    return io.nitric.proto.document.v1.Documents.internal_static_nitric_document_v1_Document_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.nitric.proto.document.v1.Documents.internal_static_nitric_document_v1_Document_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.nitric.proto.document.v1.Document.class, io.nitric.proto.document.v1.Document.Builder.class);
  }

  public static final int CONTENT_FIELD_NUMBER = 1;
  private com.google.protobuf.Struct content_;
  /**
   * <pre>
   * The document content (JSON object)
   * </pre>
   *
   * <code>.google.protobuf.Struct content = 1;</code>
   * @return Whether the content field is set.
   */
  @java.lang.Override
  public boolean hasContent() {
    return content_ != null;
  }
  /**
   * <pre>
   * The document content (JSON object)
   * </pre>
   *
   * <code>.google.protobuf.Struct content = 1;</code>
   * @return The content.
   */
  @java.lang.Override
  public com.google.protobuf.Struct getContent() {
    return content_ == null ? com.google.protobuf.Struct.getDefaultInstance() : content_;
  }
  /**
   * <pre>
   * The document content (JSON object)
   * </pre>
   *
   * <code>.google.protobuf.Struct content = 1;</code>
   */
  @java.lang.Override
  public com.google.protobuf.StructOrBuilder getContentOrBuilder() {
    return getContent();
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
    if (content_ != null) {
      output.writeMessage(1, getContent());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (content_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getContent());
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
    if (!(obj instanceof io.nitric.proto.document.v1.Document)) {
      return super.equals(obj);
    }
    io.nitric.proto.document.v1.Document other = (io.nitric.proto.document.v1.Document) obj;

    if (hasContent() != other.hasContent()) return false;
    if (hasContent()) {
      if (!getContent()
          .equals(other.getContent())) return false;
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
    if (hasContent()) {
      hash = (37 * hash) + CONTENT_FIELD_NUMBER;
      hash = (53 * hash) + getContent().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.nitric.proto.document.v1.Document parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.document.v1.Document parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.nitric.proto.document.v1.Document parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.nitric.proto.document.v1.Document parseFrom(
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
  public static Builder newBuilder(io.nitric.proto.document.v1.Document prototype) {
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
   * Provides a return document type
   * </pre>
   *
   * Protobuf type {@code nitric.document.v1.Document}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:nitric.document.v1.Document)
      io.nitric.proto.document.v1.DocumentOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.nitric.proto.document.v1.Documents.internal_static_nitric_document_v1_Document_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.nitric.proto.document.v1.Documents.internal_static_nitric_document_v1_Document_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.nitric.proto.document.v1.Document.class, io.nitric.proto.document.v1.Document.Builder.class);
    }

    // Construct using io.nitric.proto.document.v1.Document.newBuilder()
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
      if (contentBuilder_ == null) {
        content_ = null;
      } else {
        content_ = null;
        contentBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.nitric.proto.document.v1.Documents.internal_static_nitric_document_v1_Document_descriptor;
    }

    @java.lang.Override
    public io.nitric.proto.document.v1.Document getDefaultInstanceForType() {
      return io.nitric.proto.document.v1.Document.getDefaultInstance();
    }

    @java.lang.Override
    public io.nitric.proto.document.v1.Document build() {
      io.nitric.proto.document.v1.Document result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.nitric.proto.document.v1.Document buildPartial() {
      io.nitric.proto.document.v1.Document result = new io.nitric.proto.document.v1.Document(this);
      if (contentBuilder_ == null) {
        result.content_ = content_;
      } else {
        result.content_ = contentBuilder_.build();
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
      if (other instanceof io.nitric.proto.document.v1.Document) {
        return mergeFrom((io.nitric.proto.document.v1.Document)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.nitric.proto.document.v1.Document other) {
      if (other == io.nitric.proto.document.v1.Document.getDefaultInstance()) return this;
      if (other.hasContent()) {
        mergeContent(other.getContent());
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
      io.nitric.proto.document.v1.Document parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.nitric.proto.document.v1.Document) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.Struct content_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Struct, com.google.protobuf.Struct.Builder, com.google.protobuf.StructOrBuilder> contentBuilder_;
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     * @return Whether the content field is set.
     */
    public boolean hasContent() {
      return contentBuilder_ != null || content_ != null;
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     * @return The content.
     */
    public com.google.protobuf.Struct getContent() {
      if (contentBuilder_ == null) {
        return content_ == null ? com.google.protobuf.Struct.getDefaultInstance() : content_;
      } else {
        return contentBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     */
    public Builder setContent(com.google.protobuf.Struct value) {
      if (contentBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        content_ = value;
        onChanged();
      } else {
        contentBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     */
    public Builder setContent(
        com.google.protobuf.Struct.Builder builderForValue) {
      if (contentBuilder_ == null) {
        content_ = builderForValue.build();
        onChanged();
      } else {
        contentBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     */
    public Builder mergeContent(com.google.protobuf.Struct value) {
      if (contentBuilder_ == null) {
        if (content_ != null) {
          content_ =
            com.google.protobuf.Struct.newBuilder(content_).mergeFrom(value).buildPartial();
        } else {
          content_ = value;
        }
        onChanged();
      } else {
        contentBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     */
    public Builder clearContent() {
      if (contentBuilder_ == null) {
        content_ = null;
        onChanged();
      } else {
        content_ = null;
        contentBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     */
    public com.google.protobuf.Struct.Builder getContentBuilder() {

      onChanged();
      return getContentFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     */
    public com.google.protobuf.StructOrBuilder getContentOrBuilder() {
      if (contentBuilder_ != null) {
        return contentBuilder_.getMessageOrBuilder();
      } else {
        return content_ == null ?
            com.google.protobuf.Struct.getDefaultInstance() : content_;
      }
    }
    /**
     * <pre>
     * The document content (JSON object)
     * </pre>
     *
     * <code>.google.protobuf.Struct content = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Struct, com.google.protobuf.Struct.Builder, com.google.protobuf.StructOrBuilder>
        getContentFieldBuilder() {
      if (contentBuilder_ == null) {
        contentBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Struct, com.google.protobuf.Struct.Builder, com.google.protobuf.StructOrBuilder>(
                getContent(),
                getParentForChildren(),
                isClean());
        content_ = null;
      }
      return contentBuilder_;
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


    // @@protoc_insertion_point(builder_scope:nitric.document.v1.Document)
  }

  // @@protoc_insertion_point(class_scope:nitric.document.v1.Document)
  private static final io.nitric.proto.document.v1.Document DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.nitric.proto.document.v1.Document();
  }

  public static io.nitric.proto.document.v1.Document getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Document>
      PARSER = new com.google.protobuf.AbstractParser<Document>() {
    @java.lang.Override
    public Document parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Document(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Document> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Document> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.nitric.proto.document.v1.Document getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

