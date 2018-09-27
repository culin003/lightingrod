/**
 * copywrite 2015-2020 金地物业
 * 不能修改和删除上面的版权声明
 * 此代码属于数据与信息中心部门编写，在未经允许的情况下不得传播复制
 * GsonIgnore.java
 * @Date 2017年8月17日 下午5:20:59
 * guguihe
 */
package org.leoly.lightingrod.support.gson;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * TODO:
 * 
 * @author guguihe
 * @Date 2017年8月17日 下午5:20:59
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface GsonIgnore {

}
