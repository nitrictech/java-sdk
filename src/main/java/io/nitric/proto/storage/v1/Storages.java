// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: storage/v1/storage.proto

package io.nitric.proto.storage.v1;

public final class Storages {
  private Storages() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_storage_v1_StorageWriteRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_storage_v1_StorageWriteRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_storage_v1_StorageWriteResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_storage_v1_StorageWriteResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_storage_v1_StorageReadRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_storage_v1_StorageReadRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_storage_v1_StorageReadResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_storage_v1_StorageReadResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_storage_v1_StorageDeleteRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_storage_v1_StorageDeleteRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_storage_v1_StorageDeleteResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_storage_v1_StorageDeleteResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030storage/v1/storage.proto\022\021nitric.stora" +
      "ge.v1\"E\n\023StorageWriteRequest\022\023\n\013bucket_n" +
      "ame\030\001 \001(\t\022\013\n\003key\030\002 \001(\t\022\014\n\004body\030\003 \001(\014\"\026\n\024" +
      "StorageWriteResponse\"6\n\022StorageReadReque" +
      "st\022\023\n\013bucket_name\030\001 \001(\t\022\013\n\003key\030\002 \001(\t\"#\n\023" +
      "StorageReadResponse\022\014\n\004body\030\001 \001(\014\"8\n\024Sto" +
      "rageDeleteRequest\022\023\n\013bucket_name\030\001 \001(\t\022\013" +
      "\n\003key\030\002 \001(\t\"\027\n\025StorageDeleteResponse2\236\002\n" +
      "\016StorageService\022U\n\004Read\022%.nitric.storage" +
      ".v1.StorageReadRequest\032&.nitric.storage." +
      "v1.StorageReadResponse\022X\n\005Write\022&.nitric" +
      ".storage.v1.StorageWriteRequest\032\'.nitric" +
      ".storage.v1.StorageWriteResponse\022[\n\006Dele" +
      "te\022\'.nitric.storage.v1.StorageDeleteRequ" +
      "est\032(.nitric.storage.v1.StorageDeleteRes" +
      "ponseBj\n\032io.nitric.proto.storage.v1B\010Sto" +
      "ragesP\001Z\014nitric/v1;v1\252\002\027Nitric.Proto.Sto" +
      "rage.v1\312\002\027Nitric\\Proto\\Storage\\V1b\006proto" +
      "3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_nitric_storage_v1_StorageWriteRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_nitric_storage_v1_StorageWriteRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_storage_v1_StorageWriteRequest_descriptor,
        new java.lang.String[] { "BucketName", "Key", "Body", });
    internal_static_nitric_storage_v1_StorageWriteResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_nitric_storage_v1_StorageWriteResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_storage_v1_StorageWriteResponse_descriptor,
        new java.lang.String[] { });
    internal_static_nitric_storage_v1_StorageReadRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_nitric_storage_v1_StorageReadRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_storage_v1_StorageReadRequest_descriptor,
        new java.lang.String[] { "BucketName", "Key", });
    internal_static_nitric_storage_v1_StorageReadResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_nitric_storage_v1_StorageReadResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_storage_v1_StorageReadResponse_descriptor,
        new java.lang.String[] { "Body", });
    internal_static_nitric_storage_v1_StorageDeleteRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_nitric_storage_v1_StorageDeleteRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_storage_v1_StorageDeleteRequest_descriptor,
        new java.lang.String[] { "BucketName", "Key", });
    internal_static_nitric_storage_v1_StorageDeleteResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_nitric_storage_v1_StorageDeleteResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_storage_v1_StorageDeleteResponse_descriptor,
        new java.lang.String[] { });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
