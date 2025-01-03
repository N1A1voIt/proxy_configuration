package proxyCore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import proxyServices.ProxyDeleteServices;
import proxyServices.ProxyUpdateService;
import proxyServices.SelectServices;

public class ProxyCoreService {
    ProxyDeleteServices proxyDeleteServices;
    ProxyUpdateService proxyUpdateService;
    SelectServices selectServices;
    public ProxyCoreService(){
        this.proxyDeleteServices = new ProxyDeleteServices();
        this.proxyUpdateService = new ProxyUpdateService();
        this.selectServices = new SelectServices();
    }
    public List<Node> getBackendNodes(String backendName) throws IOException{
        List<String> backends = this.selectServices.selectBackendNodes(backendName);
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < backends.size(); i++) {
            nodes.add(new Node(backends.get(i)));
        }
        return nodes;
    }
    public HashMap<String,List<Node>> allBackendNodes() throws IOException{
        return this.selectServices.allBackendNodes();
    }
    public void updateBackendNodes(String backendName,List<Node> node) throws Exception{
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < node.size(); i++) {
            nodes.add(node.get(i).getNodeLine());
        }
        List<String> actualNodes = selectServices.selectBackendNodes(backendName);
        nodes.addAll(actualNodes);
        this.proxyUpdateService.updateBackendCluster(backendName,nodes);
    }
    public void updateBackendNodes(String backendName,Node node) throws Exception{
        List<String> nodes = new ArrayList<>();
        List<String> actualNodes = selectServices.selectBackendNodes(backendName);
        nodes.addAll(actualNodes);
        nodes.add(node.getNodeLine());
        this.proxyUpdateService.updateBackendCluster(backendName,nodes);
    }
    public void deleteNode(String backendName, String nodeName) throws IOException{
        proxyDeleteServices.deleteBackendNode(backendName, nodeName);
    }
}
