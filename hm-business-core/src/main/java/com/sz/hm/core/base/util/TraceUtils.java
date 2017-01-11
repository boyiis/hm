package com.sz.hm.core.base.util;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.sz.hm.core.base.model.ITrace;


/**
 * 轨迹工具类
 */
public class TraceUtils {
	
	/** */
	public static final String ADMIN_ACCOUNT="admin";
	
	public static final String ADMIN_ACCOUNT_ID="admin";
	
	/**
	 * ���ü��� �����켣��Ϣ
	 * @param trace
	 */
	public static void setCreateActiveTrace(ITrace trace){
		
		trace.setCreateId(ADMIN_ACCOUNT_ID);
		trace.setCreateTime(new Date(System.currentTimeMillis()));
		trace.setCreateName(ADMIN_ACCOUNT);
		trace.setUpdateId(ADMIN_ACCOUNT_ID);
		trace.setUpdateTime(new Date(System.currentTimeMillis()));
		trace.setUpdateName(ADMIN_ACCOUNT);
		
	}
	
	/**
	 * ���������켣��Ϣ
	 * @param trace
	 */
	public static void setCreateTrace(ITrace trace){
		
		
		if(UserUtils.getUser()==null){
			setCreateActiveTrace(trace);
			return;
		}
		
		trace.setCreateId(UserUtils.getUser().getId());
		trace.setCreateTime(new Date(System.currentTimeMillis()));
		trace.setCreateName(UserUtils.getUser().getMobilePhone());
		trace.setUpdateId(UserUtils.getUser().getId());
		trace.setUpdateTime(new Date(System.currentTimeMillis()));
		trace.setUpdateName(UserUtils.getUser().getMobilePhone());
		
	}
	
	/**
	 * ���������켣��Ϣ
	 * @param trace
	 */
	@SuppressWarnings("rawtypes")
	public static void setCreateTrace(Collection cls){
		
		ITrace trace=null;
		for(Object obj : cls){
			trace = (ITrace) obj;
			setCreateTrace(trace);
		}
	}
	
	/**
	 * ɾ����ɾ���ļ�¼
	 * @param trace
	 */
	@SuppressWarnings("rawtypes")
	public static void removeDelete(List cls){
		
		ITrace trace=null;
		for(int i=cls.size()-1;i>-1;i--){
			trace = (ITrace) cls.get(i);
			if(trace.getDeleteFlag()){
				cls.remove(i);
			}
		}
	}
	
	/**
	 * ���ø��¹켣��Ϣ
	 * @param trace
	 */
	public static void setUpdateTrace(ITrace trace){
		
		if(UserUtils.getUser()==null){
			return;
		}
		
		trace.setUpdateId(UserUtils.getUser().getId());
		trace.setUpdateTime(new Date(System.currentTimeMillis()));
		trace.setUpdateName(UserUtils.getUser().getMobilePhone());
		
	}
	
	/**
	 * ���������켣��Ϣ
	 * @param trace
	 */
	@SuppressWarnings("rawtypes")
	public static void setUpdateTrace(Collection cls){
		
		ITrace trace=null;
		for(Object obj : cls){
			trace = (ITrace) obj;
			setUpdateTrace(trace);
		}
	}
	
	/**
	 * ͨ��HashSet�߳��ظ�Ԫ��
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicateList(List list)  {
        HashSet h  = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
	
	/**
	 * ��ʼ������ʵ��
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T initializeAndUnproxy(T entity) {
	    if (entity == null) {
	        throw new 
	           NullPointerException("��ʼ��ʵ��Ϊ��");
	    }

	    Hibernate.initialize(entity);
	    if (entity instanceof HibernateProxy) {
	        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
	                .getImplementation();
	    }
	    return entity;
	}
}
