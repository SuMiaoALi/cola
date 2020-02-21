package com.baoyun.ins.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 孔垂云 on 2017/5/17.
 */
@Component
public class RedisUtil {
    /**
     * 注入的stringRedisTemplate
     */
    /*@Autowired
    private StringRedisTemplate stringRedisTemplate;*/
    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    /**
     * 设置key和对象的value
     *
     * @param key key
     * @param value value
     */
    public void set(final String key, Object value) {
        final byte[] vbytes = SerializeUtil.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize(key), vbytes);
                return null;
            }
        });
    }

    /**
     * 设置key和value，并加上时间（秒）
     *
     * @param key key
     * @param value value
     * @param l 时间
     */
    public void set(final String key, Object value, final long l) {
        final byte[] vbytes = SerializeUtil.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // setex key time value
            	connection.setEx(redisTemplate.getStringSerializer().serialize(key), l, vbytes);
                return null;
            }
        });
    }

    /**
     * 根据key来取值
     *
     * @param key key
     * @param elementType 数据类型
     * @param <T> 泛型
     * @return 获取的对象
     */
    public <T> T get(final String key, Class<T> elementType) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @SuppressWarnings("unchecked")
			@Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keybytes = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keybytes)) {
                    byte[] valuebytes = connection.get(keybytes);
                    T value = (T) SerializeUtil.deserialize(valuebytes);
                    return value;
                }
                return null;
            }
        });
    }

    /**
     * 根据key删除该值
     *
     * @param key key
     */
    public void del(final String key) {
        final byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(keyBytes);
                return null;
            }
        });
    }
    /**
     * 判断是否存在
     * @Description: TODO
     * @author louis
     * @date 2018年5月3日 上午1:51:56
     * @param key
     * @return
     * @throws
     */
    public boolean exists(final String key) {
    	// 对传入的key先序列化
        final byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                // exists key
                return connection.exists(keyBytes);
            }
        });
    }
}