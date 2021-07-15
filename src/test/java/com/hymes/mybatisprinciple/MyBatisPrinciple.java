package com.hymes.mybatisprinciple;

import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface  UserMapper{
    @Select("select * from blog where id=#{id}")
    List<UserMapper> selectUserList(Integer id,String name);
}

@SpringBootTest
class MyBatisPrinciple {
    @Test
    public void test() {
        UserMapper userMapper= (UserMapper) Proxy.newProxyInstance(MyBatisPrinciple.class.getClassLoader(),
                new Class<?>[]{UserMapper.class},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(Arrays.toString(args));
                Select annotation = method.getAnnotation(Select.class);
                Map<String, Object> nameArgMap = buildMethodArgNameMap(method, args);
                if (annotation != null) {
                    String[] value = annotation.value();
                    System.out.println(Arrays.toString(value));
                    System.out.println(method.getName());
                }
                return null;
            }
        });
        userMapper.selectUserList(1,"test");
    }

    public static String parseSQL(String sql, Map<String, Object> nameArgMap) {
        return "";
    }

    public static Map<String, Object> buildMethodArgNameMap(Method method, Object[] args) {
        Map<String, Object> nameArgMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        if (args.length != parameters.length) {
            throw new RuntimeException();
        }
        int[] index ={0};
        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            nameArgMap.put(name, args[index[0]]);
            index[0]++;
        });
        return nameArgMap;
    }

}
