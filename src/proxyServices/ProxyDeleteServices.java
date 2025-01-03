package proxyServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import utils.PathVariables;

public class ProxyDeleteServices {
    
    public void deleteBackendNode(String backendName, String nodeName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PathVariables.CONFIG_FILE_PATH));
        List<String> updatedLines = new ArrayList<>();

        boolean inTargetBackend = false;

        for (String line : lines) {
            if (line.trim().startsWith("backend " + backendName)) {
                inTargetBackend = true;
                updatedLines.add(line);
                continue;
            }

            if (inTargetBackend) {
                if (line.trim().startsWith("server " + nodeName + " ")) {
                    System.out.println("Deleting node: " + nodeName);
                    continue;
                }
                if (line.trim().isEmpty()) {
                    inTargetBackend = false;
                }
            }

            updatedLines.add(line);
        }

        Files.write(Paths.get(PathVariables.CONFIG_FILE_PATH), updatedLines);
        System.out.println("Node " + nodeName + " deleted successfully from backend " + backendName + ".");
    }
}
