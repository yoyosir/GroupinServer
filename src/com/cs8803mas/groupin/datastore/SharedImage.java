package com.cs8803mas.groupin.datastore;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class SharedImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Long gid;
	@Persistent
	private String name;
	@Persistent
	private Long uid;
	@Persistent
	private Blob blob;
	@Persistent
	private Long time;

	public SharedImage() {

	}

	public SharedImage(Long gid, Long uid, String name, byte[] bytes) {
		super();
		this.gid = gid;
		this.uid = uid;
		this.name = name;
		this.blob = new Blob(bytes);
		time = System.currentTimeMillis();
	}

	public Long getTime() {
		return time;
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Blob getBlob() {
		return blob;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}

}
