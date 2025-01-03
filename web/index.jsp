<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="proxyCore.*" %>

<%
    Map<String, List<Node>> backendAndNodes = (Map<String, List<Node>>) request.getAttribute("backendAndNodes");
%>     
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Proxy Configuration</title>
    <link href="assets\css\bootstrap.min.css" rel="stylesheet">
    <script src="assets\js\jquery.min.js"></script>
    <script src="assets\js\bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Proxy Configuration</h1>
        <form action="proxy" method="post">
            <div class="form-group">
                <label for="backendName">Backend Name:</label>
                <input type="text" class="form-control" id="backendName" name="backendName" required>
            </div>
            <div class="form-group">
                <label for="nodeName">Node Name:</label>
                <input type="text" class="form-control" id="nodeName" name="nodeName" required>
            </div>
            <div class="form-group">
                <label for="nodeIp">Node IP:</label>
                <input type="text" class="form-control" id="nodeIp" name="nodeIp" required>
            </div>
            <button type="submit" class="btn btn-primary">Add/Update Node</button>
        </form>
        <hr>
        <h2>Backend Nodes</h2>
        <%
            if (backendAndNodes != null) {
                for (Map.Entry<String, List<Node>> entry : backendAndNodes.entrySet()) {
                    String backendName = entry.getKey();
                    List<Node> nodes = entry.getValue();
        %>
                    <h3><%= backendName %></h3>
                    <ul>
                        <%
                            for (Node node : nodes) {
                        %>
                                <li><%= node.getNodeName() %> (<%= node.getNodeIp() %>)
                                    <form action="proxy" method="get" style="display:inline;">
                                        <input type="hidden" name="backendName" value="<%= backendName %>">
                                        <input type="hidden" name="nodeName" value="<%= node.getNodeName() %>">
                                        <input type="hidden" name="action" value="delete">
                                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                    </form>
                                </li>
                        <%
                            }
                        %>
                    </ul>
        <%
                }
            }
        %>
    </div>
</body>
</html>