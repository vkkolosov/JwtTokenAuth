package vk.kolosov.insidejwt.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vk.kolosov.insidejwt.model.UserInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LocalServiceTest {

    @Autowired
    private LocalService localService;

    @Test
    void findUserInfoByName() {

        //given
        String expectedResult = "test";
        //run
        String result = localService.findUserInfoByName(expectedResult).getName();

        //assert
        Assert.assertEquals(expectedResult, result);
    }

}