# Protocol Documentation
<a name="top"></a>

## Table of Contents

- [document/v1/document.proto](#document/v1/document.proto)
    - [Collection](#nitric.document.v1.Collection)
    - [Document](#nitric.document.v1.Document)
    - [DocumentDeleteRequest](#nitric.document.v1.DocumentDeleteRequest)
    - [DocumentDeleteResponse](#nitric.document.v1.DocumentDeleteResponse)
    - [DocumentGetRequest](#nitric.document.v1.DocumentGetRequest)
    - [DocumentGetResponse](#nitric.document.v1.DocumentGetResponse)
    - [DocumentQueryRequest](#nitric.document.v1.DocumentQueryRequest)
    - [DocumentQueryRequest.PagingTokenEntry](#nitric.document.v1.DocumentQueryRequest.PagingTokenEntry)
    - [DocumentQueryResponse](#nitric.document.v1.DocumentQueryResponse)
    - [DocumentQueryResponse.PagingTokenEntry](#nitric.document.v1.DocumentQueryResponse.PagingTokenEntry)
    - [DocumentQueryStreamRequest](#nitric.document.v1.DocumentQueryStreamRequest)
    - [DocumentQueryStreamResponse](#nitric.document.v1.DocumentQueryStreamResponse)
    - [DocumentSetRequest](#nitric.document.v1.DocumentSetRequest)
    - [DocumentSetResponse](#nitric.document.v1.DocumentSetResponse)
    - [Expression](#nitric.document.v1.Expression)
    - [ExpressionValue](#nitric.document.v1.ExpressionValue)
    - [Key](#nitric.document.v1.Key)
  
    - [DocumentService](#nitric.document.v1.DocumentService)
  
- [event/v1/event.proto](#event/v1/event.proto)
    - [EventPublishRequest](#nitric.event.v1.EventPublishRequest)
    - [EventPublishResponse](#nitric.event.v1.EventPublishResponse)
    - [NitricEvent](#nitric.event.v1.NitricEvent)
    - [NitricTopic](#nitric.event.v1.NitricTopic)
    - [TopicListRequest](#nitric.event.v1.TopicListRequest)
    - [TopicListResponse](#nitric.event.v1.TopicListResponse)
  
    - [EventService](#nitric.event.v1.EventService)
    - [TopicService](#nitric.event.v1.TopicService)
  
- [faas/v1/faas.proto](#faas/v1/faas.proto)
    - [ClientMessage](#nitric.faas.v1.ClientMessage)
    - [HttpResponseContext](#nitric.faas.v1.HttpResponseContext)
    - [HttpResponseContext.HeadersEntry](#nitric.faas.v1.HttpResponseContext.HeadersEntry)
    - [HttpTriggerContext](#nitric.faas.v1.HttpTriggerContext)
    - [HttpTriggerContext.HeadersEntry](#nitric.faas.v1.HttpTriggerContext.HeadersEntry)
    - [HttpTriggerContext.QueryParamsEntry](#nitric.faas.v1.HttpTriggerContext.QueryParamsEntry)
    - [InitRequest](#nitric.faas.v1.InitRequest)
    - [InitResponse](#nitric.faas.v1.InitResponse)
    - [ServerMessage](#nitric.faas.v1.ServerMessage)
    - [TopicResponseContext](#nitric.faas.v1.TopicResponseContext)
    - [TopicTriggerContext](#nitric.faas.v1.TopicTriggerContext)
    - [TriggerRequest](#nitric.faas.v1.TriggerRequest)
    - [TriggerResponse](#nitric.faas.v1.TriggerResponse)
  
    - [FaasService](#nitric.faas.v1.FaasService)
  
- [queue/v1/queue.proto](#queue/v1/queue.proto)
    - [FailedTask](#nitric.queue.v1.FailedTask)
    - [NitricTask](#nitric.queue.v1.NitricTask)
    - [QueueCompleteRequest](#nitric.queue.v1.QueueCompleteRequest)
    - [QueueCompleteResponse](#nitric.queue.v1.QueueCompleteResponse)
    - [QueueReceiveRequest](#nitric.queue.v1.QueueReceiveRequest)
    - [QueueReceiveResponse](#nitric.queue.v1.QueueReceiveResponse)
    - [QueueSendBatchRequest](#nitric.queue.v1.QueueSendBatchRequest)
    - [QueueSendBatchResponse](#nitric.queue.v1.QueueSendBatchResponse)
    - [QueueSendRequest](#nitric.queue.v1.QueueSendRequest)
    - [QueueSendResponse](#nitric.queue.v1.QueueSendResponse)
  
    - [QueueService](#nitric.queue.v1.QueueService)
  
- [secret/v1/secret.proto](#secret/v1/secret.proto)
    - [Secret](#nitric.secret.v1.Secret)
    - [SecretAccessRequest](#nitric.secret.v1.SecretAccessRequest)
    - [SecretAccessResponse](#nitric.secret.v1.SecretAccessResponse)
    - [SecretPutRequest](#nitric.secret.v1.SecretPutRequest)
    - [SecretPutResponse](#nitric.secret.v1.SecretPutResponse)
    - [SecretVersion](#nitric.secret.v1.SecretVersion)
  
    - [SecretService](#nitric.secret.v1.SecretService)
  
- [storage/v1/storage.proto](#storage/v1/storage.proto)
    - [StorageDeleteRequest](#nitric.storage.v1.StorageDeleteRequest)
    - [StorageDeleteResponse](#nitric.storage.v1.StorageDeleteResponse)
    - [StorageReadRequest](#nitric.storage.v1.StorageReadRequest)
    - [StorageReadResponse](#nitric.storage.v1.StorageReadResponse)
    - [StorageWriteRequest](#nitric.storage.v1.StorageWriteRequest)
    - [StorageWriteResponse](#nitric.storage.v1.StorageWriteResponse)
  
    - [StorageService](#nitric.storage.v1.StorageService)
  
- [Scalar Value Types](#scalar-value-types)



<a name="document/v1/document.proto"></a>
<p align="right"><a href="#top">Top</a></p>

## document/v1/document.proto



<a name="nitric.document.v1.Collection"></a>

### Collection



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | The collection name |
| parent | [Key](#nitric.document.v1.Key) |  | Optional parent key, required when the collection is a sub-collection of another document |






<a name="nitric.document.v1.Document"></a>

### Document
Provides a return document type


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| content | [google.protobuf.Struct](#google.protobuf.Struct) |  | The document content (JSON object) |
| key | [Key](#nitric.document.v1.Key) |  | The document&#39;s unique key, including collection/sub-collections |






<a name="nitric.document.v1.DocumentDeleteRequest"></a>

### DocumentDeleteRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [Key](#nitric.document.v1.Key) |  | Key of the document to delete |






<a name="nitric.document.v1.DocumentDeleteResponse"></a>

### DocumentDeleteResponse







<a name="nitric.document.v1.DocumentGetRequest"></a>

### DocumentGetRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [Key](#nitric.document.v1.Key) |  | Key of the document to retrieve |






<a name="nitric.document.v1.DocumentGetResponse"></a>

### DocumentGetResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| document | [Document](#nitric.document.v1.Document) |  | The retrieved value |






<a name="nitric.document.v1.DocumentQueryRequest"></a>

### DocumentQueryRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| collection | [Collection](#nitric.document.v1.Collection) |  | The collection to query |
| expressions | [Expression](#nitric.document.v1.Expression) | repeated | Optional query expressions |
| limit | [int32](#int32) |  | Optional query fetch limit |
| paging_token | [DocumentQueryRequest.PagingTokenEntry](#nitric.document.v1.DocumentQueryRequest.PagingTokenEntry) | repeated | Optional query paging continuation token |






<a name="nitric.document.v1.DocumentQueryRequest.PagingTokenEntry"></a>

### DocumentQueryRequest.PagingTokenEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="nitric.document.v1.DocumentQueryResponse"></a>

### DocumentQueryResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| documents | [Document](#nitric.document.v1.Document) | repeated | The retrieved values |
| paging_token | [DocumentQueryResponse.PagingTokenEntry](#nitric.document.v1.DocumentQueryResponse.PagingTokenEntry) | repeated | The query paging continuation token, when empty no further results are available |






<a name="nitric.document.v1.DocumentQueryResponse.PagingTokenEntry"></a>

### DocumentQueryResponse.PagingTokenEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="nitric.document.v1.DocumentQueryStreamRequest"></a>

### DocumentQueryStreamRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| collection | [Collection](#nitric.document.v1.Collection) |  | The collection to query |
| expressions | [Expression](#nitric.document.v1.Expression) | repeated | Optional query expressions |
| limit | [int32](#int32) |  | Optional query fetch limit |






<a name="nitric.document.v1.DocumentQueryStreamResponse"></a>

### DocumentQueryStreamResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| document | [Document](#nitric.document.v1.Document) |  | The stream document |






<a name="nitric.document.v1.DocumentSetRequest"></a>

### DocumentSetRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [Key](#nitric.document.v1.Key) |  | Key of the document to set |
| content | [google.protobuf.Struct](#google.protobuf.Struct) |  | The document content to store (JSON object) |






<a name="nitric.document.v1.DocumentSetResponse"></a>

### DocumentSetResponse







<a name="nitric.document.v1.Expression"></a>

### Expression
Provides a query expression type


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| operand | [string](#string) |  | The query operand or attribute |
| operator | [string](#string) |  | The query operator [ == | &lt; | &lt;= | &gt; | &gt;= | startsWith ] |
| value | [ExpressionValue](#nitric.document.v1.ExpressionValue) |  | The query expression value |






<a name="nitric.document.v1.ExpressionValue"></a>

### ExpressionValue



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| int_value | [int64](#int64) |  | Represents an integer value. |
| double_value | [double](#double) |  | Represents a double value. |
| string_value | [string](#string) |  | Represents a string value. |
| bool_value | [bool](#bool) |  | Represents a boolean value. |






<a name="nitric.document.v1.Key"></a>

### Key
Provides a document identifying key type


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| collection | [Collection](#nitric.document.v1.Collection) |  | The item collection |
| id | [string](#string) |  | The items unique id |





 

 

 


<a name="nitric.document.v1.DocumentService"></a>

### DocumentService
Service for storage and retrieval of simple JSON keyValue

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| Get | [DocumentGetRequest](#nitric.document.v1.DocumentGetRequest) | [DocumentGetResponse](#nitric.document.v1.DocumentGetResponse) | Get an existing document |
| Set | [DocumentSetRequest](#nitric.document.v1.DocumentSetRequest) | [DocumentSetResponse](#nitric.document.v1.DocumentSetResponse) | Create a new or overwrite an existing document |
| Delete | [DocumentDeleteRequest](#nitric.document.v1.DocumentDeleteRequest) | [DocumentDeleteResponse](#nitric.document.v1.DocumentDeleteResponse) | Delete an existing document |
| Query | [DocumentQueryRequest](#nitric.document.v1.DocumentQueryRequest) | [DocumentQueryResponse](#nitric.document.v1.DocumentQueryResponse) | Query the document collection (supports pagination) |
| QueryStream | [DocumentQueryStreamRequest](#nitric.document.v1.DocumentQueryStreamRequest) | [DocumentQueryStreamResponse](#nitric.document.v1.DocumentQueryStreamResponse) stream | Query the document collection (supports streaming) |

 



<a name="event/v1/event.proto"></a>
<p align="right"><a href="#top">Top</a></p>

## event/v1/event.proto



<a name="nitric.event.v1.EventPublishRequest"></a>

### EventPublishRequest
Request to publish an event to a topic


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| topic | [string](#string) |  | The name of the topic to publish the event to |
| event | [NitricEvent](#nitric.event.v1.NitricEvent) |  | The event to be published |






<a name="nitric.event.v1.EventPublishResponse"></a>

### EventPublishResponse
Result of publishing an event


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| id | [string](#string) |  | The id of the published message When an id was not supplied one should be automatically generated |






<a name="nitric.event.v1.NitricEvent"></a>

### NitricEvent
Nitric Event Model


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| id | [string](#string) |  | A Unique ID for the Nitric Event |
| payload_type | [string](#string) |  | A content hint for the events payload |
| payload | [google.protobuf.Struct](#google.protobuf.Struct) |  | The payload of the event |






<a name="nitric.event.v1.NitricTopic"></a>

### NitricTopic
Represents an event topic


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | The Nitric name for the topic |






<a name="nitric.event.v1.TopicListRequest"></a>

### TopicListRequest
Request for the Topic List method






<a name="nitric.event.v1.TopicListResponse"></a>

### TopicListResponse
Topic List Response


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| topics | [NitricTopic](#nitric.event.v1.NitricTopic) | repeated | The list of found topics |





 

 

 


<a name="nitric.event.v1.EventService"></a>

### EventService
Service for publishing asynchronous event

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| Publish | [EventPublishRequest](#nitric.event.v1.EventPublishRequest) | [EventPublishResponse](#nitric.event.v1.EventPublishResponse) | Publishes an message to a given topic |


<a name="nitric.event.v1.TopicService"></a>

### TopicService
Service for management of event topics

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| List | [TopicListRequest](#nitric.event.v1.TopicListRequest) | [TopicListResponse](#nitric.event.v1.TopicListResponse) | Return a list of existing topics in the provider environment |

 



<a name="faas/v1/faas.proto"></a>
<p align="right"><a href="#top">Top</a></p>

## faas/v1/faas.proto



<a name="nitric.faas.v1.ClientMessage"></a>

### ClientMessage
Messages the client is able to send to the server


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| id | [string](#string) |  | Client message ID, used to pair requests/responses |
| init_request | [InitRequest](#nitric.faas.v1.InitRequest) |  | Client initialisation request |
| trigger_response | [TriggerResponse](#nitric.faas.v1.TriggerResponse) |  | Client responsding with result of a trigger |






<a name="nitric.faas.v1.HttpResponseContext"></a>

### HttpResponseContext
Specific HttpResponse message
Note this does not have to be handled by the
User at all but they will have the option of control
If they choose...


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| headers | [HttpResponseContext.HeadersEntry](#nitric.faas.v1.HttpResponseContext.HeadersEntry) | repeated | The request headers... |
| status | [int32](#int32) |  | The HTTP status of the request |






<a name="nitric.faas.v1.HttpResponseContext.HeadersEntry"></a>

### HttpResponseContext.HeadersEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="nitric.faas.v1.HttpTriggerContext"></a>

### HttpTriggerContext



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| method | [string](#string) |  | The request method |
| path | [string](#string) |  | The path of the request |
| headers | [HttpTriggerContext.HeadersEntry](#nitric.faas.v1.HttpTriggerContext.HeadersEntry) | repeated | The request headers |
| query_params | [HttpTriggerContext.QueryParamsEntry](#nitric.faas.v1.HttpTriggerContext.QueryParamsEntry) | repeated | The query params (if parseable by the membrane) |






<a name="nitric.faas.v1.HttpTriggerContext.HeadersEntry"></a>

### HttpTriggerContext.HeadersEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="nitric.faas.v1.HttpTriggerContext.QueryParamsEntry"></a>

### HttpTriggerContext.QueryParamsEntry



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| key | [string](#string) |  |  |
| value | [string](#string) |  |  |






<a name="nitric.faas.v1.InitRequest"></a>

### InitRequest
Placeholder message






<a name="nitric.faas.v1.InitResponse"></a>

### InitResponse
Placeholder message






<a name="nitric.faas.v1.ServerMessage"></a>

### ServerMessage
Messages the server is able to send to the client


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| id | [string](#string) |  | Server message ID, used to pair requests/responses |
| init_response | [InitResponse](#nitric.faas.v1.InitResponse) |  | Server responding with client configuration details to an InitRequest |
| trigger_request | [TriggerRequest](#nitric.faas.v1.TriggerRequest) |  | Server requesting client to process a trigger |






<a name="nitric.faas.v1.TopicResponseContext"></a>

### TopicResponseContext
Specific event response message
We do not accept responses for events
only whether or not they were successfully processed


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| success | [bool](#bool) |  | Success status of the handled event |






<a name="nitric.faas.v1.TopicTriggerContext"></a>

### TopicTriggerContext



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| topic | [string](#string) |  | The topic the message was published for |






<a name="nitric.faas.v1.TriggerRequest"></a>

### TriggerRequest
The server has a trigger for the client to handle


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| data | [bytes](#bytes) |  | The data in the trigger |
| mime_type | [string](#string) |  | Should we supply a mime type for the data? Or rely on context? |
| http | [HttpTriggerContext](#nitric.faas.v1.HttpTriggerContext) |  |  |
| topic | [TopicTriggerContext](#nitric.faas.v1.TopicTriggerContext) |  |  |






<a name="nitric.faas.v1.TriggerResponse"></a>

### TriggerResponse
The worker has successfully processed a trigger


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| data | [bytes](#bytes) |  | The data returned in the response |
| http | [HttpResponseContext](#nitric.faas.v1.HttpResponseContext) |  | response to a http request |
| topic | [TopicResponseContext](#nitric.faas.v1.TopicResponseContext) |  | response to a topic trigger |





 

 

 


<a name="nitric.faas.v1.FaasService"></a>

### FaasService
Service for streaming communication with gRPC FaaS implementations

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| TriggerStream | [ClientMessage](#nitric.faas.v1.ClientMessage) stream | [ServerMessage](#nitric.faas.v1.ServerMessage) stream | Begin streaming triggers/response to/from the membrane |

 



<a name="queue/v1/queue.proto"></a>
<p align="right"><a href="#top">Top</a></p>

## queue/v1/queue.proto



<a name="nitric.queue.v1.FailedTask"></a>

### FailedTask



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| task | [NitricTask](#nitric.queue.v1.NitricTask) |  | The task that failed to be pushed |
| message | [string](#string) |  | A message describing the failure |






<a name="nitric.queue.v1.NitricTask"></a>

### NitricTask
A task to be sent or received from a queue.


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| id | [string](#string) |  | A unique id for the task |
| lease_id | [string](#string) |  | The lease id unique to the pop request, this must be used to complete, extend the lease or release the task. |
| payload_type | [string](#string) |  | A content hint for the tasks payload |
| payload | [google.protobuf.Struct](#google.protobuf.Struct) |  | The payload of the task |






<a name="nitric.queue.v1.QueueCompleteRequest"></a>

### QueueCompleteRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| queue | [string](#string) |  | The nitric name for the queue this will automatically be resolved to the provider specific queue identifier. |
| lease_id | [string](#string) |  | Lease id of the task to be completed |






<a name="nitric.queue.v1.QueueCompleteResponse"></a>

### QueueCompleteResponse







<a name="nitric.queue.v1.QueueReceiveRequest"></a>

### QueueReceiveRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| queue | [string](#string) |  | The nitric name for the queue this will automatically be resolved to the provider specific queue identifier. |
| depth | [int32](#int32) |  | The max number of items to pop off the queue, may be capped by provider specific limitations |






<a name="nitric.queue.v1.QueueReceiveResponse"></a>

### QueueReceiveResponse



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| tasks | [NitricTask](#nitric.queue.v1.NitricTask) | repeated | Array of tasks popped off the queue |






<a name="nitric.queue.v1.QueueSendBatchRequest"></a>

### QueueSendBatchRequest



| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| queue | [string](#string) |  | The Nitric name for the queue this will automatically be resolved to the provider specific queue identifier. |
| tasks | [NitricTask](#nitric.queue.v1.NitricTask) | repeated | Array of tasks to push to the queue |






<a name="nitric.queue.v1.QueueSendBatchResponse"></a>

### QueueSendBatchResponse
Response for sending a collection of tasks


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| failedTasks | [FailedTask](#nitric.queue.v1.FailedTask) | repeated | A list of tasks that failed to be queued |






<a name="nitric.queue.v1.QueueSendRequest"></a>

### QueueSendRequest
Request to push a single event to a queue


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| queue | [string](#string) |  | The Nitric name for the queue this will automatically be resolved to the provider specific queue identifier. |
| task | [NitricTask](#nitric.queue.v1.NitricTask) |  | The task to push to the queue |






<a name="nitric.queue.v1.QueueSendResponse"></a>

### QueueSendResponse
Result of pushing a single task to a queue





 

 

 


<a name="nitric.queue.v1.QueueService"></a>

### QueueService
The Nitric Queue Service contract

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| Send | [QueueSendRequest](#nitric.queue.v1.QueueSendRequest) | [QueueSendResponse](#nitric.queue.v1.QueueSendResponse) | Send a single event to a queue |
| SendBatch | [QueueSendBatchRequest](#nitric.queue.v1.QueueSendBatchRequest) | [QueueSendBatchResponse](#nitric.queue.v1.QueueSendBatchResponse) | Send multiple events to a queue |
| Receive | [QueueReceiveRequest](#nitric.queue.v1.QueueReceiveRequest) | [QueueReceiveResponse](#nitric.queue.v1.QueueReceiveResponse) | Receive event(s) off a queue |
| Complete | [QueueCompleteRequest](#nitric.queue.v1.QueueCompleteRequest) | [QueueCompleteResponse](#nitric.queue.v1.QueueCompleteResponse) | Complete an event previously popped from a queue |

 



<a name="secret/v1/secret.proto"></a>
<p align="right"><a href="#top">Top</a></p>

## secret/v1/secret.proto



<a name="nitric.secret.v1.Secret"></a>

### Secret
The secret container


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| name | [string](#string) |  | The secret name |






<a name="nitric.secret.v1.SecretAccessRequest"></a>

### SecretAccessRequest
Request to get a secret from a Secret Store


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| secret_version | [SecretVersion](#nitric.secret.v1.SecretVersion) |  | The id of the secret |






<a name="nitric.secret.v1.SecretAccessResponse"></a>

### SecretAccessResponse
The secret response


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| secret_version | [SecretVersion](#nitric.secret.v1.SecretVersion) |  | The version of the secret that was requested |
| value | [bytes](#bytes) |  | The value of the secret |






<a name="nitric.secret.v1.SecretPutRequest"></a>

### SecretPutRequest
Request to put a secret to a Secret Store


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| secret | [Secret](#nitric.secret.v1.Secret) |  | The Secret to put to the Secret store |
| value | [bytes](#bytes) |  | The value to assign to that secret |






<a name="nitric.secret.v1.SecretPutResponse"></a>

### SecretPutResponse
Result from putting the secret to a Secret Store


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| secret_version | [SecretVersion](#nitric.secret.v1.SecretVersion) |  | The id of the secret |






<a name="nitric.secret.v1.SecretVersion"></a>

### SecretVersion
A version of a secret


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| secret | [Secret](#nitric.secret.v1.Secret) |  | Reference to the secret container |
| version | [string](#string) |  | The secret version

map&lt;string, string&gt; labels = 4; //Tags for GCP and azure, |





 

 

 


<a name="nitric.secret.v1.SecretService"></a>

### SecretService
The Nitric Secret Service contract

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| Put | [SecretPutRequest](#nitric.secret.v1.SecretPutRequest) | [SecretPutResponse](#nitric.secret.v1.SecretPutResponse) | Updates a secret, creating a new one if it doesn&#39;t already exist |
| Access | [SecretAccessRequest](#nitric.secret.v1.SecretAccessRequest) | [SecretAccessResponse](#nitric.secret.v1.SecretAccessResponse) | Gets a secret from a Secret Store |

 



<a name="storage/v1/storage.proto"></a>
<p align="right"><a href="#top">Top</a></p>

## storage/v1/storage.proto



<a name="nitric.storage.v1.StorageDeleteRequest"></a>

### StorageDeleteRequest
Request to delete a storage item


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| bucket_name | [string](#string) |  | Name of the bucket to delete from |
| key | [string](#string) |  | Key of item to delete |






<a name="nitric.storage.v1.StorageDeleteResponse"></a>

### StorageDeleteResponse
Result of deleting a storage item






<a name="nitric.storage.v1.StorageReadRequest"></a>

### StorageReadRequest
Request to retrieve a storage item


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| bucket_name | [string](#string) |  | Nitric name of the bucket to retrieve from this will be automatically resolved to the provider specific bucket identifier. |
| key | [string](#string) |  | Key of item to retrieve |






<a name="nitric.storage.v1.StorageReadResponse"></a>

### StorageReadResponse
Returned storage item


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| body | [bytes](#bytes) |  | The body bytes of the retrieved storage item |






<a name="nitric.storage.v1.StorageWriteRequest"></a>

### StorageWriteRequest
Request to put (create/update) a storage item


| Field | Type | Label | Description |
| ----- | ---- | ----- | ----------- |
| bucket_name | [string](#string) |  | Nitric name of the bucket to store in this will be automatically resolved to the provider specific bucket identifier. |
| key | [string](#string) |  | Key to store the item under |
| body | [bytes](#bytes) |  | bytes array to store |






<a name="nitric.storage.v1.StorageWriteResponse"></a>

### StorageWriteResponse
Result of putting a storage item





 

 

 


<a name="nitric.storage.v1.StorageService"></a>

### StorageService
Services for storage and retrieval of files in the form of byte arrays, such as text and binary files.

| Method Name | Request Type | Response Type | Description |
| ----------- | ------------ | ------------- | ------------|
| Read | [StorageReadRequest](#nitric.storage.v1.StorageReadRequest) | [StorageReadResponse](#nitric.storage.v1.StorageReadResponse) | Retrieve an item from a bucket |
| Write | [StorageWriteRequest](#nitric.storage.v1.StorageWriteRequest) | [StorageWriteResponse](#nitric.storage.v1.StorageWriteResponse) | Store an item to a bucket |
| Delete | [StorageDeleteRequest](#nitric.storage.v1.StorageDeleteRequest) | [StorageDeleteResponse](#nitric.storage.v1.StorageDeleteResponse) | Delete an item from a bucket |

 



## Scalar Value Types

| .proto Type | Notes | C++ | Java | Python | Go | C# | PHP | Ruby |
| ----------- | ----- | --- | ---- | ------ | -- | -- | --- | ---- |
| <a name="double" /> double |  | double | double | float | float64 | double | float | Float |
| <a name="float" /> float |  | float | float | float | float32 | float | float | Float |
| <a name="int32" /> int32 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint32 instead. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="int64" /> int64 | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint64 instead. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="uint32" /> uint32 | Uses variable-length encoding. | uint32 | int | int/long | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="uint64" /> uint64 | Uses variable-length encoding. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum or Fixnum (as required) |
| <a name="sint32" /> sint32 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int32s. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sint64" /> sint64 | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int64s. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="fixed32" /> fixed32 | Always four bytes. More efficient than uint32 if values are often greater than 2^28. | uint32 | int | int | uint32 | uint | integer | Bignum or Fixnum (as required) |
| <a name="fixed64" /> fixed64 | Always eight bytes. More efficient than uint64 if values are often greater than 2^56. | uint64 | long | int/long | uint64 | ulong | integer/string | Bignum |
| <a name="sfixed32" /> sfixed32 | Always four bytes. | int32 | int | int | int32 | int | integer | Bignum or Fixnum (as required) |
| <a name="sfixed64" /> sfixed64 | Always eight bytes. | int64 | long | int/long | int64 | long | integer/string | Bignum |
| <a name="bool" /> bool |  | bool | boolean | boolean | bool | bool | boolean | TrueClass/FalseClass |
| <a name="string" /> string | A string must always contain UTF-8 encoded or 7-bit ASCII text. | string | String | str/unicode | string | string | string | String (UTF-8) |
| <a name="bytes" /> bytes | May contain any arbitrary sequence of bytes. | string | ByteString | str | []byte | ByteString | string | String (ASCII-8BIT) |

