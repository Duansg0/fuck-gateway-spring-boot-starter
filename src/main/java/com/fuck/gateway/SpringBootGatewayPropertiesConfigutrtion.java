package com.fuck.gateway;

import lombok.Data;

/**
 * SpringBootGatewayPropertiesConfigutrtion
 * @author duansg
 * @version 1.0
 */
@Data
public class SpringBootGatewayPropertiesConfigutrtion {

    private int port;

    private int bossThreads;

    private int workerThreads;

}
