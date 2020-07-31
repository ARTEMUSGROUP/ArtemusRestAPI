package com.artemus.app.model.request;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class Package {

	@Schema(description = "A description of the package type. See Appendix C for a list of valid packages.",required = true)
	@Attributes(required = true, description = "The packageType.")
	@NotEmpty(message = "packageType cannot be blank")
	@Valid
	private String packageType;
	
	@Schema(description = "Holds text description of marks found on this package. This element has no attributes and no text – the marks are in the element’s text. If the text would cause XML parsing issues, feel free to use a CDATA tag.",required = false)
	private String marks;
	
	@Schema(description = "The number of cargo pieces in this package.Must be greater than 0.",required = true)
	private String pieces;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit weight;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit volume;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit length;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit width;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit height;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit set;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit min;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit max;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit vents;
	
	@Schema(description = "The name of this attribute. See Appendix B for known attribute types.",required = false)
	private ValueUnit drainage;

	@AssertTrue(message = " Package Type must be 3 digit code.")
	@Hidden
	public boolean isValid() {
		if ((packageType.length()>3)) {
			return false;
		} else {
			return true;
		}
	}
	
	public String getPackageType() {
		if(packageType==null)
			return packageType;	
		else	
		return packageType.toUpperCase();
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getMarks() {
		if (marks == null) {
			marks = "NO MARKS";
		}
		return marks.toUpperCase();
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getPieces() {
		if(pieces==null)
			return pieces;
		else
		return pieces.toUpperCase();
	}

	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	public ValueUnit getWeight() {
		if (weight == null)
			weight = new ValueUnit();
		return weight;
	}

	public void setWeight(ValueUnit weight) {
		this.weight = weight;
	}

	public ValueUnit getVolume() {
		if (volume == null)
			volume = new ValueUnit();
		return volume;
	}

	public void setVolume(ValueUnit volume) {
		this.volume = volume;
	}

	public ValueUnit getLength() {
		if (length == null)
			length = new ValueUnit();
		return length;
	}

	public void setLength(ValueUnit length) {
		this.length = length;
	}

	public ValueUnit getWidth() {
		if (width == null)
			width = new ValueUnit();
		return width;
	}

	public void setWidth(ValueUnit width) {
		this.width = width;
	}

	public ValueUnit getHeight() {
		if (height == null)
			height = new ValueUnit();
		return height;
	}

	public void setHeight(ValueUnit height) {
		this.height = height;
	}

	public ValueUnit getSet() {
		if (set == null)
			set = new ValueUnit();
		return set;
	}

	public void setSet(ValueUnit set) {
		this.set = set;
	}

	public ValueUnit getMin() {
		if (min == null)
			min = new ValueUnit();
		return min;
	}

	public void setMin(ValueUnit min) {
		this.min = min;
	}

	public ValueUnit getMax() {
		if (max == null)
			max = new ValueUnit();
		return max;
	}

	public void setMax(ValueUnit max) {
		this.max = max;
	}

	public ValueUnit getVents() {
		if (vents == null)
			vents = new ValueUnit();
		return vents;
	}

	public void setVents(ValueUnit vents) {
		this.vents = vents;
	}

	public ValueUnit getDrainage() {
		if (drainage == null)
			drainage = new ValueUnit();
		return drainage;
	}

	public void setDrainage(ValueUnit drainage) {
		this.drainage = drainage;
	}

	public StringBuffer validatePackage() {
		StringBuffer objString = new StringBuffer();
		if (packageType == null || packageType.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("packageType");
		}
		
		if (pieces != null) {
			if (Integer.parseInt(pieces)<=0) {
				if (objString.length() > 0) {
					objString.append(",");
				}
				objString.append(" pieces: must be greater than 0 ");
			}
		}else {
			objString.append(" pieces: must be greater than 0 ");
		}
		
		
		if (weight == null) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("weight");
		}else {
			StringBuffer objWeightMsg = weight.validateValueUnit();
			if(weight.getUnit()!=null) {
				if(weight.getUnit().equalsIgnoreCase("LBS")||weight.getUnit().equalsIgnoreCase("KGS")||weight.getUnit().equalsIgnoreCase("MT")) {
				}else {
					if (objWeightMsg.length()>0) {
						objWeightMsg.append(",");
					}
					objWeightMsg.append(" unit: must be LBS,KGS,MT ");
					if (objString.length() > 0) {
						objString.append(",");
					}
					objString.append("weight:{ " + objWeightMsg + " }");
				}
			}
		}
		return objString;
	}

}
