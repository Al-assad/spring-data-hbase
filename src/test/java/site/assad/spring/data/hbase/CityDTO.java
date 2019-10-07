package site.assad.spring.data.hbase;

public class CityDTO {
    
    private String rowkey;
    private String name;
    private String province;
    private String code;
    
    public CityDTO() {
    }
    
    CityDTO(String rowkey, String name, String province, String code) {
        this.rowkey = rowkey;
        this.name = name;
        this.province = province;
        this.code = code;
    }
    
    public static CityDTOBuilder builder() {
        return new CityDTOBuilder();
    }
    
    public String getRowkey() {
        return rowkey;
    }
    
    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return "CityDTO{" +
                "rowkey='" + rowkey + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
    
    public static class CityDTOBuilder {
        private String rowkey;
        private String name;
        private String province;
        private String code;
        
        CityDTOBuilder() {
        }
        
        public CityDTOBuilder rowkey(String rowkey) {
            this.rowkey = rowkey;
            return this;
        }
        
        public CityDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public CityDTOBuilder province(String province) {
            this.province = province;
            return this;
        }
        
        public CityDTOBuilder code(String code) {
            this.code = code;
            return this;
        }
        
        public CityDTO build() {
            return new CityDTO(rowkey, name, province, code);
        }
        
        public String toString() {
            return "CityDTO.CityDTOBuilder(rowkey=" + this.rowkey + ", name=" + this.name + ", province=" + this.province + ", code=" + this.code + ")";
        }
    }
}
