//package com.xy.elasticsearch.core.parse;
//
//import com.alibaba.fastjson.JSONObject;
//import com.xy.elasticsearch.core.model.Demo;
//import com.xy.elasticsearch.core.repository.DemoRepository;
//import com.xy.elasticsearch.core.wrapper.Wrappers;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author zoubo
// * @version 1.0.0
// * @date 2019/12/29 21:29
// */
//class EntityMappingResolveTest {
//
//    private RestHighLevelClient client;
//
//    @BeforeEach
//    void before(){
//        EntityIndexParse resolve = new EntityIndexParse(Demo.class);
//    }
//
//    @Test
//    void createIndexRequest() throws IOException {
//        EntityIndexParse resolve = new EntityIndexParse(Demo.class);
////        CreateIndexRequest indexRequest = resolve.createIndexRequest();
////        client.indices().create(indexRequest);
//    }
//
//    @Test
//    void insert(){
//        Demo demo = new Demo();
//        demo.setId("1");
//        demo.setName("杰森");
//        demo.setAge(12);
//        demo.setSex("男");
//        demo.setAddress("东莞市");
//        demo.setCode("stu0007");
//        demo.setLocation(Arrays.asList(113.3311737800d, 23.1480209800));
//        RepositoryProxyFactory<DemoRepository> repositoryProxyFactory = new RepositoryProxyFactory<>(DemoRepository.class);
//        DemoRepository demoRepository = repositoryProxyFactory.newInstance();
//        demoRepository.insert(demo);
//
//    }
//
//    @Test
//    void selectById(){
//        String id = "1";
//        RepositoryProxyFactory<DemoRepository> repositoryProxyFactory = new RepositoryProxyFactory<>(DemoRepository.class);
//        DemoRepository demoRepository = repositoryProxyFactory.newInstance();
//        Demo demo = demoRepository.selectById(id);
//        System.out.println(JSONObject.toJSONString(demo));
//    }
//
//
//    @Test
//    void selectByIds(){
//        RepositoryProxyFactory<DemoRepository> repositoryProxyFactory = new RepositoryProxyFactory<>(DemoRepository.class);
//        DemoRepository demoRepository = repositoryProxyFactory.newInstance();
//        List<Demo> demos = demoRepository.selectByIds(Arrays.asList("1", "store_1"));
//        System.out.println(JSONObject.toJSONString(demos));
//    }
//
//
//    /**
//     * 打分
//     */
//    @Test
//    void selectListScore(){
//        RepositoryProxyFactory<DemoRepository> repositoryProxyFactory = new RepositoryProxyFactory<>(DemoRepository.class);
//        DemoRepository demoRepository = repositoryProxyFactory.newInstance();
//        List<Demo> list = demoRepository.selectList(Wrappers.<Demo>complexQuery()
//                .term(Demo::getAddress, "东莞市")
//                .term(Demo::getAge, 10));
//        System.out.println(list);
//    }
//
//
//    @Test
//    void selectList(){
//        RepositoryProxyFactory<DemoRepository> repositoryProxyFactory = new RepositoryProxyFactory<>(DemoRepository.class);
//        DemoRepository demoRepository = repositoryProxyFactory.newInstance();
//        List<Demo> list = demoRepository.selectList(Wrappers.<Demo>complexQuery()
//                .term(false, Demo::getAddress, "东莞市")
//                .term(false, Demo::getAge, 10)
//                .range(Demo::getAge, 18, 10));
//        System.out.println(list);
//    }
//
//
//}
