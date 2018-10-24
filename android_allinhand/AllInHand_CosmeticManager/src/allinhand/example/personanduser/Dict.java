package allinhand.example.personanduser;

import java.io.Serializable;

public class Dict implements Serializable {
	private int id;
	private String text;

	public Dict() {
	}

	public Dict(int id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * * 为什么要重写toString()呢？ * *
	 * 因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
