package proxyServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.PathVariables;

public class ProxyUpdateService {
    public void updateBackendCluster(String backendName, List<String> clusterNodes) throws IOException {
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
                if (line.trim().startsWith("server ")) {
                    continue;
                }
                if (line.trim().isEmpty()) {
                    updatedLines.addAll(clusterNodes.stream()
                            .map(node -> "    " + node)
                            .collect(Collectors.toList()));
                    inTargetBackend = false;
                }
            }
            updatedLines.add(line);
        }

        Files.write(Paths.get(PathVariables.CONFIG_FILE_PATH), updatedLines);
        System.out.println("HAProxy configuration updated successfully.");
    }

}
