package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1000;
        long phone = 12345678921L;
        String md5 = "2b432be62304cb7e17e93114cc83b968";
        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", execution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 整个秒杀流程测试
     * @throws Exception
     */
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 12345678921L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        }else {
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }

    }

    @Test
    public void testExecuteSeckillProcedure() {
        long seckillId = 1000;
        long phone = 11111111111L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(execution.getStateInfo());
        }
    }
}