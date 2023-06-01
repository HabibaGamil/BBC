package org.example;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
public class DeploymentController {
    @RequestMapping("/addCommand") public String  addCommand() {
        //eureka server used here to refresh all instances
        return "add command\n";
    }
    @RestController
    public class DockerController {
        @GetMapping("/deploy/{imageName}/{port}")
        public void deployDockerImage(@PathVariable String imageName, @PathVariable int port) throws IOException, InterruptedException {


            Path currRelativePath = Paths.get("");
            String path = currRelativePath.toAbsolutePath().toString();
            path+="/deploymentServer";
            System.out.println(path);
            //String[] cmdd = { "docker", "run", "-p", "8787:8072", "bbc1/apigatewayserver" };
            String[] cmdd = { "docker-compose", "up" };
            Process processs = Runtime.getRuntime().exec(cmdd,null, new File(path));


        }
    }

}
