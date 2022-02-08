package moviegoods.movie.direct_message;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.direct_message.request.DirectMessageRequestComplete;
import moviegoods.movie.direct_message.request.DirectMessageRequestReliability;
import moviegoods.movie.direct_message.request.DirectMessageRequestReport;
import moviegoods.movie.direct_message.response.Result;
import moviegoods.movie.domain.Report;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/direct-message")
public class DirectMessageController {
    private  final DirectMessageService directMessageService;

    @PostMapping("/transaction-complete")
    public Result complete(@RequestBody  DirectMessageRequestComplete dmrc){
         Boolean check= directMessageService.updateTransactionComplete(dmrc);
         Result result=new Result();
         if(check==true){
             result.setResult(true);
         }else{
             result.setResult(false);
         }
         return result;
    }

    @PostMapping("/reliability")
    public Result updateReliability(@RequestBody DirectMessageRequestReliability dmrr){
        Boolean check=directMessageService.updateReliability(dmrr);
        Result result=new Result();
        if(check==true){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

    @PostMapping("/report")
    public Result Report(@RequestBody DirectMessageRequestReport dmrr){
         Report report=directMessageService.report(dmrr);
         Result result=new Result();
         if(report!=null){
             result.setResult(true);
         }else{
             result.setResult(false);
         }
         return result;
    }

}
