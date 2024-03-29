package example.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import example.dto.Greeting;
import example.dto.MyClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.joining;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final String URL = "http://arxemond.ru";
    private final List<MyClass> myClasses = new ArrayList<>();
    private final List<String> stringList = new LinkedList<>();

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/delay")
    public String delay() throws InterruptedException {
//        final int delay = new Random().nextInt(3000 - 500 + 1) + 500;
//        System.out.printf("delay: %d ms" + System.lineSeparator(), delay);
//        Thread.sleep(delay);

//        myClasses.add(new MyClass(String.valueOf(delay)));

        return restTemplate.getForEntity(URL, String.class).getBody();
    }

    @RequestMapping("/without-delay")
    public String withoutDelay() {
        return restTemplate.getForEntity(URL, String.class).getBody();
    }

    @RequestMapping("/memory-leak")
    public ResponseEntity<?> memoryLeak() {
        final String string = IntStream.range(0, 50000)
                .boxed()
                .map(idx ->
                        CompletableFuture.supplyAsync(() -> {
                            final String s = String.valueOf(idx);
                            stringList.add(s);
                            return s;
                        }))
                .map(CompletableFuture::join)
                .collect(joining(" "));

        stringList.add(string);

        return new ResponseEntity<>(string, HttpStatus.OK);

    }
}