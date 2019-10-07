package site.assad.spring.data.hbase.conf;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import site.assad.spring.data.hbase.api.HbaseTemplate;

import java.util.Map;
import java.util.Set;


/**
 * Spring automatic mapping configuration of HBase
 *
 * @author Al-assad
 * @since 2019/10/7
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {
    
    private HbaseProperties hbaseProperties;
    
    @Autowired
    public void setHbaseProperties(HbaseProperties hbaseProperties) {
        this.hbaseProperties = hbaseProperties;
    }
    
    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        return new HbaseTemplate(getHbaseConfiguration());
    }
    
    private org.apache.hadoop.conf.Configuration getHbaseConfiguration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        Map<String, String> config = hbaseProperties.getConfig();
        if (config == null) {
            return configuration;
        }
        Set<String> keySet = config.keySet();
        for (String key : keySet) {
            configuration.set(key, config.get(key));
        }
        return configuration;
    }
}
