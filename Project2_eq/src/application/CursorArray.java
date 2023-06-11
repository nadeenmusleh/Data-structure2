package application;

public class CursorArray {
	Node[] cursorArray;// = new Node[11];

	public CursorArray(int capacity) {
		cursorArray = new Node[capacity];
		initialization();
	}

	private int initialization() {
		for (int i = 0; i < cursorArray.length - 1; i++)
			cursorArray[i] = new Node(null, i + 1);
		cursorArray[cursorArray.length - 1] = new Node(null, 0);
		return 0;
	}

	private int malloc() {
		int p = cursorArray[0].next;
		cursorArray[0].next = cursorArray[p].next;
		return p;
	}

	private void free(int p) {
		cursorArray[p] = new Node(null, cursorArray[0].next);
		cursorArray[0].next = p;
	}

	private boolean isNull(int l) {
		return cursorArray[l] == null;
	}

	public boolean isEmpty(int l) {
		return cursorArray[l].next == 0;
	}

	private boolean isLast(int p) {
		return cursorArray[p].next == 0;
	}

	public int createList() {
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!!!");
		else
			cursorArray[l] = new Node("-", 0);
		return l;
	}

	public boolean insertAtHead(Object data, int x) {
		if (isNull(x)) // list not created
			return false;
		int p = malloc();
		if (p != 0) {
			cursorArray[p] = new Node(data, cursorArray[x].next);
			cursorArray[x].next = p;
		} else {
//			System.out.println("Error: Out of space!!!");
			return false;
		}
		return true;
	}

	public void insertAtLast(Object data, int x) {
		if (isNull(x)) // list not created
			return;
		int p = malloc();
		if (p != 0) {
			while (!isLast(x))
				x = cursorArray[x].next;
			cursorArray[p] = new Node(data, 0);
			cursorArray[x].next = p;
		} else
			System.out.println("Error: Out of space!!!");
	}


	public void insertSorted(Object data, int x) {
		if (isNull(x)) // list not created
			return;
		int p = malloc();
		if (p != 0) {
			while (!isLast(x) &&
					((Comparable) cursorArray[cursorArray[x].next].data).compareTo(data)<0)
				x = cursorArray[x].next;
			if(cursorArray[x].next == 0)
				cursorArray[p] = new Node(data, 0);
			else
				cursorArray[p] = new Node(data, cursorArray[x].next);
			cursorArray[x].next = p;



		} else
			System.out.println("Error: Out of space!!!");
	}


	public void deleteAtLast(int x) {
		if (isNull(x) || isEmpty(x)){ // list not created
			System.out.println("Empty List!!!");
			return;
		}

		while (!isLast(cursorArray[x].next))
			x = cursorArray[x].next;
		int p = cursorArray[x].next;
		cursorArray[x].next = 0;
		free(p);

	}

	public void traversList(int x) {
		System.out.print("list_" + x + "-->");
		while (!isNull(x) && !isEmpty(x)) {
			x = cursorArray[x].next;
			System.out.print(cursorArray[x] + "-->");
		}
		System.out.println("null");
	}

	public int find(Object data, int x) {
		while (!isNull(x) && !isEmpty(x)) {
			x = cursorArray[x].next;
			if (cursorArray[x].data.equals(data))
				return x;
		}
		return -1; // not found
	}

	public int findPrevious(Object data, int x) {
		while (!isNull(x) && !isEmpty(x)) {
			if (cursorArray[cursorArray[x].next].data.equals(data))
				return x;
			x= cursorArray[x].next;
		}
		return -1; // not found
	}


	public Object deleteFirst( int x) {
		if(!isNull(x) && !isEmpty(x)){
			int p = cursorArray[x].next;
			cursorArray[x].next = cursorArray[cursorArray[x].next].next;
			Node temp = cursorArray[p];
			return temp.data;
		}
		return null;
	}


	public Object getFirst(int x){
		if(!isNull(x) && !isEmpty(x))
			return cursorArray[cursorArray[x].next].data;
		return null;
	}

	public Node delete(Object data, int x) {
		int p = findPrevious(data, x);
		if (p != -1) {
			int c = cursorArray[p].next;
			Node temp = cursorArray[c];
			cursorArray[p].next = temp.next;
			free(c);
		}
		return null;
	}
}