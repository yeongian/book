package book;

import book.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler {
    @Autowired
    ProductRepository productRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString) {

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_(@Payload Delivered delivered) {

        if (delivered.isMe()) {
            System.out.println("##### listener  : " + delivered.toJson());
            // productId로 해당 제품 찾기
            // Product product = productRepository.findById(delivered.getProductId()).orElseThrow(IllegalArgumentException::new);
            Product product = productRepository.findById(delivered.getProductId()).get();
            product.setStock(product.getStock() - 1);

            productRepository.save(product);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCanceled_(@Payload Canceled canceled){

        if(canceled.isMe()){
            System.out.println("##### listener  : " + canceled.toJson());
            // productId로 해당 제품 찾기
            Product product = productRepository.findById(canceled.getProductId()).get();
            product.setStock(product.getStock() + 1);

            productRepository.save(product);
        }
    }

}
