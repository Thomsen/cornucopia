package com.cornucopia.ui.tree;

public class PdaOrgDto {
	
	/**
	 * 组织Id
	 */
	private Long dataId;
	
	/**
	 * 组织Code
	 */
	private String code;
	
	/**
	 * 组织名称
	 */
	private String text;
    
	/**
	 * 是否叶子节点
	 */
    private boolean leaf;
	
    /**
     * 组织级别
     */
	private int orgLevel;
	
	/**
	 * 组织类型
	 */
	private int orgType;
	
	/**
	 * 直属上级组织ID
	 */
	private Long parentId;

	/**
     * 候选处理人类型+ID(格式为type_Id)
     */
    private String typeDataId;
    
    /**
     * 本地添加，判断组织是否展开
     */
    private boolean isExpanded;
    
    /**
     * 本地添加，表明层次
     */
    private int level;
    
    public PdaOrgDto(Long dataId, String text, boolean leaf, int orgLevel,
			Long parentId) {
		super();
		this.dataId = dataId;
		this.text = text;
		this.leaf = leaf;
		this.orgLevel = orgLevel;
		this.parentId = parentId;
	}
    
	public String getTypeDataId() {
        return typeDataId;
    }
    public void setTypeDataId(String typeDataId) {
        this.typeDataId = typeDataId;
    }
    public Long getDataId() {
        return this.dataId;
    }
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean isLeaf() {
        return this.leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    public int getOrgLevel() {
        return this.orgLevel;
    }
    public void setOrgLevel(int orgLevel) {
        this.orgLevel = orgLevel;
    }
    public int getOrgType() {
        return this.orgType;
    }
    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
    
    

}
