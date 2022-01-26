package dev.vality.cds.client.identity.document.storage.configuration;

import dev.vality.damsel.identity_document_storage.IdentityDocumentStorageSrv;
import dev.vality.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(CdsClientIDStorageProperties.class)
public class CdsClientIDStorageConfiguration {

    @Bean
    public IdentityDocumentStorageSrv.Iface identityDocumentStorageSrv(CdsClientIDStorageProperties properties)
            throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(properties.getUrl().getURI())
                .withNetworkTimeout(properties.getNetworkTimeout())
                .build(IdentityDocumentStorageSrv.Iface.class);
    }

}
