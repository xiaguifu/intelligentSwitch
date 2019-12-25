package com.sanmeditech.config;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

/**
 * Created by lwq on 2019/12/18.
 */
@ControllerAdvice(annotations = Controller.class)
public class ResponseBodyHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return Arrays.stream(methodParameter.getMethodAnnotations()).anyMatch(annotation -> annotation instanceof ResponseBody);
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType mediaType, Class resolveClass, ServerHttpRequest request, ServerHttpResponse response) {
        if(resolveClass == StringHttpMessageConverter.class){
            Object result = null;
            if(null == body){
                result = BodyResult.SUCCESS;
            }else if(body instanceof BodyResult){
                result = body;
            }else if(body instanceof ResponseEntity){
                return body;
            }else if(body instanceof Exception){
                result = new BodyResult(1,((Exception) body).getMessage());
            }else if(body instanceof String){
                result = BodyResult.success(body);
            }
            return JSON.toJSONString(result);
        }else{
            if(null == body){
                return BodyResult.SUCCESS;
            }else if(body instanceof BodyResult){
                return body;
            }else if(body instanceof ResponseEntity){
                return body;
            }else if(body instanceof Exception){
                return new BodyResult(1,((Exception) body).getMessage());
            }else if(body instanceof String){
                return BodyResult.success(body);
            }
        }
        return BodyResult.success(body);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception exception){
        return new BodyResult(1,exception.getMessage());
    }

    public static void writeToResponseBody(HttpServletResponse response, Object result) throws IOException {
        response.setHeader("ContentType", MediaType.APPLICATION_JSON_UTF8_VALUE);
        Writer writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
    }
}
