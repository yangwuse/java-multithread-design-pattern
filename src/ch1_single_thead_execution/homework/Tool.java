package ch1_single_thead_execution.homework;

// 餐具(刀/叉)类
public class Tool {
    private final String name;
    public Tool(String name) {
        this.name = name;
    }
    public String toString() {
        return "[ " + name + " ]";
    }
}
