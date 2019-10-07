package site.assad.spring.data.hbase;


import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import site.assad.spring.data.hbase.api.RowMapper;

public class CityDTORowMapper implements RowMapper<CityDTO> {
    
    @Override
    public CityDTO mapRow(Result result, int rowNum) throws Exception {
        CityDTO dto = new CityDTO.CityDTOBuilder()
                .rowkey(new String(result.getRow()))
                .name(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("name"))))
                .province(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("province"))))
                .code(new String(result.getValue(Bytes.toBytes("f0"), Bytes.toBytes("code"))))
                .build();
        return dto;
    }
    
}
