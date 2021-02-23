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
    ReservationRepository reservationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCanceled_(@Payload Canceled canceled){

        if(canceled.isMe()){
            System.out.println("##### listener  : " + canceled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_(@Payload Delivered delivered){

        if(delivered.isMe()){
            System.out.println("##### listener  : " + delivered.toJson());
            //
            Reservation reservation = reservationRepository.findById(delivered.getOrderId()).get();

            reservation.setStatusCode(2);

            reservationRepository.save(reservation);
        }
    }

}
