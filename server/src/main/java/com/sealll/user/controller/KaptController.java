package com.sealll.user.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.sealll.config.SpringConfig;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.zip.GZIPOutputStream;

/**
 * @author sealll
 * @time 2021/4/22 11:53
 */
@Controller
public class KaptController implements ApplicationContextAware {
    //    @Autowired
    private Producer kaptchaProducer;

    private Map<String, BufferedImage> cache = new HashMap<>();
    private final Integer N = 5;

    public KaptController() {

    }

    @ResponseBody
    @RequestMapping("/kaptcha")
    public String getKaptcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String capText = null;
        BufferedImage bi = null;
        Iterator<Map.Entry<String, BufferedImage>> iterator = cache.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, BufferedImage> next = iterator.next();
            capText = next.getKey();
            bi = next.getValue();
            iterator.remove();
        }
        HttpSession session = req.getSession();
        String finalCapText = capText;
        new Thread(()->{
            long start = System.currentTimeMillis();
            session.setAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY, finalCapText);
            long start2 = System.currentTimeMillis();
            session.setAttribute(Constants.KAPTCHA_SESSION_CONFIG_DATE, new Date());

            Object o = ThreadContext.get(ParameterConstants.JEDIS);
            if(o != null){
                ((Jedis)o).close();
            }
            long end = System.currentTimeMillis();
//            System.out.println("2- ---" + (end - start2));
//            System.out.println("1- ---" + (end - start));
        }).start();


        Callable<Object> objectCallable = () -> {
            String capText1 = kaptchaProducer.createText();
            BufferedImage bi1 = this.kaptchaProducer.createImage(capText1);
            cache.put(capText1, bi1);
            return true;
        };
        Thread thread = new Thread(new FutureTask<Object>(objectCallable));
        thread.start();

        // Set to expire far in the past.
        resp.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        resp.setHeader("Pragma", "no-cache");
        // return a jpeg
        resp.setContentType("image/jpeg");

        // create the text for the image

        ServletOutputStream out = resp.getOutputStream();
//        out.write(new byte[]{'a','b','c'});

//        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
        ImageIO.write(bi, "jpg", out);
//        long end = System.currentTimeMillis();
//        System.out.println("teim1 " + (end - start1));
//        System.out.println("teim2 " + (end - start2));
//        System.out.println("teim3 " + (end - start3));
//        System.out.println("teim4 " + (end - start4));
        return "success";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        kaptchaProducer = applicationContext.getBean(Producer.class);
        for (int i = 0; i < N; i++) {
            String capText = kaptchaProducer.createText();
            BufferedImage bi = this.kaptchaProducer.createImage(capText);
            cache.put(capText, bi);
        }
    }
}
