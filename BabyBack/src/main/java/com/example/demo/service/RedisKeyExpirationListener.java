//package com.example.demo.service;
//
//import io.lettuce.core.RedisClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
//    private final static Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
//    /**
//     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
//     *
//     * @param listenerContainer must not be {@literal null}.
//     */
//    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
//        String expiredKey = message.toString();
//        System.out.println(expiredKey);
//    }
//}