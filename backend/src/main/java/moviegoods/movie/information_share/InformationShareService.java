package moviegoods.movie.information_share;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.entity.Cinema;
import moviegoods.movie.domain.entity.Comment;
import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import moviegoods.movie.domain.entity.Post;
import moviegoods.movie.domain.entity.User.User;
import moviegoods.movie.information_share.InformationRepository.*;
import moviegoods.movie.information_share.repsonse.Comments;
import moviegoods.movie.information_share.repsonse.InformationShareResponseDetail;
import moviegoods.movie.information_share.repsonse.InformationShareResponseSearch;
import moviegoods.movie.information_share.request.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InformationShareService {

    // private final InformationShareRepository informationShareRepository;
    private final InformationShareCinemaRepository informationShareCinemaRepository;
    private final InformationShareUserRepository informationShareUserRepository;
    private final InformationShareContent_detailRepository informationShareContent_detailRepository;
    private final InformationSharePostRepository informationSharePostRepository;
    private final InformationShareCommentRepository informationShareCommentRepository;
    private final EntityManager em;

    public List<Post> savePost(InformationShareRequestWrite isrw) {

        Long user_id = isrw.getUser_id();
        String title = isrw.getTitle();
        String cinema_name = isrw.getCinema_name();
        String cinema_area = isrw.getCinema_area();
        String cinema_branch = isrw.getCinema_branch();
        String content = isrw.getContent();
        String image_url = isrw.getImage_url();
        User user1 = new User();
        user1.setNickname("현정");
        informationShareUserRepository.save(user1);
        User user2 = new User();
        user2.setNickname("민지");
        informationShareUserRepository.save(user2);
        User user3 = new User();
        user3.setNickname("지영");
        informationShareUserRepository.save(user3);

        Optional<User> OptionalUser = informationShareUserRepository.findById(user_id);
        if (OptionalUser.isPresent()) {
            User user = OptionalUser.get();
            Cinema cinema = new Cinema();
            cinema.setName(cinema_name);
            cinema.setBranch(cinema_branch);
            cinema.setArea(cinema_area);
            Content_Detail content_detail = saveContent_detail(content);
            Post post = new Post();
            post.setImage_url(image_url);
            post.setTitle(title);
            post.setViews(0L);
            post.setCategory("정보공유");
            post.setUser(user);
            //user.setPost(post);
            post.setContent_detail(content_detail);
            content_detail.setPost(post);
            post.setCinema(cinema);
            cinema.setPost(post);

            informationSharePostRepository.save(post);
            informationShareCinemaRepository.save(cinema);
            informationShareContent_detailRepository.save(content_detail);
            List<Post> postList = informationSharePostRepository.findAll();

            return postList;
        }
        return new ArrayList<>();

    }

    public Content_Detail saveContent_detail(String content) {

        Content_Detail content_detail = new Content_Detail();

        content_detail.setContent(content);
        content_detail.setWritten_date(LocalDateTime.now());

        return content_detail;
    }

    public void saveUser(User user) {

        informationShareUserRepository.save(user);

    }

    public InformationShareResponseSearch makeInformationShareResponseSearch(String nickname, String title, Long views,
            Long post_id, LocalDateTime written_date) {
        InformationShareResponseSearch informationShareResponseSearch = new InformationShareResponseSearch();
        informationShareResponseSearch.setNickname(nickname);
        informationShareResponseSearch.setPost_id(post_id);
        informationShareResponseSearch.setTitle(title);
        informationShareResponseSearch.setView(views);
        informationShareResponseSearch.setWritten_date(written_date);
        return informationShareResponseSearch;
    }

    public List<InformationShareResponseSearch> Search(InformationShareRequestSearch isrs) {
        List<InformationShareResponseSearch> searchList = new ArrayList<>();
        String area = isrs.getCinema_area();
        String branch = isrs.getCinema_branch();
        String name = isrs.getCinema_name();
        String search_word = isrs.getSearch_word();
        String sort_name = isrs.getSort_name(); // 제목 / 제목+내용 / 내용 / 작성자
        if (!search_word.equals("")) {
            String searchJpql1 = "select p From post p join p.content_detail c ";
            if (sort_name.equals("제목")) {
                searchJpql1 = searchJpql1 + "where p.title like '%" + search_word + "%'";
            } else if (sort_name.equals("제목+내용")) {
                searchJpql1 = searchJpql1 + "where (c.content like '%" + search_word + "%') " + "OR (p.title like '%"
                        + search_word + "%')";

            } else if (sort_name.equals("내용")) {
                searchJpql1 = searchJpql1 + "where c.content like '%" + search_word + "%'";

            } else if (sort_name.equals("작성자")) {
                String searchJpql2 = "select p From post p join p.user u where u.nickname like '%" + search_word + "%'";
                List<Post> postList = em.createQuery(searchJpql2, Post.class).getResultList();
                for (Post post : postList) {
                    Long content_detail_id = post.getContent_detail().getContent_detail_id();
                    Content_Detail content_detail = informationShareContent_detailRepository.findById(content_detail_id)
                            .get();

                    String nickname = post.getUser().getNickname();
                    String title = post.getTitle();
                    Long views = post.getViews();
                    Long post_id = post.getPost_id();
                    LocalDateTime written_date = content_detail.getWritten_date();
                    searchList.add(makeInformationShareResponseSearch(nickname, title, views, post_id, written_date));

                }
                return searchList;
            }

            List<Post> postList = em.createQuery(searchJpql1, Post.class).getResultList();
            for (Post post : postList) {
                Long user_id = post.getUser().getUser_id();
                User user = informationShareUserRepository.findById(user_id).get();
                String nickname = user.getNickname();
                String title = post.getTitle();
                Long views = post.getViews();
                Long post_id = post.getPost_id();
                LocalDateTime written_date = post.getContent_detail().getWritten_date();
                searchList.add(makeInformationShareResponseSearch(nickname, title, views, post_id, written_date));

            }

            return searchList;

        }
        if (area.equals("") && branch.equals("") && name.equals("")) {
            String dateJpql = "select p FROM post p join p.content_detail c order by c.datetime DESC";
            List<Post> postList = em.createQuery(dateJpql, Post.class).getResultList();
            for (Post post : postList) {
                Long user_id = post.getUser().getUser_id();
                User user = informationShareUserRepository.findById(user_id).get();
                String nickname = user.getNickname();
                String title = post.getTitle();
                Long views = post.getViews();
                Long post_id = post.getPost_id();
                LocalDateTime written_date = post.getContent_detail().getWritten_date();
                searchList.add(makeInformationShareResponseSearch(nickname, title, views, post_id, written_date));

            }

            return searchList;

        }
        Boolean check = false;
        String filterJpql = "select p from post p join p.cinema c where ";
        if (!area.equals("")) {
            filterJpql += "c.area= '" + area + "'";
            check = true;
        }
        if (!branch.equals("")) {
            if (check == true) {
                filterJpql += "AND c.branch= '" + branch + "'";
            } else {
                filterJpql += "c.branch= '" + branch + "'";

            }
            check = true;
        }
        if (!name.equals("")) {
            if (check == true) {
                filterJpql += "AND c.name= '" + name + "'";
            } else {
                filterJpql += "c.name= '" + name + "'";
            }
        }

        List<Post> postList = em.createQuery(filterJpql, Post.class).getResultList();
        for (Post post : postList) {
            String nickname = informationShareUserRepository.findById(post.getUser().getUser_id()).get().getNickname();
            String title = post.getTitle();
            Long views = post.getViews();
            Long post_id = post.getPost_id();
            LocalDateTime written_date = informationShareContent_detailRepository
                    .findById(post.getContent_detail().getContent_detail_id()).get().getWritten_date();
            searchList.add(makeInformationShareResponseSearch(nickname, title, views, post_id, written_date));

        }

        return searchList;

    }

    // 상세조회
    public InformationShareResponseDetail detailInfo(InformationShareRequestDetail isrd) {
        Long post_id = isrd.getPost_id();
        Long user_id = isrd.getUser_id();
        User user = informationShareUserRepository.findById(user_id).get();

        // String jpql="select p From post p join p.content_detail c where
        // p.post_id="+post_id;

        List<Object[]> row = em.createQuery("select u.user_id, u.nickname, p.views,p.title,p.image_url,d.written_date,"
                + " d.content,c.name,c.area,c.branch from post p join p.content_detail d left join p.cinema c left join p.user u where p.post_id=:post_id ")
                .setParameter("post_id", post_id).getResultList();
        Object[] result = row.get(0);
        InformationShareDetailDTO ifsd = new InformationShareDetailDTO((Long) result[0], (String) result[1],
                (Long) result[2], (String) result[3], (String) result[4], (LocalDateTime) result[5], (String) result[6],
                (String) result[7], (String) result[8], (String) result[9]);

        InformationShareResponseDetail informationShareResponseDetail = new InformationShareResponseDetail();

        List<Object[]> row2 = em.createQuery(
                "select u.user_id, u.nickname, d.content, d.written_date from comment c join c.post p left join c.user u left join c.content_detail d where p.post_id=:post_id")
                .setParameter("post_id", post_id).getResultList();
        List<Comments> result2 = new ArrayList<>();
        for (Object[] objects : row2) {
            Boolean check = false;
            Comments comment = new Comments((Long) objects[0], (String) objects[1], (String) objects[2],
                    (LocalDateTime) objects[3]);
            if (user_id == (Long) objects[0]) {
                check = true;
            }
            comment.setIs_mine(check);
            result2.add(comment);
        }

        if (ifsd.getUser_id() == user_id) {
            informationShareResponseDetail.setIs_mine(true);
        } else {
            informationShareResponseDetail.setIs_mine(false);
        }
        informationShareResponseDetail.setCinema_area(ifsd.getArea());
        informationShareResponseDetail.setCinema_branch(ifsd.getBranch());
        informationShareResponseDetail.setCinema_name(ifsd.getName());
        informationShareResponseDetail.setContent(ifsd.getContent());
        informationShareResponseDetail.setImage_url(ifsd.getImage_url());
        informationShareResponseDetail.setNickname(ifsd.getNickname());
        informationShareResponseDetail.setPost_id(post_id);
        informationShareResponseDetail.setViews(ifsd.getViews());
        informationShareResponseDetail.setWritten_date(ifsd.getWritten_date());
        informationShareResponseDetail.setTitle(ifsd.getTitle());
        informationShareResponseDetail.setComment(result2);

        return informationShareResponseDetail;

    }

    public Comment saveComment(InformationShareRequestSaveComment isrsc) {
        Long user_id = isrsc.getUser_id();
        Long post_id = isrsc.getPost_id();
        String content = isrsc.getContent();
        User user = informationShareUserRepository.findById(user_id).get();
        Content_Detail content_detail = new Content_Detail();
        content_detail.setWritten_date(LocalDateTime.now());
        content_detail.setContent(content);
        Post post = informationSharePostRepository.findById(post_id).get();
        Comment comment = new Comment();

        comment.setUser(user);
        user.getComments().add(comment);
        comment.setContent_detail(content_detail);
        content_detail.setComment(comment);
        comment.setPost(post);
        post.getComments().add(comment);
        Comment comment1 = informationShareCommentRepository.save(comment);
        informationShareContent_detailRepository.save(content_detail);

        Comment result = informationShareCommentRepository.findById(comment1.getComment_id()).get();

        return result;

    }

    public Boolean deleteDetail(InformationShareRequestDeleteDetail isrdd) {

        Long user_id = isrdd.getUser_id();
        Long post_id = isrdd.getPost_id();

        informationSharePostRepository.deleteById(post_id);

        Boolean result = informationSharePostRepository.existsById(post_id);
        return result;
    }

    public Boolean deleteComment(InformationShareRequestDeleteComment isrdc) {
        Long user_id = isrdc.getUser_id();
        Long comment_id = isrdc.getComment_id();

        informationShareCommentRepository.deleteById(comment_id);
        Boolean result = informationShareCommentRepository.existsById(comment_id);
        return result;
    }

}
