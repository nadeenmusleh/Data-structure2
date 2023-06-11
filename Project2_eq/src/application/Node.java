package application;

public class Node {
	Object data;
	int next;

	public Node(Object data, int next) {
		this.data = data;
		this.next = next;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toString() {
		return "[" + data + "]";
	}
}
