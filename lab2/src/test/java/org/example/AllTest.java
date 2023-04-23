package org.example;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.database.dao.hibernate.StreetDaoHibernate;
import org.example.database.dao.jdbc.StreetDaoJdbc;
import org.example.database.dao.mybatis.StreetMapper;
import org.example.database.entity.Street;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

public class AllTest {
    @Mock
    private SqlSessionFactory sqlSessionFactory;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private StreetMapper streetMapper;

    @Mock
    private Session session;

    @Mock
    private SqlSession sqlSession;

    @Mock
    private StreetDaoHibernate streetDaoHibernate;

    @Mock
    private StreetDaoJdbc streetDaoJdbc;

    @Mock
    List<Street> streetMapperAll;

    @Mock
    List<Street> streetJdbcAll;

    @Mock
    List<Street> streetHibernateAll;

    private List<Street> streets;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        streets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            streets.add(Street.builder()
                    .name("street" + i)
                    .postId(i)
                    .build());
        }
    }

    @Test
    @Order(10)
    void saveOneHundredEntitiesByThreeWays() {
        long myBatisBegin = System.currentTimeMillis();
        doReturn(sqlSession).when(sqlSessionFactory).openSession();
        doReturn(streetMapper).when(sqlSession).getMapper(StreetMapper.class);
        for (int i = 0; i < 100; i++) {
            streets.get(i).setName("streetMyBatis" + i);
            streetMapper.save(streets.get(i));
        }
        long myBatisFinish = System.currentTimeMillis();
        System.out.println("MyBatis save time: " + (myBatisFinish - myBatisBegin));

        long jdbcBegin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            streets.get(i).setName("streetJDBC" + i);
            streetDaoJdbc.save(streets.get(i));
        }
        long jdbcFinish = System.currentTimeMillis();
        System.out.println("JDBC save time: " + (jdbcFinish - jdbcBegin));

        long hibernateBegin = System.currentTimeMillis();
        doReturn(session).when(sessionFactory).openSession();
        for (int i = 0; i < 100; i++) {
            streets.get(i).setName("streetHibernate" + i);
            streetDaoHibernate.save(streets.get(i));
        }
        long hibernateFinish = System.currentTimeMillis();
        System.out.println("Hibernate save time: " + (hibernateFinish - hibernateBegin));
    }

    @Test
    @Order(1)
    void getOneHundredEntitiesByThreeWays() {
        long myBatisBegin = System.currentTimeMillis();
        doReturn(sqlSession).when(sqlSessionFactory).openSession();
        doReturn(streetMapper).when(sqlSession).getMapper(StreetMapper.class);
        doReturn(streetMapperAll).when(streetMapper).getAll();
        doReturn(300).when(streetMapperAll).size();
        long myBatisFinish = System.currentTimeMillis();
        assertEquals(300, streetMapperAll.size());
        System.out.println("MyBatis getAll time: " + (myBatisFinish - myBatisBegin));

        long jdbcBegin = System.currentTimeMillis();
        doReturn(streetJdbcAll).when(streetDaoJdbc).getAll();
        doReturn(300).when(streetJdbcAll).size();
        long jdbcFinish = System.currentTimeMillis();
        assertEquals(300, streetJdbcAll.size());
        System.out.println("JDBC getAll time: " + (jdbcFinish - jdbcBegin));

        long hibernateBegin = System.currentTimeMillis();
        doReturn(session).when(sessionFactory).openSession();
        doReturn(streetHibernateAll).when(streetDaoHibernate).getAll();
        doReturn(300).when(streetHibernateAll).size();
        long hibernateFinish = System.currentTimeMillis();
        assertEquals(300, streetHibernateAll.size());
        System.out.println("Hibernate getAll time: " + (hibernateFinish - hibernateBegin));
    }
}