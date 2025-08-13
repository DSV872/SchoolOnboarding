package com.dsv.assignment;

import com.dsv.assignment.service.ExcelService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AssignmentApplication.class, args);
		ExcelService service = context.getBean(ExcelService.class);
        try {
            service.run();
        } catch (IOException e) {
            throw new RuntimeException("There is an error");
        }
    }

}
