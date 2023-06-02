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
        @PostMapping("/trigger")
        public String trigger(){
//            Dotenv dotenv = null;
//            dotenv = Dotenv.configure().load();
//            System.out.println(String.format(
//                    "Hello World. Shell is: %s. Name is: %s",
//                    System.getenv("SHELL"),
//                    dotenv.get("NAME")
//            ));
            System.out.println("deplyment server was triggered");
            return "deployment server triggered";
        }
        @PostMapping("/deploy/{imageName}/{port}")
        public void deployDockerImage(@PathVariable String imageName, @PathVariable int port) throws IOException, InterruptedException {
            Path currRelativePath = Paths.get("");
            String path = currRelativePath.toAbsolutePath().toString();
            System.out.println(path);
             path+="/demoapp";
            System.out.println(path);
//            String[] cmdd = { "docker", "run", "-p", "8787:8072", "bbc1/apigatewayserver" };
//           // String[] cmdd = { "docker-compose", "up" };
//            System.out.println("started deploying api gateway");
//            Process processs = Runtime.getRuntime().exec(cmdd);
            System.out.println("started deploying demo");
            String[] cmdd2 = { "docker-compose", "up" };
            Process processs2 = Runtime.getRuntime().exec(cmdd2,null, new File(path));

        }
    }

}
