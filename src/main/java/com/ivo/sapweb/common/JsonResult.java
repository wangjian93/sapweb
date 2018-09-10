package com.ivo.sapweb.common;

import java.util.HashMap;

/**
 * 返回结果对象
 * @author wangjian
 * @date 2018/8/30
 */
public class JsonResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private JsonResult(){}

    /**
     * 返回成功
     * @return
     */
    public static JsonResult ok() {
        return ok("操作成功");
    }

    /**
     * 返回成功
     * @param message
     * @return
     */
    public static JsonResult ok(String message) {
        return ok("200", message);
    }

    /**
     * 返回成功
     * @param code
     * @param message
     * @return
     */
    public static JsonResult ok(String code, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("code", code);
        jsonResult.put("msg", message);
        return jsonResult;
    }

    /**
     * 返回失败
     */
    public static JsonResult error() {
        return error("操作失败");
    }

    /**
     * 返回失败
     * @param message
     * @return
     */
    public static JsonResult error(String message) {
        return error("500", message);
    }

    /**
     * 返回失败
     * @param code
     * @param message
     * @return
     */
    public static JsonResult error(String code, String message) {
        return ok(code, message);
    }

    /**
     * 设置code
     * @param code
     * @return
     */
    public JsonResult setCode(String code) {
        super.put("code", code);
        return this;
    }

    /**
     * 设置message
     * @param message
     * @return
     */
    public JsonResult setMessage(String message) {
        super.put("msg", message);
        return this;
    }

    /**
     * 放入object
     * @param key
     * @param object
     * @return
     */
    @Override
    public JsonResult put(String key, Object object) {
        super.put(key, object);
        return this;
    }

}
