package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Service
@Slf4j
public class LikeBasketsService {
    private final EntityManager em;

    public Boolean isLikeEvent(Long user_id, Long event_id) {
        Boolean is_like = Boolean.FALSE;

        String likeJpql = "select count(l) from like_basket l where event_id = :event_id and user_id = :user_id";
        Object object = em.createQuery(likeJpql).setParameter("event_id", event_id)
                .setParameter("user_id", user_id).setMaxResults(1).getSingleResult();

        if (Integer.parseInt(object.toString()) == 1) {
            is_like = Boolean.TRUE;
        }
        return is_like;
    }

    public Boolean isLikeTransaction(Long user_id, Long transaction_id) {
        String likeJpql = "select count(l) from like_basket l where transaction_id = :transaction_id and user_id = :user_id";
        Object object = em.createQuery(likeJpql).setParameter("transaction_id", transaction_id)
                .setParameter("user_id",user_id).setMaxResults(1).getSingleResult();
        Boolean is_like = Boolean.FALSE;
        if (Integer.parseInt(object.toString()) == 1) {
            is_like = Boolean.TRUE;
        }
        return is_like;

    }
}
