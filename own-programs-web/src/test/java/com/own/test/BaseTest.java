package com.own.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.own.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class,webEnvironment=WebEnvironment.RANDOM_PORT)
@Rollback
//@SpringApplicationConfiguration(Application.class)
//@WebIntegrationTest({ "server.port=4004" })
//@TransactionConfiguration(defaultRollback=true)
public class BaseTest {

}
