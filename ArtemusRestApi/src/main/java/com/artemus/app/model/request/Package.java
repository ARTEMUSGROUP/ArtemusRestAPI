package com.artemus.app.model.request;

public class Package {

	private String packageType;
	private String marks;
	private int pieces;
	private ValueUnit weight;
	private ValueUnit volume;
	private ValueUnit length;
	private ValueUnit width;
	private ValueUnit height;
	private ValueUnit set;
	private ValueUnit min;
	private ValueUnit max;
	private ValueUnit vents;
	private ValueUnit drainage;

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public ValueUnit getWeight() {
		return weight;
	}

	public void setWeight(ValueUnit weight) {
		this.weight = weight;
	}

	public ValueUnit getVolume() {
		return volume;
	}

	public void setVolume(ValueUnit volume) {
		this.volume = volume;
	}

	public ValueUnit getLength() {
		return length;
	}

	public void setLength(ValueUnit length) {
		this.length = length;
	}

	public ValueUnit getWidth() {
		return width;
	}

	public void setWidth(ValueUnit width) {
		this.width = width;
	}

	public ValueUnit getHeight() {
		return height;
	}

	public void setHeight(ValueUnit height) {
		this.height = height;
	}

	public ValueUnit getSet() {
		return set;
	}

	public void setSet(ValueUnit set) {
		this.set = set;
	}

	public ValueUnit getMin() {
		return min;
	}

	public void setMin(ValueUnit min) {
		this.min = min;
	}

	public ValueUnit getMax() {
		return max;
	}

	public void setMax(ValueUnit max) {
		this.max = max;
	}

	public ValueUnit getVents() {
		return vents;
	}

	public void setVents(ValueUnit vents) {
		this.vents = vents;
	}

	public ValueUnit getDrainage() {
		return drainage;
	}

	public void setDrainage(ValueUnit drainage) {
		this.drainage = drainage;
	}
	
	public StringBuffer validatePackage() {
		StringBuffer objString = new StringBuffer();
		if(packageType==null||packageType.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("packageType");
		}
		return objString;
	}

}
