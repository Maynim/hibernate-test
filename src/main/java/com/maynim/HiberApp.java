package com.maynim;



import com.maynim.entity.User;
import com.maynim.entity.UserChat;
import com.maynim.util.HibernateUtil;
import com.maynim.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Map;

@Slf4j
public class HiberApp {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                RootGraph<User> userGraph = session.createEntityGraph(User.class);
                userGraph.addAttributeNodes("company", "userChats");
                SubGraph<UserChat> userChatsSubgraph = userGraph.addSubgraph("userChats", UserChat.class);
                userChatsSubgraph.addAttributeNodes("chat");

//                RootGraph<?> graph = session.getEntityGraph("WithCompanyAndChat");
//                Map<String, Object> properties = Map.of(
//                        GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("WithCompanyAndChat")
//                );
//                User user = session.find(User.class, 1L, properties);

//                System.out.println(user.getCompany().getName());
//                System.out.println(user.getUserChats().size());
                List<User> users = session.createQuery("SELECT u FROM User u", User.class)
                        .setHint(GraphSemantic.LOAD.getJpaHintName(), userGraph)
                        .list();

                users.forEach(it -> System.out.println(it.getCompany().getName()));
                users.forEach(it -> System.out.println(it.getUserChats().size()));

                session.getTransaction().commit();
            }
        }
    }
}
