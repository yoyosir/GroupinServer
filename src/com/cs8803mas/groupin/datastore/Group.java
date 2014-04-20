package com.cs8803mas.groupin.datastore;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String name;
	@Persistent
	private Long guuid;
	@Persistent
	private String passcode;
	@Persistent
	private Double coordinateX;
	@Persistent
	private Double coordinateY;

	public Group() {

	}

	public Group(String name, Long guuid, String passcode, Double coordinateX, Double coordinateY) {
		super();
		this.name = name;
		this.guuid = guuid;
		this.passcode = passcode;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGuuid() {
		return guuid;
	}

	public void setGuuid(Long guuid) {
		this.guuid = guuid;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public Double getCoordinateX() {
		if (coordinateX == null) coordinateX = 0.0;
		return coordinateX;
	}

	public void setCoordinateX(Double coordinateX) {
		this.coordinateX = coordinateX;
	}

	public Double getCoordinateY() {
		if (coordinateY == null) coordinateY = 0.0;
		return coordinateY;
	}

	public void setCoordinateY(Double coordinateY) {
		this.coordinateY = coordinateY;
	}

}
