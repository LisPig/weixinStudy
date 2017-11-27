/*-----------JAVABEAN----------
 * @功能说明：缓存管理器
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-04-15:09:23
 */
package com.ego.core.lang.cache;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理器，用于管理缓存的提供者。
 *
 * @author Administrator
 */
public class CacheManager  {
	private static ConcurrentHashMap<String ,Object> cache=new ConcurrentHashMap<String ,Object>();
/*
    private static CacheProvider provider;

    static {
        loadInitialProviders(CacheProvider.class);
    }

    public static synchronized void registerProvider(Provider p) throws SpiException {
        registerProvider(Cache.SERVICE_NAME, p);
    }

    public static Collection<Provider> getProviders() throws SpiException {
        return getProviders(Cache.SERVICE_NAME);
    }
*/
    /**
     * 获取系统可用缓存
     *
     *
     * @return
     * @throws CacheException
     */
	/*
    public static Cache getCache() throws CacheException {
        if (provider != null) {
            return provider.getService();
        }
        Collection<Provider> providers;
        try {
            providers = getProviders();
            for (Provider p : providers) {
                if (p.supports(null)) {
                    provider = (CacheProvider) p;
                    return provider.getService();
                }
            }
        } catch (SpiException ex) {
            throw new CacheException(ex);
        }
        throw new CacheException("not find available cache service");
    }
*/
    /**
     * 获取缓存中的数据
     *
     * @param key
     * @return
     * @throws CacheException
     */
    public static Object get(String key)  {
        if (key != null) {
            return cache.get(key);
        }
        return null;

    }

    /**
     * 向指缓存写入数据
     *
     * @param key
     * @param value
     * @throws CacheException 如果没有对应name的缓存
     */
    public static void put(String key, Object value)  {
        if (key != null && value != null) {
        	cache.put(key, value);
        }

    }

    /**
     * 清除缓冲中的某个数据
     *
     * @param key
     * @return 返回清除的旧值，如果没有对应的旧值返回null，如果缓存名、缓存键为null，返回null
     * @throws CacheException 如果没有对应name的缓存
     */
    public static Object clear(String key) {
        Object returnValue = null;
        if (key != null) { 
                returnValue = cache.get(key);
                cache.remove(key);
        }
        return returnValue;
    }

  
    /**
     * 清除默认缓存的所有数据项
     *
     * @throws CacheException 如果没有对应name的缓存
     */
    public static void clear()  {
    	cache.clear();;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }
}
