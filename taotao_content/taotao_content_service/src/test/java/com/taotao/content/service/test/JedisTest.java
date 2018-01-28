package com.taotao.content.service.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    //单机版
    @Test
    public void singleJedisTest() {
        Jedis jedis = new Jedis("192.168.84.129",6379);
        jedis.set("bkey","bvalue");
        System.out.println("bkey:"+jedis.get("bkey"));
        jedis.close();
    }

    //jedis连接池
    @Test
    public void jedisPoolTest() {
        JedisPool jedisPool = new JedisPool("192.168.84.129",6379);
        //从连接池中获取jedis
        Jedis jedis = jedisPool.getResource();
        jedis.set("bkey","bvalue");
        System.out.println("bkey:"+jedis.get("bkey"));
        jedis.close();
    }

    //jedis集群
    @Test
    public void jedisClusterTest() {
        Set<HostAndPort> nodes =  new HashSet<>();
        nodes.add(new HostAndPort("192.168.84.129",7001));
        nodes.add(new HostAndPort("192.168.84.129",7002));
        nodes.add(new HostAndPort("192.168.84.129",7003));
        nodes.add(new HostAndPort("192.168.84.129",7004));
        nodes.add(new HostAndPort("192.168.84.129",7005));
        nodes.add(new HostAndPort("192.168.84.129",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("cluster","cluster");
        String cluster = jedisCluster.get("cluster");
        System.out.println("cluster:"+cluster);
        jedisCluster.close();//当tomcat停止时关闭
    }
}
