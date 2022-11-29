package br.com.sistemalima.movie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class MovieApplication

fun main(args: Array<String>) {
	runApplication<MovieApplication>(*args)
}
