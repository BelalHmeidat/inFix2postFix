public class MyStack {
    private Object[] stack;
    private int top;
    private int size;

    public MyStack(int size) {
        this.size = size;
        stack = new Object[size];
        top = -1;
    }

    public Object getTop() {
        if (top == -1) {
            System.out.println("Stack Empty!");
            return null;
        } else {
            return stack[top];
        }
    }

    public void push(Object element) {
        if (top == (size - 1)) {
            System.out.println("Stack is full!");
        } else {
            stack[++top] = element;
        }
    }

    public Object pop() {
        if (top == -1) {
            System.out.println("Stack Empty!");
            return -1;
        } else {
            return stack[top--];
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }

    //print stack
    public void printStack() {
        MyStack tmp = new MyStack(this.size);
        while (!this.isEmpty()) {
            tmp.push(this.pop());
        }
        while (!tmp.isEmpty()) {
            System.out.println(tmp.pop());
        }
    }
}