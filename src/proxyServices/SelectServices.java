package proxyServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import proxyCore.Node;
import utils.PathVariables;

public class SelectServices {
    public List<String> selectBackendNodes(String backendName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PathVariables.CONFIG_FILE_PATH));
        List<String> backendNodes = new ArrayList<>();
        boolean inTargetBackend = false;
        for (String line : lines) {
            if (line.trim().startsWith("backend " + backendName)) {
                inTargetBackend = true;
                continue;
            }
            if (inTargetBackend) {
                if (line.trim().startsWith("server ")) {
                    backendNodes.add(line.trim());
                    System.out.println(line.trim());
                }
                if (line.trim().isEmpty()) {
                    break;
                }
            }
        }
        return backendNodes;
    }
    public HashMap<String,List<Node>> allBackendNodes() throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(PathVariables.CONFIG_FILE_PATH));
        HashMap<String,List<Node>> results = new HashMap<>();
        String backend = "";
        for (String line : lines) {
            if (line.trim().startsWith("backend")) {
                backend = line.trim().split(" ")[1];
                results.put(backend, new ArrayList<>());
            }
            if (line.trim().startsWith("server ")) {
                results.get(backend).add(new Node(line.trim().replace(" check","")));
            }
        }
        return results;
    }
}
