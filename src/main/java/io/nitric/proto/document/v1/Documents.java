// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: document/v1/document.proto

package io.nitric.proto.document.v1;

public final class Documents {
  private Documents() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_Collection_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_Collection_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_Key_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_Key_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_Document_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_Document_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_ExpressionValue_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_ExpressionValue_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_Expression_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_Expression_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentGetRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentGetRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentGetResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentGetResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentSetRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentSetRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentSetResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentSetResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentDeleteRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentDeleteRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentDeleteResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentDeleteResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentQueryRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentQueryRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentQueryRequest_PagingTokenEntry_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentQueryRequest_PagingTokenEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentQueryResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentQueryResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentQueryResponse_PagingTokenEntry_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentQueryResponse_PagingTokenEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentQueryStreamRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentQueryStreamRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_nitric_document_v1_DocumentQueryStreamResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_nitric_document_v1_DocumentQueryStreamResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032document/v1/document.proto\022\022nitric.doc" +
      "ument.v1\032\034google/protobuf/struct.proto\"C" +
      "\n\nCollection\022\014\n\004name\030\001 \001(\t\022\'\n\006parent\030\002 \001" +
      "(\0132\027.nitric.document.v1.Key\"E\n\003Key\0222\n\nco" +
      "llection\030\001 \001(\0132\036.nitric.document.v1.Coll" +
      "ection\022\n\n\002id\030\002 \001(\t\"4\n\010Document\022(\n\007conten" +
      "t\030\001 \001(\0132\027.google.protobuf.Struct\"t\n\017Expr" +
      "essionValue\022\023\n\tint_value\030\001 \001(\003H\000\022\026\n\014doub" +
      "le_value\030\002 \001(\001H\000\022\026\n\014string_value\030\003 \001(\tH\000" +
      "\022\024\n\nbool_value\030\004 \001(\010H\000B\006\n\004kind\"c\n\nExpres" +
      "sion\022\017\n\007operand\030\001 \001(\t\022\020\n\010operator\030\002 \001(\t\022" +
      "2\n\005value\030\003 \001(\0132#.nitric.document.v1.Expr" +
      "essionValue\":\n\022DocumentGetRequest\022$\n\003key" +
      "\030\001 \001(\0132\027.nitric.document.v1.Key\"E\n\023Docum" +
      "entGetResponse\022.\n\010document\030\001 \001(\0132\034.nitri" +
      "c.document.v1.Document\"d\n\022DocumentSetReq" +
      "uest\022$\n\003key\030\001 \001(\0132\027.nitric.document.v1.K" +
      "ey\022(\n\007content\030\003 \001(\0132\027.google.protobuf.St" +
      "ruct\"\025\n\023DocumentSetResponse\"=\n\025DocumentD" +
      "eleteRequest\022$\n\003key\030\001 \001(\0132\027.nitric.docum" +
      "ent.v1.Key\"\030\n\026DocumentDeleteResponse\"\223\002\n" +
      "\024DocumentQueryRequest\0222\n\ncollection\030\001 \001(" +
      "\0132\036.nitric.document.v1.Collection\0223\n\013exp" +
      "ressions\030\003 \003(\0132\036.nitric.document.v1.Expr" +
      "ession\022\r\n\005limit\030\004 \001(\005\022O\n\014paging_token\030\005 " +
      "\003(\01329.nitric.document.v1.DocumentQueryRe" +
      "quest.PagingTokenEntry\0322\n\020PagingTokenEnt" +
      "ry\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001\"\316\001\n\025D" +
      "ocumentQueryResponse\022/\n\tdocuments\030\001 \003(\0132" +
      "\034.nitric.document.v1.Document\022P\n\014paging_" +
      "token\030\002 \003(\0132:.nitric.document.v1.Documen" +
      "tQueryResponse.PagingTokenEntry\0322\n\020Pagin" +
      "gTokenEntry\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:" +
      "\0028\001\"\224\001\n\032DocumentQueryStreamRequest\0222\n\nco" +
      "llection\030\001 \001(\0132\036.nitric.document.v1.Coll" +
      "ection\0223\n\013expressions\030\003 \003(\0132\036.nitric.doc" +
      "ument.v1.Expression\022\r\n\005limit\030\004 \001(\005\"M\n\033Do" +
      "cumentQueryStreamResponse\022.\n\010document\030\001 " +
      "\001(\0132\034.nitric.document.v1.Document2\362\003\n\017Do" +
      "cumentService\022V\n\003Get\022&.nitric.document.v" +
      "1.DocumentGetRequest\032\'.nitric.document.v" +
      "1.DocumentGetResponse\022V\n\003Set\022&.nitric.do" +
      "cument.v1.DocumentSetRequest\032\'.nitric.do" +
      "cument.v1.DocumentSetResponse\022_\n\006Delete\022" +
      ").nitric.document.v1.DocumentDeleteReque" +
      "st\032*.nitric.document.v1.DocumentDeleteRe" +
      "sponse\022\\\n\005Query\022(.nitric.document.v1.Doc" +
      "umentQueryRequest\032).nitric.document.v1.D" +
      "ocumentQueryResponse\022p\n\013QueryStream\022..ni" +
      "tric.document.v1.DocumentQueryStreamRequ" +
      "est\032/.nitric.document.v1.DocumentQuerySt" +
      "reamResponse0\001Bn\n\033io.nitric.proto.docume" +
      "nt.v1B\tDocumentsP\001Z\014nitric/v1;v1\252\002\030Nitri" +
      "c.Proto.Document.v1\312\002\030Nitric\\Proto\\Docum" +
      "ent\\V1b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.StructProto.getDescriptor(),
        });
    internal_static_nitric_document_v1_Collection_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_nitric_document_v1_Collection_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_Collection_descriptor,
        new java.lang.String[] { "Name", "Parent", });
    internal_static_nitric_document_v1_Key_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_nitric_document_v1_Key_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_Key_descriptor,
        new java.lang.String[] { "Collection", "Id", });
    internal_static_nitric_document_v1_Document_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_nitric_document_v1_Document_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_Document_descriptor,
        new java.lang.String[] { "Content", });
    internal_static_nitric_document_v1_ExpressionValue_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_nitric_document_v1_ExpressionValue_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_ExpressionValue_descriptor,
        new java.lang.String[] { "IntValue", "DoubleValue", "StringValue", "BoolValue", "Kind", });
    internal_static_nitric_document_v1_Expression_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_nitric_document_v1_Expression_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_Expression_descriptor,
        new java.lang.String[] { "Operand", "Operator", "Value", });
    internal_static_nitric_document_v1_DocumentGetRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_nitric_document_v1_DocumentGetRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentGetRequest_descriptor,
        new java.lang.String[] { "Key", });
    internal_static_nitric_document_v1_DocumentGetResponse_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_nitric_document_v1_DocumentGetResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentGetResponse_descriptor,
        new java.lang.String[] { "Document", });
    internal_static_nitric_document_v1_DocumentSetRequest_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_nitric_document_v1_DocumentSetRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentSetRequest_descriptor,
        new java.lang.String[] { "Key", "Content", });
    internal_static_nitric_document_v1_DocumentSetResponse_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_nitric_document_v1_DocumentSetResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentSetResponse_descriptor,
        new java.lang.String[] { });
    internal_static_nitric_document_v1_DocumentDeleteRequest_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_nitric_document_v1_DocumentDeleteRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentDeleteRequest_descriptor,
        new java.lang.String[] { "Key", });
    internal_static_nitric_document_v1_DocumentDeleteResponse_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_nitric_document_v1_DocumentDeleteResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentDeleteResponse_descriptor,
        new java.lang.String[] { });
    internal_static_nitric_document_v1_DocumentQueryRequest_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_nitric_document_v1_DocumentQueryRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentQueryRequest_descriptor,
        new java.lang.String[] { "Collection", "Expressions", "Limit", "PagingToken", });
    internal_static_nitric_document_v1_DocumentQueryRequest_PagingTokenEntry_descriptor =
      internal_static_nitric_document_v1_DocumentQueryRequest_descriptor.getNestedTypes().get(0);
    internal_static_nitric_document_v1_DocumentQueryRequest_PagingTokenEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentQueryRequest_PagingTokenEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_nitric_document_v1_DocumentQueryResponse_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_nitric_document_v1_DocumentQueryResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentQueryResponse_descriptor,
        new java.lang.String[] { "Documents", "PagingToken", });
    internal_static_nitric_document_v1_DocumentQueryResponse_PagingTokenEntry_descriptor =
      internal_static_nitric_document_v1_DocumentQueryResponse_descriptor.getNestedTypes().get(0);
    internal_static_nitric_document_v1_DocumentQueryResponse_PagingTokenEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentQueryResponse_PagingTokenEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_nitric_document_v1_DocumentQueryStreamRequest_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_nitric_document_v1_DocumentQueryStreamRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentQueryStreamRequest_descriptor,
        new java.lang.String[] { "Collection", "Expressions", "Limit", });
    internal_static_nitric_document_v1_DocumentQueryStreamResponse_descriptor =
      getDescriptor().getMessageTypes().get(14);
    internal_static_nitric_document_v1_DocumentQueryStreamResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_nitric_document_v1_DocumentQueryStreamResponse_descriptor,
        new java.lang.String[] { "Document", });
    com.google.protobuf.StructProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
