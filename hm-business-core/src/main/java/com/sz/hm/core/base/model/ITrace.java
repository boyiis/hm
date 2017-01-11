package com.sz.hm.core.base.model;

import java.util.Date;

public interface ITrace {

	public abstract String getCreateId();

	public abstract void setCreateId(String createId);

	public abstract Date getCreateTime();

	public abstract void setCreateTime(Date createTime);

	public abstract String getCreateName();

	public abstract void setCreateName(String createName);

	public abstract boolean getDeleteFlag();

	public abstract void setDeleteFlag(boolean deleteFlag);

	public abstract String getUpdateId();

	public abstract void setUpdateId(String updateId);

	public abstract Date getUpdateTime();

	public abstract void setUpdateTime(Date updateTime);

	public abstract String getUpdateName();

	public abstract void setUpdateName(String updateName);
}
