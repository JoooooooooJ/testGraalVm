package br.com.gubee.gubeegraalvmspring.product.adapter.infra

import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Service

@Service
class IntegrateProductRoute {
    companion object {
        val log = LoggerFactory.getLogger(IntegrateProductRoute::class.java)
    }

    /* @KafkaListener(topics = ["\${channel.product-marketplace.topic-name}"],
             concurrency = "\${channel.product-marketplace.partition-size}",
             containerFactory = "kafkaGubeeJsonListenerContainerFactory",
             groupId = "createOrUpdateProductCnova")*/
    @SendTo("\${channel.product-marketplace-callback.topic-name}")
    fun process(teste: MutableMap<Any, Any>) {
        log.info("Test test this is a test {}", teste)
    }
}