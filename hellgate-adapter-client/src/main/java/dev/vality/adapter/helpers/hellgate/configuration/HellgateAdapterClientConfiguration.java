package dev.vality.adapter.helpers.hellgate.configuration;

import dev.vality.damsel.proxy_provider.ProviderProxyHostSrv;
import dev.vality.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties({HellgateAdapterClientProperties.class})
public class HellgateAdapterClientConfiguration {

    @Bean
    public ProviderProxyHostSrv.Iface providerProxySrv(HellgateAdapterClientProperties properties) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(properties.getUrl().getURI())
                .withNetworkTimeout(properties.getNetworkTimeout())
                .build(ProviderProxyHostSrv.Iface.class);
    }

}
