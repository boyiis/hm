/******************************************************************************
 * Copyright (C) 2015 Shenzhen Penguin Network Technology Co., Ltd
 * All Rights Reserved.
 * �����Ϊ�����������Ƽ����޹�˾�������ơ�δ������˾��ʽ����ͬ�⣬�����κθ��ˡ�����
 * ����ʹ�á����ơ��޸Ļ򷢲������.
 *****************************************************************************/

package com.sz.hm.core.base.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.sz.hm.core.base.model.Pager;
import com.sz.hm.core.base.model.ResultJson;

/**
 * ����DAO��,�Զ�ע��sessionFactory
 *
 * @author �ĵ»�
 * @since JDK1.6
 * @history 2010-07-28 �ĵ»� �½�
 */
public class BaseDAO extends HibernateDaoSupport {
	
	/** ע�� rawtypes  */
	private static final String RAW_TYPES = "rawtypes";
	
    /**
     * ע��sessionFactory
     *
     * @param sessionFactory
     */
    @Autowired(required = false)
    public void setSessionfactory(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

    /**
     * ��ȡ����
     *
     * @param <T> ��������
     * @param clz VO CLASS����
     * @param key VO ����
     * @return ���ҵ��Ľ��,���û�ҵ�,����null
     */
    public <T> T read(Class<T> clz, Serializable key) {
    	if(key==null){
    		return null;
    	}
        return clz.cast(this.getHibernateTemplate().get(clz, key));
    }

    /**
     * �������
     */
    public void update(Object obj) {
        this.getHibernateTemplate().update(obj);
    }
    
    /**
     * �������
     */
    public void save(Object obj) {
        this.getHibernateTemplate().save(obj);
    }

    /**
     * �������¶���
     */
    public void saveOrUpdate(Object obj) {
        this.getHibernateTemplate().saveOrUpdate(obj);
    }

    /**
     * �������¼��϶���
     */
    public void saveOrUpdateAll(@SuppressWarnings(RAW_TYPES) Collection collection) {
        for(Object obj:collection){
        	this.getHibernateTemplate().saveOrUpdate(obj);
        }
    }

    /**
     * �������¶���
     */
    public void deleteAll(@SuppressWarnings(RAW_TYPES) Collection col) {
        this.getHibernateTemplate().deleteAll(col);
    }
    
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param cls ��������
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Class<T> cls) {
        return find(hql,Collections.EMPTY_LIST,cls);
    }
    
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings(RAW_TYPES)
	public <T> List<T> find(String hql, List<Object> args, Class<T> cls) {
        List<T> result = new ArrayList<T>();
		List lst = this.getHibernateTemplate().find(hql, args.toArray());
        for (Object obj : lst) {
            result.add(cls.cast(obj));
        }
        return result;
    }
    
    /**
     * ����hql��ѯ,������ִ�����͵��б�
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls �������� 
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Object[]> findwithRawResult(String hql, List<Object> args) {
        return (List<Object[]>) this.getHibernateTemplate().find(hql, args.toArray());
    }
    
    /**
     * ����hql��ѯ,�����б���������
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
	public int findCount(final String hql, final List<Object> args) {
    	return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
    		 
            @Override
            public Integer doInHibernate(Session session){
            	
            	Query query = session.createQuery(hql.trim().startsWith("from")?"select count(1) ".concat(hql):hql);
            	int i = 0;
            	for (Object arg : args) {
                    query.setParameter(i++, arg);
                }
            	
            	if(null != query.uniqueResult()){
            		return ((Long)query.uniqueResult()).intValue();
            	}else{
            		return 0 ;
            	}
            }
        });
    }
    
    /**
     * ����hql��ѯ,�����б��ҳ����
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({ "unchecked" })
	public <T> List<T> findList(final String hql, final Pager page, final List<Object> args,Class<T> cls) {
        return this.getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

            public List<T> doInHibernate(Session session) {
                Query query = session.createQuery(hql);
                int i = 0;
                for (Object arg : args) {
                    query.setParameter(i++, arg);
                }
                query.setFirstResult(page.getPageSize()*(page.getPageNum()-1));
                query.setMaxResults(page.getPageSize());
                return query.list();
            }
        });
    }
    
    /**
     * ����hql��ѯ,����Ψһ������
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({ RAW_TYPES, "unchecked" })
	public <T> T findUnique(final String hql, final List<Object> args,Class<T> cls) {
        return  (T)this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) {
                Query query = session.createQuery(hql);
                int i = 0;
                for (Object arg : args) {
                    query.setParameter(i++, arg);
                }
                query.setFirstResult(0);
                query.setMaxResults(1);
                List result =  query.list();
                return result.isEmpty()?null:result.get(0);
            }

        });
    }

    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({ RAW_TYPES})
    public List findBySql(final String sql, final List args) {
        return this.getHibernateTemplate().execute(new HibernateCallback<List>() {

            public List doInHibernate(Session session) {
                SQLQuery query = session.createSQLQuery(sql);
                int i = 0;
                for (Object arg : args) {
                    query.setParameter(i++, arg);
                }
                return query.list();
            }

        });
    }
    
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({ RAW_TYPES})
    public List findByPageSql(final String sql, final List args, final Pager pager) {
        return this.getHibernateTemplate().execute(new HibernateCallback<List>() {

            public List doInHibernate(Session session) {
                SQLQuery query = session.createSQLQuery(sql);
                int i = 0;
                for (Object arg : args) {
                    query.setParameter(i++, arg);
                }
                query.setFirstResult(pager.getPageSize()*(pager.getPageNum()-1));
	            query.setMaxResults(pager.getPageSize());
                return query.list();
            }

        });
    }
    
    
    /**
     * hqlֻ����һ����
     *
     * @param hql ��ѯ���
     * @param args ��������
     * @param cls ��������
     * @return
     */
    public ResultJson bulkUpdateInLoop(String hql, Object args[]) {
    	int iCount = 0;
        for(Object arg:args){
        	iCount += getHibernateTemplate().bulkUpdate(hql, arg);
        }
        Map<String,Object> attrs = new HashMap<String,Object>(1);
        attrs.put("doCount", iCount);
        ResultJson result = new ResultJson(true);
        result.setData(attrs);
    	return result;
    }
    
    /**
     * hqlֻ����һ����
     *
     * @param hql ��ѯ���
     * @param args ��������
     * @param cls ��������
     * @return
     */
    public ResultJson bulkUpdate(String hql, Object args[]) {
        getHibernateTemplate().bulkUpdate(hql, args);
    	return new ResultJson(true);
    }
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({RAW_TYPES, "unchecked" })
    public List<Object[]> findRawByComplexHql(final String hql, final Map<String, Object> map) {
    	// ��ѯ���
        return  (List<Object[]>)this.getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) {
                Query query = session.createQuery(hql);
                if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
                }  
                return query.list();
            }

        });
    }   
    
    /**
     * ����hql��ѯ,�����б���������
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
	public int findCountByComplexHql(final String hql, final Map<String, Object> map) {
    	return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
    		 
            @Override
            public Integer doInHibernate(Session session){
            	
            	Query query = session.createQuery(hql.trim().startsWith("from")?"select count(1) ".concat(hql):hql);
            	
            	if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
            	}
            	
            	if(null != query.uniqueResult()){
            		return ((Long)query.uniqueResult()).intValue();
            	}else{
            		return 0 ;
            	}
            }
        });
    }
	
	 /**
     * ����sql��ѯ,�����б���������
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
	public int findCountByComplexSql(final String hql, final Map<String, Object> map) {
    	return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
    		 
            @Override
            public Integer doInHibernate(Session session){
            	
            	Query query = session.createSQLQuery(hql.trim().startsWith("from")?"select count(1) ".concat(hql):hql);
            	
            	if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
            	}
            	
            	if(null != query.uniqueResult()){
            		return ((Long)query.uniqueResult()).intValue();
            	}else{
            		return 0 ;
            	}
            }
        });
    }
	
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({RAW_TYPES })
    public <T> List<T> findByComplexHql(final String hql, final Map<String, Object> map, Class<T> cls) {
    	// ��ѯ���
        List lst =  this.getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) {
                Query query = session.createQuery(hql);
                if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
                }  
                return query.list();
            }

        });
        
        // ��װ���
        List<T> result = new ArrayList<T>();
        for (Object obj : lst) {
            result.add(cls.cast(obj));
        }
        return result;
    }
    
    /**
     * ����hql��ѯ,������Ψһһ�����
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({RAW_TYPES })
    public <T> T findUniqueByComplexHql(final String hql, final Map<String, Object> map, Class<T> cls) {
    	// ��ѯ���
        List<T> lst =  this.getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            public List doInHibernate(Session session) {
                Query query = session.createQuery(hql);
                if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
                }  
                query.setFirstResult(0);
                query.setMaxResults(1);
                return query.list();
            }

        });
        
        return lst.isEmpty()?null:cls.cast(lst.get(0));
    }

    
    /**
     * ����Sql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({RAW_TYPES })
    public <T> List<T> findByComplexSql(final String sql, final Map<String, Object> map, Class<T> cls) {
    	// ��ѯ���
        List lst =  this.getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) {
                SQLQuery query = session.createSQLQuery(sql);
                if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
                }  
                return query.list();
            }

        });
        
        // ��װ���
        List<T> result = new ArrayList<T>();
        for (Object obj : lst) {
            result.add(cls.cast(obj));
        }
        return result;
    }
    
    
    /**
     * ����Sql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({RAW_TYPES, "unchecked" })
    public List<Object[]> findRawByComplexSql(final String sql, final Map<String, Object> map) {
    	// ��ѯ���
        return  (List<Object[]>)this.getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) {
                SQLQuery query = session.createSQLQuery(sql);
                if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
                }  
                return query.list();
            }

        });
        
     
    }
    
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({RAW_TYPES })
    public <T> List<T> findByComplexHql(final String hql,final Pager page, final Map<String, Object> map, Class<T> cls) {
    	// ��ѯ���
        List lst =  this.getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) {
                Query query = session.createQuery(hql);
                if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
                }  
                query.setFirstResult(page.getPageSize()*(page.getPageNum()-1));
                query.setMaxResults(page.getPageSize());
                return query.list();
            }

        });
        
        // ��װ���
        List<T> result = new ArrayList<T>();
        for (Object obj : lst) {
            result.add(cls.cast(obj));
        }
        return result;
    }
    
    /**
     * ����Sql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    @SuppressWarnings({RAW_TYPES })
    public <T> List<T> findByComplexSql(final String sql,final Pager page, final Map<String, Object> map, Class<T> cls) {
    	// ��ѯ���
        List lst =  this.getHibernateTemplate().execute(new HibernateCallback<List>() {
            public List doInHibernate(Session session) {
            	 SQLQuery query = session.createSQLQuery(sql);
                if (map != null) {  
                    Set<String> keySet = map.keySet();  
                    for (String string : keySet) {  
                        Object obj = map.get(string);  
                        //���￼�Ǵ���Ĳ�����ʲô���ͣ���ͬ����ʹ�õķ�����ͬ  
                        if(obj instanceof Collection<?>){  
                            query.setParameterList(string, (Collection<?>)obj);  
                        }else if(obj instanceof Object[]){  
                            query.setParameterList(string, (Object[])obj);  
                        }else{  
                            query.setParameter(string, obj);  
                        }  
                    }  
                }  
                query.setFirstResult(page.getPageSize()*(page.getPageNum()-1));
                query.setMaxResults(page.getPageSize());
                return query.list();
            }

        });
        
        // ��װ���
        List<T> result = new ArrayList<T>();
        for (Object obj : lst) {
            result.add(cls.cast(obj));
        }
        return result;
    }
    
}
