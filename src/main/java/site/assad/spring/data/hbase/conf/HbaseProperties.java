package site.assad.spring.data.hbase.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Spring automatic mapping configuration of HBase
 *
 * @author Al-assad
 * @since 2019/10/7
 */
@ConfigurationProperties(prefix = "spring.data.hbase")
public class HbaseProperties {
    
    private Map<String, String> config;
    
    public Map<String, String> getConfig() {
        return config;
    }
    
    public void setConfig(Map<String, String> config) {
        this.config = config;
    }
}
