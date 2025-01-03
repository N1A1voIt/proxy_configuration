package proxyController;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import proxyCore.Node;
import proxyCore.ProxyCoreService;

@WebServlet(urlPatterns = { "/proxy" })
public class ProxyController extends HttpServlet {
    private ProxyCoreService proxyCoreService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.proxyCoreService = new ProxyCoreService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeName = req.getParameter("nodeName");
        String backendName = req.getParameter("backendName");
        String nodeIp = req.getParameter("nodeIp");
        Node node = new Node(nodeName, nodeIp);
        try {
            proxyCoreService.updateBackendNodes(backendName, node);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action") == null && req.getParameter("backendName") == null) {
            req.setAttribute("backendAndNodes", proxyCoreService.allBackendNodes());
        }
        String backendName = req.getParameter("backendName");
        if (req.getParameter("action").equals("delete")) {
            String nodeName = req.getParameter("nodeName");
            proxyCoreService.deleteNode(backendName, nodeName);
        }
        if (req.getParameter("action") == null) {
            req.setAttribute("clusters", proxyCoreService.getBackendNodes(backendName));
        }
    }
}
