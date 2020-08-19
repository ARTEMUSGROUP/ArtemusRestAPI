package com.artemus.app.configscac;

public class ConfigScac {

	private Boolean replaceDescFromHs;

	private Boolean isHsMandatory;

	

	public ConfigScac(Boolean replaceDescFromHs, Boolean isHsMandatory) {
		super();
		this.replaceDescFromHs = replaceDescFromHs;
		this.isHsMandatory = isHsMandatory;
	}

	public Boolean getReplaceDescFromHs() {
		return replaceDescFromHs;
	}

	public void setReplaceDescFromHs(Boolean replaceDescFromHs) {
		this.replaceDescFromHs = replaceDescFromHs;
	}

	public Boolean getIsHsMandatory() {
		return isHsMandatory;
	}

	public void setIsHsMandatory(Boolean isHsMandatory) {
		this.isHsMandatory = isHsMandatory;
	}

	@Override
	public String toString() {
		return "ConfigScac [replaceDescFromHs=" + replaceDescFromHs + ", isHsMandatory=" + isHsMandatory + "]";
	}

}
