package moviegoods.movie.direct_message.request;


import lombok.Data;

@Data
public class DirectMessageRequestComplete {  //구매자한테만 구매완료 버튼 보이도록
    Long transaction_id;
}
