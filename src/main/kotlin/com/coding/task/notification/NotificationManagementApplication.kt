package com.coding.task.notification

import com.coding.task.notification.consumer.Consumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotificationManagementApplication: CommandLineRunner {

	@Autowired
	private lateinit var consumer: Consumer

	override fun run(vararg args: String?) {
		consumer.consumeEvents()
	}
}

fun main(args: Array<String>) {
	runApplication<NotificationManagementApplication>(*args)
}
