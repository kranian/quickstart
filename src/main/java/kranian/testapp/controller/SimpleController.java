package kranian.testapp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import kranian.testapp.listener.ElevisorSessionListener;
import kranian.testapp.util.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author koo.taejin
 */
@Controller
public class SimpleController {

    @RequestMapping("/getCurrentTimestamp")
    @ResponseBody
    @Description("Returns the server's current timestamp.")
    public Map<String, Object> getCurrentTimestamp() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("getCurrentTimestamp", System.currentTimeMillis());

        return map;
    }
    @RequestMapping("/sleep1")
    @ResponseBody
    @Description("Call that takes 1 seconds to complete.")
    public Map<String, Object> sleep1() throws InterruptedException {
        Thread.sleep(1000);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "1s ok");

        return map;
    }

    @RequestMapping("/sleep100ms")
    @ResponseBody
    @Description("Call that takes 1 seconds to complete.")
    public Map<String, Object> sleep100ms() throws InterruptedException {
        Thread.sleep(100);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "100ms ok");

        return map;
    }
    @RequestMapping("/sleep3")
    @ResponseBody
    @Description("Call that takes 3 seconds to complete.")
    public Map<String, Object> sleep3() throws InterruptedException {
        Thread.sleep(3000);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "3s ok");

        return map;
    }

    @RequestMapping("/sleep5")
    @ResponseBody
    @Description("Call that takes 5 seconds to complete")
    public Map<String, Object> sleep5() throws InterruptedException {
        Thread.sleep(5000);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "5s ok");

        return map;
    }

    @RequestMapping("/sleep7")
    @ResponseBody
    @Description("Call that takes 7 seconds to complete")
    public Map<String, Object> sleep7() throws InterruptedException {
        Thread.sleep(7000);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "7s ok");

        return map;
    }
    @RequestMapping("/sleepRandom")
    @ResponseBody
    @Description("Call that takes random seconds to complete")
    public Map<String, Object> sleepRandom() throws InterruptedException {
        Random random = new Random();
        int min = 100;
        int max = random.nextInt(1 * 11 * 1000);
        int result = Math.max(min,max);
        Thread.sleep(result);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", result + "ms ok");

        return map;
    }
    @RequestMapping("/sleep5000")
    @ResponseBody
    @Description("Call that takes 5 min to complete")
    public Map<String, Object> sleep5min() throws InterruptedException {
        Thread.sleep(5 * 60 * 1000 );

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "5 min ok");

        return map;
    }

    @RequestMapping("/sessionCounter")
    @ResponseBody
    @Description("Call that takes SessionCounter to complete")
    public Map<String, Object> sesisonCounter() throws InterruptedException {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        session.invalidate();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Session Make Counter", ElevisorSessionListener.getTotalSessionCount());
        return map;
    }
}
