package site.assad.spring.data.hbase;

import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.assad.spring.data.hbase.api.HbaseTemplate;
import site.assad.spring.data.hbase.api.TableCallback;

import javax.annotation.Resource;
import java.util.List;

/**
 * spring template normal api test
 *
 * @author Al-assad
 * @since 2019/10/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HBaseTemplateTest {
    
    @Resource
    private HbaseTemplate hbaseTemplate;
    
    /**
     * put data test
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
     * delete data test
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
    
    /**
     * get data test
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
     * scan data test
     */
    @Test
    public void testScan() {
        Scan scan = new Scan().withStartRow(Bytes.toBytes("key100")).withStopRow(Bytes.toBytes("key300"));
        
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
    
    /**
     * scan filter data test
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
    
    /**
     * get data test
     */
    @Test
    public void getTest() {
        CityDTO cityDTO = hbaseTemplate.get("city", "key122", new CityDTORowMapper());
        System.out.println(cityDTO);
    }
    
    /**
     * scan data test
     */
    @Test
    public void scanTest(){
        Scan scan = new Scan().withStartRow(Bytes.toBytes("key100")).withStopRow(Bytes.toBytes("key300"));
        List<CityDTO> cityDTO = hbaseTemplate.find("city", scan, new CityDTORowMapper());
    }
    
    
}
