package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() {
        long id = 1000L;
        long phone = 18710990178L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount"+insertCount);
    }

    @Test
    public void testQueryByIdWitnSeckill() {
        long id = 1001L;
        long phone = 18710990178L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        if (successKilled!=null){
            System.out.println(successKilled.getSeckill());
        }
    }
}