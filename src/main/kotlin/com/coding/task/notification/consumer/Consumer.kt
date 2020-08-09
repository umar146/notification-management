package com.coding.task.notification.consumer

import com.rabbitmq.client.*
import com.rabbitmq.client.Consumer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Consumer {

    /**
     * RabbitMQ Host Name
     */
    @Value("\${spring.rabbitmq.host}")
    var hostname: String? = null

    /**
     * RabbitMQ Port
     */
    @Value("\${spring.rabbitmq.port}")
    var port: String? = null

    /**
     * RabbitMQ User Name
     */
    @Value("\${spring.rabbitmq.username}")
    var username: String? = null

    /**
     * RabbitMQ Password
     */
    @Value("\${spring.rabbitmq.password}")
    private val password: String? = null

    final val ROUTING_KEY_USER_IMPORTANT_WARN = "user.important.warn"
    final val ROUTING_KEY_USER_IMPORTANT_INFO = "user.important.info"
    final val TOPIC_EXCHANGE_NAME = "orders.topic.exchange"
    final val QUEUE_NAME = "orders.topic.queue"
    final val BINDING_PATTERN_IMPORTANT = "*.important.*"
    val autoAck = true

    fun consumeEvents() {

        val factory = ConnectionFactory()
        factory.setUri("amqp://$username:$password@$hostname:$port")
        factory.virtualHost = "/";
        factory.isAutomaticRecoveryEnabled = true

        val connection: Connection = factory.newConnection()
        val channel: Channel = connection.createChannel()

        val queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        val assignedQueueName = queueDeclare?.getQueue()
        channel?.queueBind(assignedQueueName, TOPIC_EXCHANGE_NAME, BINDING_PATTERN_IMPORTANT)
        channel?.basicConsume(assignedQueueName, autoAck, ConsumerCallbackImpl())
        println("consumer listening")
    }
}

class ConsumerCallbackImpl : Consumer {

    override fun handleRecoverOk(p0: String?) {

    }

    override fun handleConsumeOk(p0: String?) {

    }

    override fun handleShutdownSignal(p0: String?, p1: ShutdownSignalException?) {

    }

    override fun handleCancel(p0: String?) {

    }

    override fun handleDelivery(consumerTag: String?, p1: Envelope?, basicProperties: AMQP.BasicProperties?, body: ByteArray?) {
        println("Event Info: " + body?.toString(Charsets.UTF_8))
    }

    override fun handleCancelOk(p0: String?) {

    }
}