package com.ricky.api.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ApiUserApplication

fun main(args: Array<String>) {
	runApplication<ApiUserApplication>(*args)
}
