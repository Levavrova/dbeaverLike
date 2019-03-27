

##  CONNECTION DETAILS


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]` 

**Description:** Provides a single MySQL database connection details  in json format for the received id.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** {"id":1,"name":"name1","hostname":"localhost","port":3306,"databaseName":"information_schema","username":"root"}<br/>
password is currently not sent.

* **Error Response:**<br/>
**Code:** 404 Not Found <br/>
**Content:** 404 NOT_FOUND: Connection details for Id 12 not found.

##


#### * REQUEST Method and URL:<br/>
`GET /connection-details`

**Description:** Provides a list of MySQL database connection details in json format.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** [{"id":1,"name":"name1","hostname":"localhost","port":3306,"databaseName":"information_schema","username":"user1"},<br/>
{"id":2,"name":"name2","hostname":"localhost","port":3306,"databaseName":"information_schema","username":"user2"},<br/>
{"id":3,"name":"name3","hostname":"localhost","port":3306,"databaseName":"connectiondetails","username":"user3"}]<br/>
password is currently not sent.

##


#### * REQUEST Method and URL:<br/>
`POST /connection-details`

* **Data Parameters:**<br/>
JSON body with content-Type application/json<br/>
`{"name":"name6","hostname":"localhost","port":"3306","databaseName":"connectiondetails","username":"user6","password":"pass6"}`

**Description:** Creates a new connection details record.

* **Success Response:**<br/>
**Code:** 201 Created<br/>
**Content:** {"id":6,"name":"name6","hostname":"localhost","port":"3306","databaseName":"connectiondetails","username":"user6"}

* **Error Response:**<br/>
**Code:** 400 Bad Request<br/>
**Content:** 400 BAD_REQUEST: Invalid data - Id must not be specified.

##


#### * REQUEST Method and URL:<br/>
`PUT /connection-details/{id}`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`

* **Data Parameters:**<br/>
JSON body with content-Type application/json<br/>
`{"name":"name6","hostname":"localhost","port":"3306","databaseName":"connectiondetails","username":"user6","password":"pass6"}`

**Description:** Updates a connection details record for received id and data or creates new one based on the received data.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** {"id":6,"name":"name6","hostname":"localhost","port":"3306","databaseName":"connectiondetails","username":"user6"}

##


#### * REQUEST Method and URL:<br/>
`DELETE /connection-details/{id}`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`

**Description:** Deletes connection details record for received id.

* **Success Response:**<br/>
**Code:** 204 No Content<br/>

* **Error Response:**<br/>
**Code:** 404 Not Found<br/>
**Content:** 404 NOT_FOUND: No class com.dbeaverLike.connectionDetails.ConnectionDetails entity with id 12 exists!

##
<br/><br/>


##  DATABASE STRUCTURE


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/schemas`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`

**Description:** Provides a list of schemas, including their privileges and other details,using connection details specified by received id. The operation is supported for database information_schema only.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** [{"schemaName":"information_schema","catalogName":"def","defaultCharacterSetName":"utf8","defaultCollationName":"utf8_general_ci","sqlPath":null,"schemaPrivileges":[]},<br/>
{"schemaName":"sys","catalogName":"def","defaultCharacterSetName":"utf8","defaultCollationName":"utf8_general_ci","sqlPath":null,"schemaPrivileges":<br/>
[{"grantee":"'mysql.sys'@'localhost'","privilegeType":"TRIGGER","isGrantable":"NO"}]}]

* **Error Response:**<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Wrong connection details configuration. The operation is supported for database information_schema only.

##


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/tables`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`

**Description:** Provides a list of tables, including their referential constraints and other details,using connection details specified by received id. The operation is supported for database information_schema only. 

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** [{"tableCatalog":"def","tableSchema":"library","tableName":"books",<br/>
"tableType":"BASE TABLE","engine":"InnoDB","version":10,"rowFormat":"Dynamic","tableRows":18,"avgRowLength":910,"dataLength":16384,"maxDataLength":0,"indexLength":0,"dataFree":0,"autoIncrement":null,"createTime":"2017-03-16T13:31:28","updateTime":null,"checkTime":null,"tableCollation":"utf8_general_ci","checksum":null,"createOptions":"","tableComment":"","referentialConstraints":[]},<br/>
{"tableCatalog":"def","tableSchema":"library","tableName":"books_authors",<br/>
"tableType":"BASE TABLE","engine":"InnoDB","version":10,"rowFormat":"Dynamic","tableRows":23,"avgRowLength":712,"dataLength":16384,"maxDataLength":0,"indexLength":32768,"dataFree":0,"autoIncrement":24,"createTime":"2017-03-16T13:35:19","updateTime":null,"checkTime":null,"tableCollation":"utf8_general_ci","checksum":null,"createOptions":"","tableComment":"",
"referentialConstraints":<br/>
[{"constraintName":"FK_bk_au_au","uniqueConstraintName":"PRIMARY","matchOption":"NONE","updateRule":"RESTRICT","deleteRule":"RESTRICT","referencedTableName":"author"},<br/>
{"constraintName":"FK_bk_au_books","uniqueConstraintName":"PRIMARY","matchOption":"NONE","updateRule":"RESTRICT","deleteRule":"RESTRICT","referencedTableName":"books"}]}]

* **Error Response:**<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Wrong connection details configuration. The operation is supported for database information_schema only.

##


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/schemas/{schemaName}/tables`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`<br/>
`schemaName=[String]`

**Description:** Provides a list of tables, including their referential constraints and other details, for received schema name. Connection details specified by received id are used. The operation is supported for database information_schema only.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content*:** the same as in the previous case

* **Error Response:**<br/>
**Code:** 404 Not Found<br/>
**Content:** 404 NOT_FOUND: Schema library1 does not exist.<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Wrong connection details configuration. The operation is supported for database information_schema only.

##


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/columns`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`

**Description:** Provides a list of columns, including their indexes and other details,using connection details specified by received id. The operation is supported for database information_schema only.  

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** [{"tableCatalog":"def","tableSchema":"royalfamilies","tableName":"person","columnName":"ID",<br/>
"columnType":"int(4) unsigned","columnKey":"PRI","ordinalPosition":1,"columnDefault":null,"isNullable":"NO","dataType":"int","characterMaximumLength":null,"characterOctetLength":null,"numericPrecision":10,"numericScale":0,"datetimePrecision":null,"characterSetName":null,"collationName":null,"extra":"auto_increment","privileges":"select,insert,update,references","columnComment":"","generationExpression":"",
"dbIndexes":<br/>
[{"indexName":"PRIMARY","seqInIndex":1,"nonUnique":0,"collation":"A","cardinality":60,"subPart":null,"packed":null,"nullable":"","indexType":"BTREE","indexComment":"","comment":""}]},<br/>
{"tableCatalog":"def","tableSchema":"royalfamilies","tableName":"person","columnName":"Name",<br/>
"columnType":"char(45)","columnKey":"MUL","ordinalPosition":2,"columnDefault":null,"isNullable":"NO","dataType":"char","characterMaximumLength":45,"characterOctetLength":135,"numericPrecision":null,"numericScale":null,"datetimePrecision":null,"characterSetName":"utf8","collationName":"utf8_general_ci","extra":"","privileges":"select,insert,update,references","columnComment":"","generationExpression":"",
"dbIndexes":<br/>
[{"indexName":"Name_idx","seqInIndex":1,"nonUnique":1,"collation":"A","cardinality":55,"subPart":null,"packed":null,"nullable":"","indexType":"BTREE","indexComment":"","comment":""}]}]

* **Error Response:**<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Wrong connection details configuration. The operation is supported for database information_schema only.

##


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/schemas/{schemaName}/tables/{tableName}/columns`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`<br/>
`schemaName=[String]`<br/>
`tableName=[String]`

**Description:** Provides a list of columns, including their indexes and other details, for received schema name and table name. Connection details specified by received id are used. The operation is supported for database information_schema only.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** the same as in the previous case

* **Error Response:**<br/>
**Code:** 404 Not Found<br/>
**Content:** 404 NOT_FOUND: Schema royalfamilies or table pperson do not exist.<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Wrong connection details configuration. The operation is supported for database information_schema only.

##
<br/><br/>


##  TABLE DATA


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/tables/{tableName}`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`<br/>
`tableName=[String]`

**Description:** Provides a data preview for the received table name. Connection details specified by received id are used.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** [["bk_id","bk_title","bk_ISBN","bk_publisher","bk_published_year"],<br/>
[1,"SQL Bible","978-0470229064","Wiley",2008],<br/>
[2,"Wiley Pathways: Introduction to Database Management","978-0470101865","Wiley",2007]]

* **Error Response:**<br/>
**Code:** 404 Not Found<br/>
**Content:** 404 NOT_FOUND: Table bookes does not exist.<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Unknown database 'librarys'

##
<br/><br/>


##  STATISTICS


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/tables/{tableName}/table-statistics`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`<br/>
`tableName=[String]`

**Description:** Provides statistics - number of records, number of attributes - for the received table name. The connection details specified by received id are used.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** {"recordNumber":7,"attributeNumber":5}

* **Error Response:**<br/>
**Code:** 404 Not Found<br/>
**Content:** 404 NOT_FOUND: Table bookks does not exist.<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Unknown database 'librarys'

##


#### * REQUEST Method and URL:<br/>
`GET /connection-details/{id}/tables/{tableName}/columns/{columnName}/column-statistics`

* **Path Variables:**<br/>
**Required:**<br/>
`id=[long]`<br/>
`tableName=[String]`<br/>
`columnName=[String]`

**Description:** Provides statistics - min, max, avg, median value - for the received column name of the received table name. The connection details specified by received id are used.

* **Success Response:**<br/>
**Code:** 200 OK<br/>
**Content:** {"minValue":1972,"maxValue":2017,"avgValue":2006.3684,"medianValue":2008}

* **Error Response:**<br/>
**Code:** 404 Not Found<br/>
**Content:** 404 NOT_FOUND: Table books or column bk_publisher_year do not exist.<br/>
**Code:** 409 Conflict<br/>
**Content:** 409 CONFLICT: Unknown database 'librarys'

##
<br/><br/>

* **Error Response for all requests except Connection Details requests :**<br/>
**Code:** 503 Service Unavailable<br/>
**Content:** 503 SERVICE_UNAVAILABLE: The data access pool of the database connections is overloaded.
