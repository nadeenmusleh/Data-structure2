package application;

public class CursorStack {
	CursorArray stack = new CursorArray(1000);
	int list = stack.createList();
	
	public void push(Object data) {
		if(!stack.insertAtHead(data, list))
			System.out.println("Error Satck Overflow!!!!");
	}

	public Object pop() {
		return (Object)stack.deleteFirst(list);
	}

	public Object peek() {
		return (Object)stack.getFirst(list);
	}

	public boolean isEmpty() {
		return stack.isEmpty(list);
	}

	public void clear() {
		while(!isEmpty()) {
			stack.deleteFirst(list);
		}
	}
	

}