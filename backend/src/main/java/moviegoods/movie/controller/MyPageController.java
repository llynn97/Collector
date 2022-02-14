package moviegoods.movie.controller;


import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.informationShare.Result;
import moviegoods.movie.domain.dto.mypage.*;
import moviegoods.movie.service.MyPageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {


    private final MyPageService myPageService;

    @GetMapping
    public MyPageResponseSearch searchMyPage(@ModelAttribute MyPageRequestSearch mprs){

        MyPageResponseSearch myPageResponseSearch=myPageService.search(mprs);

        return myPageResponseSearch;
    }

    @PatchMapping("/profile")
    public Result updateProfile(@ModelAttribute MyPageRequestProfile mprp) throws IOException, FirebaseAuthException {

        myPageService.updateProfile(mprp);
        Result result=new Result();
        result.setResult(true);
        return result;

    }

    @PatchMapping("/nickname")
    public Result updateNickname(@ModelAttribute MyPageRequestNickname mrnn){
        Result result=new Result();
        String name=myPageService.updateNickname(mrnn);
        if(mrnn.getNickname().equals(name)){
            result.setResult(true);
        }
        else{
            result.setResult(false);
        }
        return result;

    }

    @DeleteMapping("/withdrawal")
    public Result withdrawal(@ModelAttribute MyPageRequestWithdrawal mrwd){
        Boolean check=   myPageService.withdrawal(mrwd);
        Result result=new Result();
        if(check==true){
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;

    }
}
