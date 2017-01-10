package com.sz.hm.core.base.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sz.hm.core.base.dao.CommonDAO;
import com.sz.hm.core.base.model.ITrace;
import com.sz.hm.core.base.model.Pager;
import com.sz.hm.core.base.model.ResultJson;
import com.sz.hm.core.base.util.TraceUtils;


@Service(value = "commonAppService")
/**
 * @author �ĵ»�
 * @since JDK1.7
 * @history 2014-11-18 �ĵ»� �½�
 */
public class CommonAppService {

    private CommonDAO commonDAO;


    public CommonDAO getCommonDAO() {
        return commonDAO;
    }
    
    /**
     * ɾ��ʵ��
     * @param trace
     */
	public void deleteTrace(ITrace trace) {
		trace.setDeleteFlag(true);
		TraceUtils.setUpdateTrace(trace);
		save(trace);
	}

    /**
     * ɾ��ʵ�弯��
     * @param traces
     */
	public <T extends ITrace> void  deleteTraces(List<T> traces) {
		for (ITrace trace : traces) {
			deleteTrace(trace);
		}
	}

    /**
     * ����ע��
     *
     * @param commonDAO
     */
    @Autowired(required = false)
    public void setCommonDAO(@Qualifier("commonDao")
    CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    /**
     * ��ȡ����
     * @param <T> ����
     * @param clz ʵ������
     * @param pk ʵ������
     * @return ��ѯ���
     */
    public <T> T read(Class<T> clz,Serializable pk){
        return this.commonDAO.read(clz, pk);
    }

    /**
     * �������
     * @return ��ѯ���
     */
    public ResultJson save(Object obj){
        this.commonDAO.saveOrUpdate(obj);
        return new ResultJson(true);

    }
    
    /**
     * ˢ�¶��� �ֶ��ύ
     * @return ��ѯ���
     */
    public void flush(){
    	getCommonDAO().getHibernateTemplate().flush();
    }
    
    /**
     * �������
     */
    public void clearCache(){
    	getCommonDAO().getHibernateTemplate().clear();
    }
    
    /**
     * �������
     * @return ��ѯ���
     */
    public ResultJson insert(Object obj){
        this.commonDAO.save(obj);
        return new ResultJson(true);

    }
    
    /**
     * �������
     * @return ��ѯ���
     */
    @SuppressWarnings("rawtypes")
	public ResultJson saveOrUpdateAll(Collection collection){
        commonDAO.saveOrUpdateAll(collection);
        return new ResultJson(true);

    }
    
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    public <T> List<T> find(String hql,List<Object> args,Class<T> cls){
        return this.commonDAO.find(hql, args, cls);
    }
    
    /**
     * ����hql��ѯ,������ִ�����͵��б���
     *
     * @param hql ��ѯ���
     * @param cls ��������
     * @return
     */
	public int findCount(String hql, List<Object> args) {
        return  this.commonDAO.findCount(hql, args);
    }
    
    
	/**
     * �������¶���
     */
    public void deleteAll(@SuppressWarnings("rawtypes") Collection col) {
    	this.commonDAO.deleteAll(col);
    }
    
    /**
     * ����hql��ѯ,������ִ�����͵�Ψһ���
     * @param hql ��ѯ���
     * @param args ����
     * @param cls ��������
     * @return
     */
    public <T> T findUnique(String hql,List<Object> args,Class<T> cls){
        return this.commonDAO.findUnique(hql, args, cls);
    }

    /**
     * ʹ��sql��ѯ���
     * @param sql
     * @param args
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List findBySql(final String sql,final List<Object> args){
        return this.commonDAO.findBySql(sql, args);
    }
    
    /**
     * ʹ��sql��ѯ���
     * @param sql
     * @param args
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List findByPageSql(final String sql,final List<Object> args, final Pager pager){
        return this.commonDAO.findByPageSql(sql, args, pager);
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
        return this.commonDAO.bulkUpdateInLoop(hql, args);
    }
    
    /**
     * ����hql
     * @param sql
     * @param map
     * @param cls
     * @return
     */
    public <T> List<T> findByComplexHql(final String hql, final Map<String, Object> map, Class<T> cls){
    	 return this.commonDAO.findByComplexHql(hql, map, cls);
    }
    
    /**
     * ����hql
     * @param sql
     * @param map
     * @param cls
     * @return
     */
    public <T> List<T> findByComplexHql(final String hql, final Pager page, final Map<String, Object> map, Class<T> cls){
    	 return this.commonDAO.findByComplexHql(hql, page, map, cls);
    }
    
    /**
     * ���ظ��� ����hql
     *
     * @param hql ��ѯ���
     * @param cls ��������
     * @return
     */
	public int findCountByComplexHql(final String hql, final Map<String, Object> map) {
        return  this.commonDAO.findCountByComplexHql(hql, map);
    }
	
	 /**
     * ���ظ��� ����sql
     *
     * @param hql ��ѯ���
     * @param cls ��������
     * @return
     */
	public int findCountByComplexSql(final String hql, final Map<String, Object> map) {
        return  this.commonDAO.findCountByComplexSql(hql, map);
    }
	
    /**
     * ����sql
     * @param sql
     * @param map
     * @param cls
     * @return
     */
    public <T> List<T> findByComplexSql(final String sql, final Map<String, Object> map, Class<T> cls){
    	 return this.commonDAO.findByComplexSql(sql, map, cls);
    }
    
    /**
     * ����hql
     * @param sql
     * @param map
     * @param cls
     * @return
     */
    public <T> List<T> findByComplexSql(final String sql, final Pager page, final Map<String, Object> map, Class<T> cls){
    	 return this.commonDAO.findByComplexSql(sql, page, map, cls);
    }
    
    /**
     * ��ҳ��ѯ
     * @param hql
     * @param page
     * @param args
     * @param cls
     * @return
     */
    public <T> List<T> findList(final String hql, final Pager page, final List<Object> args,Class<T> cls){
    	return this.commonDAO.findList(hql, page, args, cls);
    }
    
    /**
     * ��ѯ
     * @param hql
     * @param args
     * @return
     */
	public List<Object[]> findwithRawResult(String hql, List<Object> args) {
        return this.commonDAO.findwithRawResult(hql, args);
    }
	
}
