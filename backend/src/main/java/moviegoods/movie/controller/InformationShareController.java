package moviegoods.movie.controller;


import com.google.firebase.auth.FirebaseAuthException;
import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import moviegoods.movie.domain.dto.informationShare.*;
import moviegoods.movie.domain.entity.Comment.Comment;
import moviegoods.movie.domain.entity.Post.Post;
import moviegoods.movie.service.FireBaseService;
import moviegoods.movie.service.InformationShareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/information-share")
public class InformationShareController {

    private final InformationShareService informationShareService;
    private final FireBaseService fireBaseService;

    @PostMapping("/write")
    public Result write(@Validated @ModelAttribute InformationShareRequestWrite isrw, BindingResult bindingResult) throws IOException, FirebaseAuthException {

        log.info("user_id={}",isrw.getUser_id());
        log.info("post={}",isrw.getCinema_area());
        log.info("mulrifile={}",isrw.getImage_url());

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


}
