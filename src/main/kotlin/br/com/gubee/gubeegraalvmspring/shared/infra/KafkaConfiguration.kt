package br.com.gubee.gubeegraalvmspring.shared.infra

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2
import org.springframework.util.backoff.FixedBackOff
import java.util.function.BiConsumer

@Configuration
class CnovaKafkaConfiguration {

    @Bean
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<Any, Any> {
        val consumerConfigs = kafkaProperties.buildConsumerProperties()
        val valueDeserializerClassConfig = consumerConfigs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG]
        val keyDeserializerClassConfig = consumerConfigs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG]
        consumerConfigs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer2::class.java
        consumerConfigs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer2::class.java
        consumerConfigs[ErrorHandlingDeserializer2.KEY_DESERIALIZER_CLASS] = keyDeserializerClassConfig
        consumerConfigs[ErrorHandlingDeserializer2.VALUE_DESERIALIZER_CLASS] = valueDeserializerClassConfig
        return DefaultKafkaConsumerFactory(consumerConfigs)
    }

    @Bean
    fun kafkaListenerContainerFactory(kafkaConsumerFactory: ConsumerFactory<Any?, Any?>,
                                      configurer: ConcurrentKafkaListenerContainerFactoryConfigurer): ConcurrentKafkaListenerContainerFactory<Any?, Any?> {
        val factory: ConcurrentKafkaListenerContainerFactory<Any?, Any?> = ConcurrentKafkaListenerContainerFactory()
        factory.consumerFactory = kafkaConsumerFactory
        factory.containerProperties.isAckOnError = false
        factory.containerProperties.ackMode = ContainerProperties.AckMode.RECORD
        configurer.configure(factory, kafkaConsumerFactory)
        return factory
    }

    @Bean
    fun seekToCurrentErrorHandler(): SeekToCurrentErrorHandler {
        val fixedBackOff = FixedBackOff(1000, 1)
        val seek = SeekToCurrentErrorHandler(BiConsumer { t, u ->
            print("?")
        }, fixedBackOff)
        return seek
    }
}