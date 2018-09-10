package com.ivo.sapweb.common.core;

import java.io.Serializable;

/**
 * @author wangjian
 * @date 2018/9/1
 */
public class ModelAtom implements Serializable {

    /**
     * 逻辑删除
     */
    private boolean isValid = true;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
