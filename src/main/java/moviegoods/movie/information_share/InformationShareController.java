package moviegoods.movie.information_share;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.information_share.domain.Comment;
import moviegoods.movie.information_share.domain.Post;
import moviegoods.movie.information_share.repsonse.InformationShareResponseDetail;
import moviegoods.movie.information_share.repsonse.InformationShareResponseSearch;
import moviegoods.movie.information_share.repsonse.Result;
import moviegoods.movie.information_share.request.*;
import org.hibernate.annotations.OnDelete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/information-share")
public class InformationShareController {

  // private final InformationShareRepository informationShareRepository;
   private final InformationShareService informationShareService;

   @PostMapping("/write")
   public Result write(@Validated @RequestBody InformationShareRequestWrite isrw, BindingResult bindingResult){

        log.info("user_id={}",isrw.getUser_id());
        log.info("post={}",isrw.getCinema_area());
        if(bindingResult.hasErrors()){
           Result result=new Result();
           result.setResult(false);
            return result;
        }
       //informationShareRepository.savePost(isrw);
        List<Post> postList=informationShareService.savePost(isrw);
       for (Post post : postList) {
           log.info("저장된post_title={}",post.getTitle());
           log.info("저장된 post_id={}",post.getPost_id());
           log.info("저장된 post_user_id={}",post.getUser().getUser_id());
       }


       Result result=new Result();
       result.setResult(true);
       return result;

   }

   @GetMapping("/search")
    public ResponseEntity<List<InformationShareResponseSearch>> search(@ModelAttribute InformationShareRequestSearch isrs){
           log.info("irsrs={}",isrs.getSearch_word());
       List<InformationShareResponseSearch> list=    informationShareService.Search(isrs);
         ResponseEntity<List<InformationShareResponseSearch>> entity=new ResponseEntity<>(list, HttpStatus.OK);
          return  entity;




   }

   @GetMapping("/detail")
    public InformationShareResponseDetail detail(@ModelAttribute InformationShareRequestDetail isrd){
        InformationShareResponseDetail ifsr=informationShareService.detailInfo(isrd);
        return ifsr;
   }

   @PostMapping("/comment")
    public Result saveComment(@RequestBody InformationShareRequestSaveComment isrsc){
        Comment comment=informationShareService.saveComment(isrsc);
        log.info("comment_id=={}",comment.getComment_id());
        Result result=new Result();
        if(comment!=null){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
   }

   @DeleteMapping("/detail")
    public Result deleteDetail(@ModelAttribute InformationShareRequestDeleteDetail isrdd){
         Boolean check=informationShareService.deleteDetail(isrdd);
         Result result=new Result();
         if(check==false){
             result.setResult(true);
         }else{
             result.setResult(false);
         }
         return result;
   }

   @DeleteMapping("/comment")
    public Result deleteComment(@ModelAttribute InformationShareRequestDeleteComment isrdc){
       Boolean check=informationShareService.deleteComment(isrdc);
       Result result=new Result();
       if(check==false){
           result.setResult(true);
       }else{
           result.setResult(false);
       }
       return result;

   }

   /*
    {

    "title":"ㅎㅎ",
     "cinema_name":"ㅇㅇ",
     "cinema_area":"ㅎㅇ",

    "cinema_branch":"ㅇㅇ",
    "image_url":"ㅎㅎ",
   "user_id":3,
   "content":"그래서 온르은"

  {


   "user_id":2,
   "post_id":1,
   "content":"ㅋㅋㅋ"


}
}
    */



}
