package com.ricky.api.discovery.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer


@SpringBootApplication
@EnableEurekaServer
class ApiDiscoveryServiceApplication

fun main(args: Array<String>) {
	runApplication<ApiDiscoveryServiceApplication>(*args)
}
