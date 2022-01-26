package dev.vality.fistful.client.withdrawal.configuration;

import dev.vality.damsel.withdrawals.provider_adapter.AdapterHostSrv;
import dev.vality.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties({FistfulClientWithdrawalProperties.class})
public class FistfulClientWithdrawalConfiguration {

    @Bean
    public AdapterHostSrv.Iface adapterHostSrv(FistfulClientWithdrawalProperties properties) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(properties.getUrl().getURI())
                .withNetworkTimeout(properties.getNetworkTimeout())
                .build(AdapterHostSrv.Iface.class);
    }

}
