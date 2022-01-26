package dev.vality.bender.client.configuration;

import dev.vality.bender.BenderSrv;
import dev.vality.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties({BenderClientProperties.class})
public class BenderConfiguration {

    @Bean
    public BenderSrv.Iface benderSrv(BenderClientProperties properties) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(properties.getClient().getUrl().getURI())
                .withNetworkTimeout(properties.getClient().getNetworkTimeout())
                .build(BenderSrv.Iface.class);
    }

}
