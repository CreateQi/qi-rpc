package com.qi.rpc.core.extension;

import java.lang.annotation.*;

/**
 * SPI 注解，被标注的类表示为需要加载的扩展类接口
 *
 * @author CreateQi
 * @version 1.0
 * @ClassName SPI
 * @Date 2023/1/11 19:04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPI {

}
