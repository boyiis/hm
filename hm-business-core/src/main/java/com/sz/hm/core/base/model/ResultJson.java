package com.sz.hm.core.base.model;

import java.io.Serializable;

/**
 * ��������� �ӿ�ͳһ������������
 * 
 * {
	 * 	"result": ���ؽ��(booleanֵ),
	 *  "data": ��������(object),
	 *  "msg": ������Ϣ,
 * }
 * 
 * @author zhiyong
 *
 */
public class ResultJson implements Serializable  {
	
	private static final long serialVersionUID = -9024761391789730558L;

	/** �ɹ�:true,ʧ��:false */
	private boolean result;
	
	/** ������Ϣ */
	private String msg;
	
	/** �������  */
	private Object data;
	
	public ResultJson(){
		
	}
	
	public ResultJson(boolean result){
		this.result=result;
	}
	
	public ResultJson(boolean result,String msg){
		this.result=result;
		this.msg=msg;
	}
	
	public ResultJson(boolean result,String msg, Object data){
		this.result=result;
		this.msg=msg;
		this.data=data;
	}
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
