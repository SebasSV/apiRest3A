package com.tress.app.init.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Controller;

@Controller
class TestController {

    @RequestMapping("/ruta")
    @ResponseBody
    public Map<String, Object> newTest(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();

        result.put("awbNumber", "value");
        
        return result;

    }

}