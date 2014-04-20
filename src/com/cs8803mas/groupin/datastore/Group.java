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
	private Double latitude;
	@Persistent
	private Double longitude;

	public Group() {

	}

	public Group(String name, Long guuid, String passcode, Double latitude, Double longitude) {
		super();
		this.name = name;
		this.guuid = guuid;
		this.passcode = passcode;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public Double getLatitude() {
		if (latitude == null) latitude = 0.0;
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		if (longitude == null) longitude = 0.0;
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
