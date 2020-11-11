package br.com.gubee.gubeegraalvmspring.product.adapter.channel

import br.com.gubee.gubeegraalvmspring.shared.Channel
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.stereotype.Component


/**
 * Permite externalizar as propriedades com os topicos a serem utilizado no fluxo de produto
 * @author renatojava
 */
@Component
@ConfigurationProperties("product.channel")
class ProductChannel(var pushProductChannel: Channel =
                             Channel(name = "cnova.productChannel.push",
                                     concurrency = 6,
                                     replicas = 1,
                                     partitions = 3),
                     var productIntegrationChannel: Channel =
                             Channel(name = "createOrUpdateProductCnova",
                                     replicas = 1,
                                     partitions = 8))

/**
 * Define a cricao de cada topico para o fluxo de produto
 * @author renatojava
 */
@Configuration
class ProductChannelConfiguration(private val productChannel: ProductChannel/*,
                                  private val channelProperties: ChannelProperties*/) {

    @Bean
    fun pushProductChannelTopic() = TopicBuilder.name(productChannel.pushProductChannel.name)
            .partitions(productChannel.pushProductChannel.partitions)
            .replicas(productChannel.pushProductChannel.replicas)
            .build()

    /* @Bean
     fun createProductConsumerTopic() = TopicBuilder.name(channelProperties.productMarketplace.topicName)
             .partitions(channelProperties.productMarketplace.partitionSize)
             .replicas(1)
             .build()*/


}