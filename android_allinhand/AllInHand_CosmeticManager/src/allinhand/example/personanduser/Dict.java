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
	 * * ΪʲôҪ��дtoString()�أ� * *
	 * ��Ϊ����������ʾ���ݵ�ʱ����������������Ķ������ַ���������£�ֱ�Ӿ�ʹ�ö���.toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
