package com.ivo.sapweb.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义session监听
 * @author wangjian
 * @date 2018/9/3
 */
public class MySessionListener implements SessionListener {

    /**
     * AtomicInteger 中主要实现了整型的原子操作，防止并发情况下出现异常结果
     */
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
        System.out.println("session onStart:" + sessionCount + ";  " + session.getTimeout() + ";  " + session.getId());
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("session onStart:" + sessionCount + ";  " + session.getTimeout() + ";  " + session.getId());

    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        System.out.println("session onStart:" + sessionCount + ";  " + session.getTimeout() + ";  " + session.getId());
    }

    public int getSessionCount() {
        return sessionCount.get();
    }
}
