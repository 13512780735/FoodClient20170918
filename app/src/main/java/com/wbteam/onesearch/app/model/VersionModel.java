package com.wbteam.onesearch.app.model;

/**
 *  版本更新
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-11-9  下午3:40:03
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class VersionModel {

	private String name;
	private int version;
	private String changelog;
	private long updated_at;
	private String versionShort;
	private int build;
	private String installUrl;
	private String install_url;
	private String direct_install_url;
	private String update_url;
	private String binary;

	public VersionModel() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getChangelog() {
		return changelog;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public long getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(long updated_at) {
		this.updated_at = updated_at;
	}

	public String getVersionShort() {
		return versionShort;
	}

	public void setVersionShort(String versionShort) {
		this.versionShort = versionShort;
	}

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		this.build = build;
	}

	public String getInstallUrl() {
		return installUrl;
	}

	public void setInstallUrl(String installUrl) {
		this.installUrl = installUrl;
	}

	public String getInstall_url() {
		return install_url;
	}

	public void setInstall_url(String install_url) {
		this.install_url = install_url;
	}

	public String getDirect_install_url() {
		return direct_install_url;
	}

	public void setDirect_install_url(String direct_install_url) {
		this.direct_install_url = direct_install_url;
	}

	public String getUpdate_url() {
		return update_url;
	}

	public void setUpdate_url(String update_url) {
		this.update_url = update_url;
	}

	public String getBinary() {
		return binary;
	}

	public void setBinary(String binary) {
		this.binary = binary;
	}

}
