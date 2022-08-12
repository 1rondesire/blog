package com.xzq.blog.service.mq;

import com.alibaba.fastjson.JSON;
import com.xzq.blog.service.ArticleService;
import com.xzq.blog.vo.ArticleMessage;
import com.xzq.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

@Component
@Slf4j
@RocketMQMessageListener(topic = "blog-update-article",consumerGroup = "blog-update-article-group")
public class ArticleListener implements RocketMQListener<ArticleMessage> {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public void onMessage(ArticleMessage articleMessage) {
        //更新文章详情的缓存
        log.info("得到消息：rocketmq更新articlebody的缓存");
        //articlebody信息在缓存中的key
        String prefixofkey = "bodyof_article::ArticleController::findArticleById::";
        Long articleId = articleMessage.getArticleId();
        String md5Hex = DigestUtils.md5Hex(articleId.toString());
        String key = prefixofkey+md5Hex;
        //得到要更新进缓存的value
        Result article = articleService.findArticleById(articleId);
        redisTemplate.opsForValue().set(key, JSON.toJSONString(article), Duration.ofMillis(5 * 60 * 1000));
        log.info("rocketmq更新了缓存:"+key);

        //更新文章列表的缓存
        log.info("得到消息：rocketmq更新articlelist的缓存");
        Set<String> keys = redisTemplate.keys("listof_article*");
        keys.forEach((set)->{
            redisTemplate.delete(set);
            log.info("rocketmq更新了articlelist的缓存");
        });
    }
}
