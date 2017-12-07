package com.tress.app.init;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Controller;

@Controller
class RutaController {

    @RequestMapping("/otro")
    @ResponseBody
    public Map<String, Object> ruta(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();

        result.put("peticion", "correcto");

        return result;

    }

}
