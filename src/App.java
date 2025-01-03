import java.util.HashMap;
import java.util.List;

import proxyCore.Node;
import proxyCore.ProxyCoreService;

public class App {
    public static void main(String[] args) throws Exception {
        ProxyCoreService proxyCoreService = new ProxyCoreService();
        HashMap<String,List<Node>> res = proxyCoreService.allBackendNodes();
        for (var each : res.entrySet()) {
            System.out.println(each.getKey());
            for (var each1 : each.getValue()) {
                System.out.println("    "+each1.getNodeIp());
            }
        }
        System.out.println("Hello, World!");
    }
}
