/**
 * Copyright: Copyright (c)2016
 * Company: 易宝支付(YeePay)
 */
package com.websystique.springmvc.service;

import java.util.List;

/**
 * 接口名称: BaseService <br>
 * 接口描述: <br>
 *
 * @author: zhongyi.ning
 * @since: 16/5/27 下午3:44
 * @version: 1.0.0
 */
public interface BaseService<K, T> {

    T findById(K id);

    void save(T t);

    void update(T t);

    void deleteById(K id);

    List<T> getAll();

    void deleteAll();

    boolean isExist(T t);
}
