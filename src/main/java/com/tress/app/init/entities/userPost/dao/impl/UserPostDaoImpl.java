package com.tress.app.init.entities.userPost.dao.impl;

import com.tress.app.init.entities.userPost.dao.UserPostDao;
import com.tress.app.init.entities.userPost.report.UserPost;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserPostDaoImpl implements UserPostDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<UserPost> findAll() {

        StringBuilder strQuery = new StringBuilder()
            .append("  SELECT ")
            .append("    user.user_id AS userId, ")
            .append("    user.active AS active, ")
            .append("    user.email AS email, ")
            .append("    user.last_name AS lastName, ")
            .append("    user.username AS userName, ")
            .append("    user.profession AS profession, ")
            .append("    user.avatar_url AS avatarUrl, ")
            .append("    post.post_id AS postId, ")
            .append("    post.body AS body, ")
            .append("    post.created_date AS createdDate, ")
            .append("    post.url_image AS urlImage ")
            .append("    FROM user user ")
            .append("      INNER JOIN user_post userP ON userP.user_id = user.user_id ")
            .append("      INNER JOIN post post ON userP.post_id = post.post_id ");

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(strQuery.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        query.addScalar("userId", StandardBasicTypes.LONG);
        query.addScalar("active", StandardBasicTypes.LONG);
        query.addScalar("email", StandardBasicTypes.STRING);
        query.addScalar("lastName", StandardBasicTypes.STRING);
        query.addScalar("username", StandardBasicTypes.STRING);
        query.addScalar("profession", StandardBasicTypes.STRING);
        query.addScalar("avatarUrl", StandardBasicTypes.STRING);
        query.addScalar("postId", StandardBasicTypes.LONG);
        query.addScalar("body", StandardBasicTypes.STRING);
        query.addScalar("createdDate", StandardBasicTypes.DATE);
        query.addScalar("urlImage", StandardBasicTypes.STRING);

        return query.list();
    }
}
