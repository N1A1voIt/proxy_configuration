package proxyCore;

public class Node {
    String nodeLine;
    String nodeName;
    String nodeIp;

    public Node(String nodeLine, String nodeName, String nodeIp) {
        this.nodeLine = nodeLine;
        this.nodeName = nodeName;
        this.nodeIp = nodeIp;
    }
    public Node(String nodeName, String nodeIp) {
        this.nodeName = nodeName;
        this.nodeIp = nodeIp;
        this.nodeLine = "server "+nodeName+" "+nodeIp+" check";
    }
    public Node(String nodeLine) {
        this.nodeLine = nodeLine;
    }
    public String getNodeLine() {
        return nodeLine;
    }
    public void setNodeLine(String nodeLine) {
        this.nodeLine = nodeLine;
    }
    public String getNodeName() {
        return nodeLine.split(" ")[1];
    }
    public String getNodeIp() {
        return nodeLine.split(" ")[2];
    }
    
}
