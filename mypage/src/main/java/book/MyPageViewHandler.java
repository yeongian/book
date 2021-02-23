package book;

import book.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MyPageViewHandler {


    @Autowired
    public MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserved_then_CREATE_1 (@Payload Reserved reserved) {
        try {
            if (reserved.isMe()) {
                // view 객체 생성
                MyPage myPage = new MyPage();
                // view 객체에 이벤트의 Value 를 set 함
                myPage.setOrderId(reserved.getId());
                myPage.setProductId(reserved.getProductId());
                myPage.setStatusCode(reserved.getStatusCode());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenDelivered_then_UPDATE_1(@Payload Delivered delivered) {
        try {
            if (delivered.isMe()) {
                // view 객체 조회
                MyPage myPage = myPageRepository.findById(delivered.getOrderId()).get();
                // for(  : List){
                //     // view 객체에 이벤트의 eventDirectValue 를 set 함
                //     // view 레파지 토리에 save
                //     Repository.save();
                // }
                myPage.setStatusCode(delivered.getStatusCode());

                myPageRepository.save(myPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCanceled_then_DELETE_1(@Payload Canceled canceled) {
        try {
            if (canceled.isMe()) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}