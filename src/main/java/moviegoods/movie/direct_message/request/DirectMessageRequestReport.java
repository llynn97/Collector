package moviegoods.movie.direct_message.request;


import lombok.Data;

@Data
public class DirectMessageRequestReport {

    Long transaction_id;
    Long user_id;
    String report_content;
}
