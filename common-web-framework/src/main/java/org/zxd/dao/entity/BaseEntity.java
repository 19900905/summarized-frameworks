package org.zxd.dao.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.zxd.dao.emums.YesNoEnum;

/**
 * 表通用字段
 */
@Data
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -2269755654427578345L;
	private Long id;
	/**
	 * 创建时间
	 */
	private Date createAt = new Date();
	/**
	 * 更新时间
	 */
	private Date updateAt;
	/**
	 * 版本号
	 */
	private Integer version = Integer.valueOf(1);
	/**
	 * 是否逻辑删除
	 */
	private Integer deleted = Integer.valueOf(0);

	/**
	 * 填充基础信息
	 */
	public static void fillBaseInfo(BaseEntity baseEntity) {
		baseEntity.setCreateAt(new Date());
		baseEntity.setDeleted(YesNoEnum.NO.getCode());
		baseEntity.setVersion(Integer.valueOf(1));
	}
}
