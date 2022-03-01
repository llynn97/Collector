package moviegoods.movie.service;


import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import moviegoods.movie.domain.dto.booleanResult.ResultResponseDto;
import moviegoods.movie.domain.dto.mypage.*;
import moviegoods.movie.domain.entity.Transaction.Status;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.domain.entity.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final EntityManager em;
    private final UserRepository userRepository;
    private final FireBaseService fireBaseService;

    public MyPageResponseSearch search(MyPageRequestSearch mprs){
        MyPageResponseSearch myPageResponseSearch=new MyPageResponseSearch();

        Long user_id= mprs.getUser_id();
        User user=userRepository.findById(user_id).get();
        MyPageUser myPageUserDto=new MyPageUser();
        myPageUserDto.setNickname(user.getNickname());
        myPageUserDto.setProfile_url(user.getProfile_url());
        myPageUserDto.setReliability(user.getReliability());
        myPageResponseSearch.getUser().add(myPageUserDto);


        List<Object[]> row= em.createQuery("select c.comment_id , d.content, d.written_date,p.category,p.post_id from comment c join c.user u left join c.content_detail d left join c.post p where u.user_id =:user_id order by d.written_date DESC").setParameter("user_id",user_id).getResultList();

        //작성 댓글
        for (Object[] objects : row) {

            Long  comment_id=(Long)objects[0];
            String comment_content=(String)objects[1];
            LocalDateTime written_date=(LocalDateTime)objects[2];
            String category=(String)objects[3];
            Long post_id=(Long)objects[4];
            myPageResponseSearch.getComment().add(new MyPageComment(comment_id,comment_content,written_date,category,post_id));


        }

        List<Object[]> row1=  em.createQuery("select p.post_id, p.title, c.written_date , p.category, c.content from post p join p.content_detail c left join p.user u where u.user_id =:user_id order by c.written_date DESC").setParameter("user_id",user_id).getResultList();


        for (Object[] objects : row1) {
            Long post_id=(Long)objects[0];
            String title=(String)objects[1];
            LocalDateTime written_date=(LocalDateTime)objects[2];
            String category=(String)objects[3];
            String content=(String)objects[4];

            myPageResponseSearch.getContent().add(new MyPageContent(post_id,title,written_date,category,content));
        }
        List<Object[]> row2=  em.createQuery("select t.transaction_id , c.content , c.written_date , u.nickname ,u.reliability,u.user_id,t.status from transaction t join t.content_detail c left join t.user u where u.user_id =:user_id order by c.written_date DESC").setParameter("user_id",user_id).getResultList();


        for (Object[] objects : row2) {
            Boolean is_mine=false;
            Long transaction_id=(Long)objects[0];
            String content=(String)objects[1];
            LocalDateTime written_date=(LocalDateTime)objects[2];
            String nickname=(String)objects[3];
            Long reliability=(Long)objects[4];
            Long user_id2=(Long)objects[5];
            if(user_id==user_id2){
                is_mine=true;
            }

            Status status=(Status)objects[6];

            myPageResponseSearch.getWriteTransaction().add(new MyPageTransaction(transaction_id,content,written_date,nickname,reliability,is_mine,status));
        }

        List<Object[]> row3=  em.createQuery("select t.transaction_id ,u.nickname,u.reliability,u.user_id from like_basket b join b.user u left join b.transaction t where u.user_id =:user_id ").setParameter("user_id",user_id).getResultList();

        for (Object[] objects : row3) {
            Boolean is_mine=false;
            Long transaction_id=(Long)objects[0];
            String nickname=(String)objects[1];
            Long reliability=(Long)objects[2];
            Long user_id2=(Long)objects[3];
            if(user_id==user_id2){
                is_mine=true;
            }
            List<Object[]> row4=  em.createQuery("select c.content, c.written_date ,t.status from transaction t join t.content_detail c where t.transaction_id =:transaction_id").setParameter("transaction_id",transaction_id).getResultList();
            if(row4.size()!=0){
                Object[] result4=row4.get(0);
                String content=(String)result4[0];

                LocalDateTime written_date=(LocalDateTime)result4[1];
                Status status=(Status)result4[2];
                myPageResponseSearch.getLikeTransaction().add(new MyPageTransaction(transaction_id,content,written_date,nickname,reliability,is_mine,status));
            }
        }







        List<Object[]> row5= em.createQuery("select e.event_id, e.title, e.thumbnail_url from like_basket b join b.event e left join b.user u where u.user_id=:user_id").setParameter("user_id",user_id).getResultList();
        for (Object[] objects : row5) {
            Long event_id=(Long)objects[0];
            String event_title=(String)objects[1];
            String thumbnail_url=(String)objects[2];
            myPageResponseSearch.getLikeEvent().add(new MyPageLikeEvent(event_id,event_title,thumbnail_url));
        }



        return myPageResponseSearch;














    }

    public String updateNickname(MyPageRequestNickname mprn){
        Long user_id=mprn.getUser_id();
        String nickname=mprn.getNickname();
        User user=userRepository.findById(user_id).get();
        user.setNickname(nickname);
        userRepository.save(user);
        return userRepository.findById(user_id).get().getNickname();
    }

    public void updateProfile(MyPageRequestProfile mprp) throws IOException, FirebaseAuthException {
        MultipartFile profile_image=mprp.getProfile_image();
        Long user_id=mprp.getUser_id();
        String firebaseUrl="";
        if(profile_image!=null){
            String nameFile= UUID.randomUUID().toString();
            fireBaseService.uploadFiles(profile_image,nameFile);
            firebaseUrl+="https://firebasestorage.googleapis.com/v0/b/stroagetest-f0778.appspot.com/o/"+nameFile+"?alt=media";

        }
        User user=userRepository.findById(user_id).get();
        user.setProfile_url(firebaseUrl);
        userRepository.save(user);

    }

    public Boolean withdrawal(MyPageRequestWithdrawal mrwd){
        Boolean check=false;
        Long user_id=mrwd.getUser_id();
        userRepository.deleteById(user_id);
        if(!userRepository.existsById(user_id)){
            check=true;
        }
        return check;
    }

    public Result nicknameDuplicate(MyPageRequestNicknameDuplicateDto mpnd){

        String nickname = mpnd.getNickname();
        User user = new User();

        if (nickname != null) {
            user = userRepository.findByNickname(nickname).orElse(null);
        }
        Result result = new Result();
        if (user == null) {
            result.setResult(false);
        }
        else {
            result.setResult(true);
        }

        return result;
    }


}
