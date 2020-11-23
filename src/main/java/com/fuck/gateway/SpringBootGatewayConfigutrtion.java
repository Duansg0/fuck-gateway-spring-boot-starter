package com.fuck.gateway;

import com.fuck.gateway.server.GatewayInitializedServer;
import com.fuck.gateway.server.GatewayServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * starter configutrtion
 * @author duansg
 * @version 1.0
 */
@Configuration
@ComponentScan("com.fuck.gateway")
@ConditionalOnProperty(prefix = "fuck.gateway",name = "enable",havingValue = "true",matchIfMissing = true)
@EnableConfigurationProperties(SpringBootGatewayPropertiesConfigutrtion.class)
public class SpringBootGatewayConfigutrtion {

    @Bean
    public GatewayServer initStart(){
        return GatewayInitializedServer.builder()
                .build();
    }


}
