//package szu.listener;
//
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class VideoListener {
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "video.update.queue"),
//            exchange = @Exchange(name = "video.topic", type = ExchangeTypes.TOPIC),
//            key = "video.update"
//    ))
//    public void listenVideoUpdate(String msg){
//        System.out.println(Thread.currentThread().getId());
//        System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");
//    }
//
//
//}
