package RabbitMQ;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;


@RefreshScope // important
@Configuration
@Component
@ConfigurationProperties(prefix = "views")
@Getter @Setter @ToString
public class RabbitMQConfig {

    private Map<String, String> routingKeyMap;
    private Map<String, String> queueMap;


    @Value("${view_count_exchange}")
    private String exchange;
    @Value("${post_views_exchange}")
    private String exchangeforPost;

    ///////////////////////// Queues Beans /////////////////
    @Bean
    public Queue views_directoryandsearch(){
        return new Queue("view_count_queue");
    }
    @Bean
    public Queue posts_views(){
        return new Queue("post_views_queue");
    }


    // spring bean for exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }
    @Bean
    public TopicExchange exchangeforpost(){
        return new TopicExchange(exchangeforPost);
    }
    // spring bean for binding between exchange and queue using routing key //
    @Bean
    public Binding viewAppBinding(){
        return BindingBuilder
                .bind(views_directoryandsearch())
                .to(exchange())
                .with("view_count_routing_key");
    }
    @Bean
    public Binding viewAppPostBinding(){
        return BindingBuilder
                .bind(posts_views())
                .to(exchangeforpost())
                .with("post_views_routing_key");
    }
    // message converter
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    // configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}