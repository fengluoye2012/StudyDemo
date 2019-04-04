package com.mydesign.modes.design_modes.proxy;


import com.mydesign.modes.design_modes.proxy.d_proxy.OverSeasPurchase;
import com.mydesign.modes.design_modes.proxy.d_proxy.SpecificInvocationHandler;
import com.mydesign.modes.design_modes.proxy.d_proxy.RealPurchase;
import com.mydesign.modes.design_modes.proxy.static_proxy.Accuser;
import com.mydesign.modes.design_modes.proxy.static_proxy.Lawsuit;
import com.mydesign.modes.design_modes.proxy.static_proxy.LawyerProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理模式是常用的java设计模式，他的特征是代理类与委托类有同样的接口，代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等。
 * 代理类与委托类之间通常会存在关联关系，一个代理类的对象与一个委托类的对象关联，代理类的对象本身并不真正实现服务，而是通过调用委托类的对象的相关方法，来提供特定的服务。
 * 简单的说就是，我们在访问实际对象时，是通过代理对象来访问的，代理模式就是在访问实际对象时引入一定程度的间接性，因为这种间接性，可以附加多种用途。
 */
public class ProxyClient {

    private static ProxyClient instance;

    public static ProxyClient getInstance() {
        if (instance == null) {
            synchronized (ProxyClient.class) {
                if (instance == null) {
                    instance = new ProxyClient();
                }
            }
        }
        return instance;
    }

    public void test() {
        //staticProxy();
        proxy();
    }

    /**
     * 动态代理:不需要提前创建代理类，动态的创建代理类；
     * 用到InvocationHandler接口、反射；
     * <p>
     * 相比于静态代理， 动态代理的优势在于可以很方便的对代理类的函数进行统一的处理，而不用修改每个代理类中的方法。
     * 比如说，想要在每个代理的方法前都加上一个处理方法：
     * <p>
     * 只能对接口进行代理，Java的继承机制注定了这些动态代理类们无法实现对class的动态代理。
     * <p>
     * 类似于AOP（面向切面编程）和动态代理原理类似，使用proxy和InvocationHandler;
     */
    private void proxy() {
        //创建一个实例对象，这个对象是被代理的对象；
        RealPurchase zhangSan = new RealPurchase();

        //创建一个与代理对象相关联的InvocationHandler；
        InvocationHandler purchaseHandle = new SpecificInvocationHandler<OverSeasPurchase>(zhangSan);

        //创建一个代理对象来代理zhangSan,代理对象的每个执行方法都会替换执行Invocation中的invoke()；
        OverSeasPurchase overSeasPurchaseProxy = (OverSeasPurchase) Proxy.newProxyInstance(OverSeasPurchase.class.getClassLoader(),
                new Class<?>[]{OverSeasPurchase.class}, purchaseHandle);

        //代理执行海外购买的方法；
        overSeasPurchaseProxy.purchase();
        overSeasPurchaseProxy.pay();

        //诉讼
        Accuser xiaoMing = new Accuser();
        InvocationHandler handler = new SpecificInvocationHandler<Lawsuit>(xiaoMing);
        Lawsuit lawsuitProxy = (Lawsuit) Proxy.newProxyInstance(Lawsuit.class.getClassLoader(), new Class<?>[]{Lawsuit.class}, handler);
        lawsuitProxy.lawsuit();

    }

    /**
     * 静态代理：要提前写好代理类；
     */
    private void staticProxy() {
        Accuser xiaoMing = new Accuser();
        LawyerProxy lawyer = new LawyerProxy(xiaoMing);
        lawyer.lawsuit();
    }
}
