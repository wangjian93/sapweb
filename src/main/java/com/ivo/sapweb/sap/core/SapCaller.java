package com.ivo.sapweb.sap.core;

import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/8/14
 */
public interface SapCaller {

    /**
     * 调用sap中的function接口
     *
     * @param functionName  function名称
     * @param importParams  import参数
     * @param inStructures  import结构体
     * @param inTables  传入内表
     * @param exportParams  export参数名称
     * @param outTables  export内表名称
     * @return Map（key--表export的参数、结构体、表的名称, value--表值）
     */
     Map sapCall(String functionName, Map<String, String> importParams, Map<String, Map> inStructures,
                 Map<String, List> inTables, String[] exportParams, String[] outStructures, String[] outTables);

    /**
     * 获取function的各参数信息
     * @param functionName
     * @return
     */
     Map getFunctionParamsInfo(String functionName);

}
