package com.manymobi.esdsl.mapper;

import com.alibaba.fastjson.JSONObject;
import com.manymobi.esdsl.Esdsl;
import com.manymobi.esdsl.handler.Cancellable;
import com.manymobi.esdsl.handler.Request;
import com.manymobi.esdsl.handler.Response;
import com.manymobi.esdsl.handler.ResponseListener;
import com.manymobi.esdsl.handler.RestHandler;
import com.manymobi.esdsl.handler.impl.JacksonJsonEncoder;
import com.manymobi.esdsl.handler.impl.PathEsdslFileResourceHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author 梁建军
 * 创建日期： 2021/4/18
 * 创建时间： 上午 11:28
 * @version 1.0
 * @since 1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FutureTestMapperTest {
    protected Esdsl esdsl;


    @BeforeAll
    public void init() {
        Esdsl.Build build = new Esdsl.Build();
        build.setEsdslFileResourceHandler(new PathEsdslFileResourceHandler(new File("src/test/resources/")));
        build.setRestHandler(new RestHandler() {
            @Override
            public Response performRequest(Request request) {
                return new Response(200, request.getJson());
            }

            @Override
            public Cancellable performRequestAsync(Request request, ResponseListener responseListener) {

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                        }
                        try {
                            responseListener.onSuccess(new Response(200, request.getJson()));
                        } catch (Exception e) {
                            responseListener.onFailure(e);
                        }
                    }
                }.start();

                return () -> System.out.println("取消");
            }

            @Override
            public void close() throws Exception {

            }
        });
        build.setJsonEncoder(new JacksonJsonEncoder());
        esdsl = build.build();
    }

    @Test
    void search1() throws ExecutionException, InterruptedException {
        FutureTestMapper target = esdsl.target(FutureTestMapper.class);


        CompletableFuture<JSONObject> jsonObject1 = target.search1("1", "2");


        JSONObject jsonObject = jsonObject1.get();
        System.out.println(jsonObject);
    }

    @Test
    void search2() {
        FutureTestMapper target = esdsl.target(FutureTestMapper.class);


        CompletableFuture<JSONObject> jsonObjectCompletableFuture = target.search1("1", "2");
        assertThrows(TimeoutException.class, () -> {
            JSONObject jsonObject = jsonObjectCompletableFuture.get(2, TimeUnit.SECONDS);
        });

    }

}
