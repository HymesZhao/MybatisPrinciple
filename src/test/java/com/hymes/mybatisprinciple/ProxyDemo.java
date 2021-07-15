package com.hymes.mybatisprinciple;


import java.lang.reflect.Method;

interface  Test{
    public void say() throws NoSuchMethodException;
}
interface InvokeHandler{
    Object invoke(Object object, Method method, Object... args);
}
public class ProxyDemo {
    public static void main(String[] args) {
//        new Test() {
//            @Override
//            public void say() {
////                Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(),...........)
//
//            }
//        }

    }

    public Test createProxyInstance(InvokeHandler handler, Class<?> clazz) {
        return new Test() {
            @Override
            public void say() throws NoSuchMethodException {
                Method sayMethod = clazz.getMethod("say");
                Object invoke = handler.invoke(this, sayMethod);
            }
        };
    }
}
