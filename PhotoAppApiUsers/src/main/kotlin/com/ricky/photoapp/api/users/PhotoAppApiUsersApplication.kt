package com.ricky.photoapp.api.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PhotoAppApiUsersApplication

fun main(args: Array<String>) {
	runApplication<PhotoAppApiUsersApplication>(*args)
}
