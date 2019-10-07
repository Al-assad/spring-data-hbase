# Spring-Data-Hbase

spring-data-hadoop 项目 HBaseTemplate API 提取，支持 HBase 2.x API

<br>

## 项目说明

由于 [spring-data-hadoop](https://spring.io/projects/spring-boot) 目前已经停止更新（2019-04-05），最后一个版本不支持 Hbase-Client-2.x 的 API，此项目基于 [spring-hadoop](https://github.com/Al-assad/spring-data-hbase) 代码，提取 spring-data-hbase 部分的代码，更新相关代码以支持 HBase 2.x。

该项目引入的主要组件依赖版本如下：

* spring-boot：2.1.8.RELEASE

* hbase-client：2.0.0

实际使用可以根据实际版本修改 pom 并重新打包；

<br>

## HBase 连接配置

spring-data-hbase 已经集成了 spring-boot 的 auto configuration 功能，自动读取 yml 或者 properties 中以 `spring.data.hbase.config`  为前缀的配置项，作为 hbase 的连接配置项，以下是一个示例的 yml 配置文件：

```yaml
spring.data.hbase.config:
    hbase.zookeeper.quorum: hbase
    hbase.zookeeper.property.clientPort: 2181
    hbase.zookeeper.parent: /hbase
    hbase.rootdir: hdfs://namenode:9000/hbase
```

<br>

## HBaseTemplate 使用说明

HBaseTemplate 的使用和原生 HBaseTemplate API 完全一致，所有的测试代码位于 `/src/test`  目录下，以下作简要说明：

### 示例用的 HBase 初始化语句

```
create 'city', 'f0'
```

### 映射用的 DTO 实体

```java

public class CityDTO {
    private String rowkey;
    private String name;
    private String province;
    private String code;
    .......
 }
```

### HBase Put 操作

```java
/**
 * 新增/更新数据
 */
@Test
public void testPut() {
hbaseTemplate.execute("city", (TableCallback<Object>) table -> {
    String rowkey = "key122";
    Put put = new Put(Bytes.toBytes(rowkey));
    put.addColumn(Bytes.toBytes("f0"), Bytes.toBytes("name"), Bytes.toBytes("guangzhou"));
    put.addColumn(Bytes.toBytes("f0"), Bytes.toBytes("province"), Bytes.toBytes("guangdong"));
    put.addColumn(Bytes.toBytes("f0"), Bytes.toBytes("code"), Bytes.toBytes("G-11"));
    table.put(put);
    return rowkey;
});
}

/**
 * 删除数据
 */
@Test
public void testDelete() {
hbaseTemplate.execute("city", (TableCallback<Object>) table -> {
    String rowkey = "key122";
    Delete delete = new Delete(Bytes.toBytes(rowkey));
    table.delete(delete);
    return rowkey;
});
}
```

#### HBase Get/Scan 操作

```java
/**
 * get 操作
 */
@Test
public void testGet() {
String rowkey = "key122";
CityDTO results = hbaseTemplate.get("city", rowkey, (result, rowNum) -> {
    CityDTO dto = new CityDTO.CityDTOBuilder()
            .rowkey(new String(result.getRow()))
            .name(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("name"))))
            .province(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("province"))))
            .code(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("code"))))
            .build();
    return dto;
});
System.out.println(results);
}

/**
 * scan 操作
 */
@Test
public void testScanFilter() {
Scan scan = new Scan().withStartRow(Bytes.toBytes("key100")).withStopRow(Bytes.toBytes("key300"));
scan.addFamily(Bytes.toBytes("f0"));
scan.setFilter(new SingleColumnValueFilter(
        Bytes.toBytes("f0"),
        Bytes.toBytes("code"),
        CompareOperator.EQUAL,
        Bytes.toBytes("G-11")));

List<CityDTO> results = hbaseTemplate.find("city", scan, (result, rowNum) -> {
    CityDTO dto = new CityDTO.CityDTOBuilder()
            .rowkey(new String(result.getRow()))
            .name(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("name"))))
            .province(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("province"))))
            .code(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("code"))))
            .build();
    return dto;
});
results.forEach(System.out::println);
}
```

