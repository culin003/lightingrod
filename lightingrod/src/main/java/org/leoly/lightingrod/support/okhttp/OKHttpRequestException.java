/**
 * copywrite 2015-2020 金地物业
 * 不能修改和删除上面的版权声明
 * 此代码属于数据与信息中心部门编写，在未经允许的情况下不得传播复制
 * OKHttpRequestException.java
 * @Date 2017年9月21日 下午4:20:28
 * guguihe
 */
package org.leoly.lightingrod.support.okhttp;

/**
 * TODO:
 * 
 * @author guguihe
 * @Date 2017年9月21日 下午4:20:28
 */
public class OKHttpRequestException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -463291072248325569L;

    /**
     * 
     */
    public OKHttpRequestException(String msg) {
        super(msg);
    }
    
    public OKHttpRequestException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public OKHttpRequestException(Throwable t) {
        super(t);
    }
}
