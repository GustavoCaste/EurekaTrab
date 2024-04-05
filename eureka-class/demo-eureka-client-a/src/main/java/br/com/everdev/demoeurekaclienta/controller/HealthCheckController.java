package br.com.everdev.demoeurekaclienta.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;

import ch.qos.logback.core.net.SyslogOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PatchExchange;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
public class HealthCheckController {
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/health")
    public String healthy() {
        return "Estou vivo e bem! Sou a app "+appName+" - " + LocalDateTime.now();
    }

    @GetMapping("/discover")
    public String discover() {
        Applications otherApps = eurekaClient.getApplications();
        return otherApps.getRegisteredApplications().toString();
    }

    @PostMapping("/receiveCall/{name}")
    public String receiveCall(@PathVariable String name, @RequestBody String message) {
        return  message + "\nOlá " + name + ". Aqui é "+appName+" e recebi sua mensagem.";
    }

    @GetMapping("/makeCall/{name}")
    public String makeCall(@PathVariable String name) throws URISyntaxException {
        String message = "Olá, tem alguem ai?!";

        List<InstanceInfo> instances = eurekaClient.getInstancesById(name);

        InstanceInfo instance = instances.getFirst();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://"+instance.getIPAddr() + ":" + instance.getPort()+"/receiveCall/"+appName))
                .POST(HttpRequest.BodyPublishers.ofString(message))
                .build();
        try {
            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/soma1/{nameB}/{nameC}")
    public String somaBeC(@PathVariable String nameB, @PathVariable String nameC) throws URISyntaxException {
        

        List<InstanceInfo> instances = eurekaClient.getInstancesById(nameB);


        InstanceInfo instance = instances.getFirst();

         HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://"+instance.getIPAddr() + ":" + instance.getPort()+"/soma2/"+nameC))
                .GET()
                .build();

                
               
                


        try {
            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
           String responseBody = response.body();
            return responseBody;



        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return String.valueOf(-1);
    }
   
}




