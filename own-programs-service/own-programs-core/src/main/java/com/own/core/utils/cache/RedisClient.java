package com.own.core.utils.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import com.own.core.utils.LoggerProxyFactory;
import com.own.core.utils.SerializeUtil;
import com.own.core.utils.pressure.PressureTestUtils;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

/**
 * 类 名: RedisClient<br/>
 * 描 述: 缓存接口<br/>
 * 作 者: 郭昕<br/>
 * 创 建： 2013-6-26<br/>
 *
 * 历 史: (版本) 作者 时间 注释
 */
@Service("redisClient")
public class RedisClient implements InitializingBean{

	/**
	 * 日志输出
	 */
	private static final Logger LOGGER = LoggerProxyFactory.getLogger(RedisClient.class);

	/**
	 * jedisPool缓存
	 */
	@Autowired
	private JedisCluster jedisCluster;

	@Resource
	private RedisConnectionFactory redisConnectionFactory;

	/**
	 * 这里是临时处理方法，从redisConnectionFactory中用反射获取jedisPool对象
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Class<? extends RedisConnectionFactory> class1 =
		// redisConnectionFactory.getClass();
		// Field field = class1.getDeclaredField("pool");
		// field.setAccessible(true);
		// jedisPool = (Pool<Jedis>) field.get(redisConnectionFactory);
	}

	private JedisCluster getJedis() {
		return jedisCluster;
	}

	private void returnJedis(JedisCluster jedis) {

		// jedisPool.returnResource(jedis);
	}

	/**
	 * 描 述：set字符串<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param value
	 *            值
	 * @param timeout
	 *            过期时间
	 */
	public void set(final String key, final String value, final int timeout) {
		JedisCluster jedis = getJedis();
		try {
			if (timeout > 0) {
				jedis.setex(PressureTestUtils.getRedisKeyStartPrefix() + key , timeout,
						value);
			} else {
				jedis.set(PressureTestUtils.getRedisKeyStartPrefix() + key, value);
			}

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：set字符串，指定具体的超时时间点<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param value
	 *            值
	 * @param unixTime
	 *            超时时间
	 */
	public void set(final String key, final String value, final long unixTime) {
		JedisCluster jedis = getJedis();
		try {
			jedis.set(PressureTestUtils.getRedisKeyStartPrefix() + key, value);
			if (unixTime > 0) {
				jedis.expireAt(PressureTestUtils.getRedisKeyStartPrefix() + key, unixTime);
			}
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：set对象<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param value
	 *            值
	 * @param timeout
	 *            超时时间
	 */
	public void set(final String key, final Serializable value, final int timeout) {
		JedisCluster jedis = getJedis();
		try {
			if (timeout > 0) {
				jedis.setex(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), timeout, SerializeUtil.obj2Bytes(value));
			} else {
				jedis.set(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), SerializeUtil.obj2Bytes(value));
			}
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：set对象，指定具体的超时时间点<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param value
	 *            值
	 * @param unixTime
	 *            超时时间
	 */
	public void set(final String key, final Serializable value, final long unixTime) {
		JedisCluster jedis = getJedis();
		try {
			jedis.set(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), SerializeUtil.obj2Bytes(value));
			if (unixTime > 0) {
				jedis.expireAt(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), unixTime);
			}
		} finally {
			returnJedis(jedis);
		}
	}

	public void setString(final String key, final String value, final int timeout) {
		JedisCluster jedis = getJedis();
		try {
			if (timeout > 0) {
				jedis.setex(PressureTestUtils.getRedisKeyStartPrefix() + key, timeout, value);
			} else {
				jedis.set(PressureTestUtils.getRedisKeyStartPrefix() + key, value);
			}
		} finally {
			returnJedis(jedis);
		}
	}

	public void del(final String key) {
		JedisCluster jedis = getJedis();
		try {
			jedis.del(key);
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：设置超时时间<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param seconds
	 *            超时时间
	 */
	public void expire(final String key, final int seconds) {
		JedisCluster jedis = getJedis();
		try {
			if (seconds > 0) {
				jedis.expire(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), seconds);
			}
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：指定超时时间<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param unixTime
	 *            超时时间
	 */
	public void expireAt(final String key, final long unixTime) {
		JedisCluster jedis = getJedis();
		try {
			if (unixTime > 0) {
				jedis.expireAt(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), unixTime);
			}

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：get字符串<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return 值
	 */
	public String getString(final String key) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.get(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：get对象<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T get(final String key) {
		JedisCluster jedis = getJedis();
		try {
			byte[] b = jedis.get(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key));
			if (b != null && b.length > 0) {
				return (T) SerializeUtil.bytes2Obj(b);
			}
			return null;

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：判断key是否存在<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return 值
	 */
	public Boolean exists(final String key) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.exists(PressureTestUtils.getRedisKeyStartPrefix() + key);
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：原子减法，减去指定的值 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param integer
	 *            值
	 * @return
	 */
	public Long decrBy(final String key, final long integer) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.decrBy(PressureTestUtils.getRedisKeyStartPrefix() + key, integer);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：原子减法 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return
	 */
	public Long decr(final String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.decr(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 原子加法，加上指定的值 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param integer
	 * @return
	 */
	public Long incrBy(final String key, final long integer) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.incrBy(PressureTestUtils.getRedisKeyStartPrefix() + key, integer);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：原子加法 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return
	 */
	public Long incr(final String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.incr(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描述：hash set <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param field
	 *            字段
	 * @param value
	 *            值
	 */
	public void hsetString(final String key, final String field, final String value) {
		JedisCluster jedis = getJedis();
		try {
			jedis.hset(PressureTestUtils.getRedisKeyStartPrefix() + key, field, value);

		} finally {
			returnJedis(jedis);
		}
	}
	
	public Long hlen(final String key) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.hlen(PressureTestUtils.getRedisKeyStartPrefix() + key);
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：hash set对象<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param field
	 *            字段
	 * @param value
	 *            值
	 */
	public void hset(final String key, final String field, final Serializable value) {
		JedisCluster jedis = getJedis();
		try {

			jedis.hset(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), getKey(field), SerializeUtil.obj2Bytes(value));

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描述：hash get <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param field
	 *            字段
	 * @return
	 */
	public String hgetString(final String key, final String field) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.hget(PressureTestUtils.getRedisKeyStartPrefix() + key, field);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：hash get对象<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param <T>
	 * @param key
	 *            key键
	 * @param field
	 *            字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T hget(final String key, final String field) {
		JedisCluster jedis = getJedis();
		try {

			byte[] b = jedis.hget(getKey(PressureTestUtils.getRedisKeyStartPrefix() + key), getKey(field));
			if (b != null && b.length > 0) {
				return (T) SerializeUtil.bytes2Obj(b);
			}
			return null;

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：hash getall <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return
	 */
	public Map<String, String> hgetAll(final String key) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.hgetAll(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：hash batch set <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param hash
	 */
	public void hmset(final String key, final Map<String, String> hash) {
		JedisCluster jedis = getJedis();
		try {

			jedis.hmset(PressureTestUtils.getRedisKeyStartPrefix() + key, hash);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：hash batch get <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param fields
	 *            值
	 * @return
	 */
	public List<String> hmget(final String key, final String... fields) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.hmget(PressureTestUtils.getRedisKeyStartPrefix() + key, fields);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：hash原子加法 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param field
	 *            字段
	 * @param value
	 *            值
	 * @return
	 */
	public Long hincrBy(final String key, final String field, final long value) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.hincrBy(PressureTestUtils.getRedisKeyStartPrefix() + key, field, value);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：hash key/field是否存在 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param field
	 *            字段
	 * @return
	 */
	public Boolean hexists(final String key, final String field) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.hexists(PressureTestUtils.getRedisKeyStartPrefix() + key, field);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述： hash删除field <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param fields
	 *            值
	 * @return
	 */
	public long hdel(final String key, final String... fields) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.hdel(PressureTestUtils.getRedisKeyStartPrefix() + key, fields);

		} finally {
			returnJedis(jedis);
		}
	}

	private static final int DEFAULT_EXPIRE_TIME = 90;

	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public Boolean lock(final String key, final long timeout, final TimeUnit unit) {
		JedisCluster jedis = getJedis();
		try {

			if (timeout <= 0) {
				return Boolean.FALSE;
			}

			long nano = System.nanoTime();
			do {
				try {
					if (jedis.setnx(PressureTestUtils.getRedisKeyStartPrefix() + key, key) == 1) {
						jedis.expire(PressureTestUtils.getRedisKeyStartPrefix() + key, DEFAULT_EXPIRE_TIME);
						return Boolean.TRUE;
					} else { // 存在锁
						if (LOGGER.isDebugEnabled()) {
							String desc = jedis.get(PressureTestUtils.getRedisKeyStartPrefix() + key);
							LOGGER.debug("key: " + key + " locked by another business：" + desc);
						}
					}

					Thread.sleep(100);
				} catch (Exception e) {
					LOGGER.error("[获取锁] 异常，key:{}", key, e);
					// try {
					// jedis.del(key+PressureTestUtils.getRedisKeyStartPrefix());
					// } catch (Exception e2) {
					// LOGGER.error("[获取锁] 异常，key:{}",key,e2);
					// }
					// throw e;
					return Boolean.FALSE;
				}
			} while ((System.nanoTime() - nano) < unit.toNanos(timeout));
			return Boolean.FALSE;

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 释放锁
	 * 
	 * @param key
	 */
	public Void unLock(final String key) {
		JedisCluster jedis = getJedis();
		try {
			try {
				jedis.del(PressureTestUtils.getRedisKeyStartPrefix() + key);
			} catch (Exception e) {
				LOGGER.error("[释放锁] 异常，key:{}", key, e);
				jedis.del(PressureTestUtils.getRedisKeyStartPrefix() + key);
				// throw e;
			}
			return null;

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：字符串转字节数组 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return
	 */
	private byte[] getKey(final String key) {
		JedisCluster jedis = getJedis();
		try {
			return key.getBytes();
		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述： 存储List数据 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 */
	public void lpush(final String key, final String... fields) {
		JedisCluster jedis = getJedis();
		try {
			jedis.lpush(PressureTestUtils.getRedisKeyStartPrefix() + key, fields);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：将一个或多个值 value 插入到列表 key 的表尾(最右边)。<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param fields
	 *            值
	 */
	public void rpush(final String key, final String... fields) {
		JedisCluster jedis = getJedis();
		try {

			jedis.rpush(PressureTestUtils.getRedisKeyStartPrefix() + key, fields);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：根据参数 count 的值，移除列表中与参数 value 相等的元素。 count 的值可以是以下几种： count > 0 :
	 * 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。 count < 0 : 从表尾开始向表头搜索，移除与 value
	 * 相等的元素，数量为 count 的绝对值。 count = 0 : 移除表中所有与 value 相等的值。<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param count
	 * @param value
	 *            值
	 */
	public void lrem(final String key, final long count, final String value) {
		JedisCluster jedis = getJedis();
		try {

			jedis.lrem(PressureTestUtils.getRedisKeyStartPrefix() + key, count, value);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回列表 key 的长度<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return 长度
	 */
	public Long llen(final String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.llen(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 */
	public List<String> lrange(final String key, final long start, final long end) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.lrange(PressureTestUtils.getRedisKeyStartPrefix() + key, start, end);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：删除List中包含fields所有数据 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param fields
	 *            值
	 */
	public void lrem(final String key, final String fields) {
		JedisCluster jedis = getJedis();
		try {

			jedis.lrem(PressureTestUtils.getRedisKeyStartPrefix() + key, 0, fields);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 描 述：sort 升序 <br/>
	 * 作 者：郭昕 <br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return
	 */
	public List<String> sort(final String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.sort(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：向名称为 key 的 zset 中添加元素 member，score 用于排序<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(final String key, final double score, final String member) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zadd(PressureTestUtils.getRedisKeyStartPrefix() + key, score, member);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回集合中元素个数<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @return
	 */
	public Long zcard(final String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zcard(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回名称为 key 的 zset（按 score 从大到小排序）中的 index 从 start 到 end 的所有元素<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 * @return
	 */
	public Set<String> zrevrange(final String key, final long start, final long end) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zrevrange(PressureTestUtils.getRedisKeyStartPrefix() + key, start, end);

		} finally {
			returnJedis(jedis);
		}
	}

	public Set<Tuple> zrevrangeByScores(final String key, final long start, final long end) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zrevrangeWithScores(PressureTestUtils.getRedisKeyStartPrefix() + key, start, end);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：删除集合中排名在给定区间的元素<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 * @return
	 */
	public Long zremrangeByRank(final String key, final long start, final long end) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zremrangeByRank(PressureTestUtils.getRedisKeyStartPrefix() + key, start, end);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：删除集合中排名在给定区间的元素<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 *            key键
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 * @return
	 */
	public Long zremrangeByScore(final String key, final long start, final long end) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zremrangeByScore(PressureTestUtils.getRedisKeyStartPrefix() + key, start, end);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
	 * 
	 * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
	 * 
	 * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(final String key, final String member) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zrevrank(PressureTestUtils.getRedisKeyStartPrefix() + key, member);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回有序集 key 中，成员 member 的 score 值<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(final String key, final String member) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.zscore(PressureTestUtils.getRedisKeyStartPrefix() + key, member);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 假如 key 不存在，则创建一个只包含
	 * member 元素作成员的集合。 当 key 不是集合类型时，返回一个错误。<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(final String key, final String... members) {
		JedisCluster jedis = getJedis();
		try {

			jedis.smembers(PressureTestUtils.getRedisKeyStartPrefix() + key);
			return jedis.sadd(PressureTestUtils.getRedisKeyStartPrefix() + key, members);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回集合 key 中的所有成员。 不存在的 key 被视为空集合<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(final String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.smembers(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：判断集合中成员是否存在<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 * @return
	 */
	public boolean sismember(final String key, final String field) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.sismember(PressureTestUtils.getRedisKeyStartPrefix() + key, field);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：返回集合 key 中的所有成员。 不存在的 key 被视为空集合<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param key
	 * @return
	 */
	public Long srem(final String key, final String... member) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.srem(PressureTestUtils.getRedisKeyStartPrefix() + key, member);

		} finally {
			returnJedis(jedis);
		}
	}

	/**
	 * 
	 * 描 述：BRPOPLPUSH 是 RPOPLPUSH 的阻塞版本<br/>
	 * 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作： 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。 将 source
	 * 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。 <br/>
	 * 举个例子，你有两个列表 source 和 destination ， source 列表有元素 a, b, c ， destination 列表有元素
	 * x, y, z ，执行 RPOPLPUSH source destination 之后， source 列表包含元素 a, b ， destination
	 * 列表包含元素 c, x, y, z ，并且元素 c 会被返回给客户端 <br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param source
	 *            原队列key
	 * @param destination
	 *            备份队列key
	 * @return
	 */
	public String brpoplpush(final String source, final String destination) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.brpoplpush(source, destination, 0);

		} finally {
			returnJedis(jedis);
		}
	}

	public List<String> brpop(final String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.brpop(0, key);

		} finally {
			returnJedis(jedis);
		}
	}

	@SuppressWarnings("deprecation")
	public List<String> zscan(String s) {
		JedisCluster jedis = getJedis();
		try {
			List<String> kset = new ArrayList<String>();
			String cusor = "0";
			ScanResult<Tuple> rs = null;
			do {
				rs = jedis.zscan(s, cusor);
				cusor = rs.getCursor() + "";
				List<Tuple> list = rs.getResult();
				for (Tuple t : list) {
					String k = t.getElement();
					kset.add(k);
				}
			} while (rs.getCursor() != 0);

			return kset;
		} finally {
			returnJedis(jedis);
		}
	}

	public boolean pexpireAt(String key, long millisecondsTimestamp) {
		JedisCluster jedis = getJedis();
		try {
			long ret = jedis.pexpireAt(PressureTestUtils.getRedisKeyStartPrefix() + key, millisecondsTimestamp);
			return ret > 0;
		} finally {
			returnJedis(jedis);
		}
	}

	public Long zcount(String key, long min, long max) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.zcount(PressureTestUtils.getRedisKeyStartPrefix() + key, min, max);
		} finally {
			returnJedis(jedis);
		}
	}

	public Long scard(String key) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.scard(PressureTestUtils.getRedisKeyStartPrefix() + key);
		} finally {
			returnJedis(jedis);
		}
	}

	public String lpop(String key) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.lpop(PressureTestUtils.getRedisKeyStartPrefix() + key);
		} finally {
			returnJedis(jedis);
		}
	}

	public String rpop(String key) {
		JedisCluster jedis = getJedis();
		try {
			return jedis.rpop(PressureTestUtils.getRedisKeyStartPrefix() + key);
		} finally {
			returnJedis(jedis);
		}
	}

	public Long ttl(String key) {
		JedisCluster jedis = getJedis();
		try {

			return jedis.ttl(PressureTestUtils.getRedisKeyStartPrefix() + key);

		} finally {
			returnJedis(jedis);
		}
	}
}
