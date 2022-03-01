package moviegoods.movie.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.mypage.*;
import moviegoods.movie.service.MyPageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        public Result updateProfile(@RequestParam(value="file",required = false)MultipartFile file,
                                    @RequestParam(value="user_id",required = false)String id)throws Exception{
            Long user_id=Long.valueOf(id);

            MyPageRequestProfile mprp=new MyPageRequestProfile();
            mprp.setProfile_image(file);
            mprp.setUser_id(user_id);
            myPageService.updateProfile(mprp);
            Result result=new Result();
            result.setResult(true);
            return result;
        }

        @PatchMapping("/nickname")
        public Result updateNickname(@RequestBody MyPageRequestNickname mrnn){
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
        public Result withdrawal(@RequestBody MyPageRequestWithdrawal mrwd){
            Boolean check=   myPageService.withdrawal(mrwd);
            Result result=new Result();
            if(check==true){
                result.setResult(true);
            }else{
                result.setResult(false);
            }
            return result;

        }

        @PostMapping("/duplicate-check")
        public Result nicknameDuplicate(@RequestBody MyPageRequestNicknameDuplicateDto mprnd){
            Result result= myPageService.nicknameDuplicate(mprnd);
            return result;

        }
    }

