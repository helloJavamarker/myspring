package com.spring;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {
    private Class configClass;
    private Map<String, Object> singleMap = new ConcurrentHashMap<>(); //单例池
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;
        //解析配置,解析spring提供的注解:类上面和类的方法上面----自定义的注解可能不会解析
        //componentScan---扫描路径---扫描
        scan(configClass);
        for (String beanName: beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                Object bean = creatBean(beanDefinition);
                singleMap.put(beanName, bean);
            }

        }
    }

    private Object creatBean(BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        try {
            Object bean = clazz.getDeclaredConstructor().newInstance();
            // 依赖注入
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    //declaredField.set(bean, ?);      //如何赋值,byName,byType===要先从spring容器中找
                    //从容器中找:1,直接调用map,这样不好     2,调用getBean
                    Object fieldBean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(bean, fieldBean);
                }
            }





            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scan(Class configClass) {
        ComponentScan componenetAnno = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componenetAnno.value();
        path = path.replace(".","/");
        //根据包名获得包下面的类====类加载器
        //bootstrap(jre/lib),ext(jre/ext/lib),app(classpath)
        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
//        URL resource = classLoader.getResource("com/zhang/service");
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getFile());
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files) {
//                System.out.println(f);
                ///Users/mark/IdeaProjects/myspring/out/production/myspring/com/zhang/service/UserService.class
                String fileName = f.getAbsolutePath();
                if (fileName.endsWith(".class")) {
                    String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                    className = className.replace("/", ".");
                    // /Users/mark/IdeaProjects/myspring/out/production/myspring/com/zhang/service/UserService.class --> com.zhang.service.UserService

                    //Class<?> aClass = classLoader.loadClass("com.zhang.service.UserService");
                    Class<?> aClass = null;
                    try {
                        aClass = classLoader.loadClass(className);
                        if (aClass.isAnnotationPresent(Component.class)) {
                            Component componentAnno = aClass.getDeclaredAnnotation(Component.class);
                            String beanName = componentAnno.value();
                            // 当前这个类是一个bean---?要创建对象?====bean的作用域不同,不一定要立即创建对象  还有lazy todo
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(aClass);
                            if (aClass.isAnnotationPresent(Scope.class)) {
                                Scope scopeAnno = aClass.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnno.value());
                            } else {
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String beanName){
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                return singleMap.get(beanName);
            } else {
                //原型bean,每次都创建
                Object bean = creatBean(beanDefinition);
                return bean;
            }
        } else {
            //throw RuntimeException("不存在该对象");
        }
        return null;
    }
}
