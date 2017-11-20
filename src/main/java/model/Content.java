package model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="content")
public class Content {

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="url")
	private String url;
	
	@Column(name="content")
	private String content;
	
	@Column(name="created")
	private String created;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Content() {
		this.id = UUID.randomUUID().toString();
		this.created = new Date().toString();
	}
	
	public Content(String id, String url, String content, String created) {
		super();
		this.id = id;
		this.url = url;
		this.content = content;
		this.created = new Date().toString();
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", url=" + url + ", content=" + content + ", created=" + created + "]";
	}
}
