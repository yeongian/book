package book;

import book.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_(@Payload Reserved reserved){

        if(reserved.isMe()){
            System.out.println("##### listener  : " + reserved.toJson());
            //
            Delivery delivery = new Delivery();
            
            delivery.setStatusCode(2); // 2 - 배송완료
            delivery.setOrderId(reserved.getProductId());
            delivery.setProductId(reserved.getProductId());

            deliveryRepository.save(delivery);
        }
    }

}
