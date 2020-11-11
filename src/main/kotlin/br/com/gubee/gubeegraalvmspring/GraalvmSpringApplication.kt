package br.com.gubee.gubeegraalvmspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SpringDataWebAutoConfiguration::class], proxyBeanMethods = false)
class GubeeGraalvmSpringApplication

fun main(args: Array<String>) {
    runApplication<GubeeGraalvmSpringApplication>(*args)
}
